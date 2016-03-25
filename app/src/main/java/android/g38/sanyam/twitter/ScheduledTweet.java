package android.g38.sanyam.twitter;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.g38.sanyam.androidreceivers.ActionsReceiver;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;
import android.util.Log;

import java.util.GregorianCalendar;

import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by SANYAM TYAGI on 3/16/2016.
 */
public class ScheduledTweet extends BroadcastReceiver
{
    private static String PREF_NAME="SAMPLE_TWITTER_PREF";
    private static String PREF_KEY_OAUTH_TOKEN="OAUTH_TOKEN";
    private static String PREF_KEY_OAUTH_SECRET="OAUTH_SECRET";

    private static SharedPreferences sharedPreferences;

    private static String consumerKey=null;
    private static String consumerSecret=null;
    private static String callBackUrl=null;
    private static String oAuthVerifier=null;
    private static String status=null;

    private Bundle extras;
    Context context;
    Intent intent;
    static Cursor cursor;
    String flag = "";
    Intent intentPost;

    @Override
    public void onReceive(Context context, Intent intent) {

        if (!isNetworkAvailable(context)) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Could Not Tweet").setContentText("No Internet Connection Available").setAutoCancel(true);

            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationmanager.notify(0, builder.build());
        }
        else{
            extras = intent.getExtras();
            this.context=context;
            this.intent=intent;
            initTwitterConfig();
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            if(TextUtils.isEmpty(consumerKey)||TextUtils.isEmpty(consumerSecret)){
                return;
            }
            sharedPreferences=context.getSharedPreferences(PREF_NAME, 0);
            new Tweet().execute("" + status);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Tweeted").setContentText(status).setAutoCancel(true);

            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationmanager.notify(0, builder.build());
            cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, null, null, null);
            cursor.moveToFirst();
            cursor.move(14);
            check(cursor, context);
        }



    }

    private void initTwitterConfig() {
        String a=extras.getString("extras");
        String [] arr=a.split("---");
        status=arr[0];
        consumerKey = arr[1];
        consumerSecret = arr[2];
        callBackUrl = arr[3];
        oAuthVerifier = arr[4];
    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
    void check(Cursor cursor,Context context){
        flag = cursor.getString(cursor.getColumnIndex("state")).trim();
        if (flag.equalsIgnoreCase("true")) {
            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
            setStateToFalse(context,cursor.getString(cursor.getColumnIndex(Tasks.base)));
        } else if (flag.equalsIgnoreCase("action")) {
            setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
            setStateToFalse(context, cursor.getString(cursor.getColumnIndex(Tasks.base)));
        }
    }

    void schedule(String className, String extras, Context context) {
        try {
            intentPost = new Intent(context, Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        intentPost.putExtra("extras", "" + extras);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis() + 200;
        intentPost.setData(Uri.parse("myalarms://" + time));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }


    void setActions(String extras, String actions, Context context) {
        intentPost = new Intent(context, ActionsReceiver.class);
        intentPost.putExtra("extras", "" + extras);
        intentPost.putExtra("actions", "" + actions);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis() + 200;
        intentPost.setData(Uri.parse("myalarms://" + time));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    void setStateToFalse(Context context,String base){
        ContentValues values = new ContentValues();
        values.put(Tasks.base, base);
        values.put(Tasks.state, "false");
        String mSelectionClause = Tasks.base +  " LIKE ?";
        String[] mSelectionArgs = {base};
        int c = context.getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
    }

    class Tweet extends AsyncTask<String,String,Void> {

        @Override
        protected Void doInBackground(String... params) {
            String status = params[0];

            try{

                ConfigurationBuilder builder = new ConfigurationBuilder();
                builder.setOAuthConsumerKey(consumerKey);
                builder.setOAuthConsumerSecret(consumerSecret);

                String access_token = sharedPreferences.getString(PREF_KEY_OAUTH_TOKEN,"");
                String access_secret=sharedPreferences.getString(PREF_KEY_OAUTH_SECRET,"");

                AccessToken accessToken = new AccessToken(access_token,access_secret);

                Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);
                StatusUpdate statusUpdate = new StatusUpdate(status);

                twitter4j.Status response = twitter.updateStatus(statusUpdate);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }
}




