package android.g38.sanyam.facebook;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.Services.Notify;
import android.g38.sanyam.androidreceivers.ActionsReceiver;
import android.g38.sanyam.androidreceivers.CursorFunctions;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.HomeActivity;
import android.g38.socialassist.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 3/16/2016.
 */
public class ScheduledPost extends BroadcastReceiver
{

    Context context;
    Intent intent;
    Bundle extras;
    Notify notify;
    static Cursor cursor;
    String flag = "";
    Intent intentPost;
    String mSelectionClause = Tasks.base +  " LIKE ?";
    String[] mSelectionArgs = {""};

    @Override
    public void onReceive(Context context, Intent intent) {
        FacebookSdk.sdkInitialize(context);
        notify=new Notify(context);
        extras = intent.getExtras();
        CursorFunctions cursorFunctions = new CursorFunctions(context);
        if (!isNetworkAvailable(context)) {
            notify.buildNotification("Could Not Post On Facebook","No Internet Connection Available",extras.getString("rId")
                    ,extras.getString("extras"));
        }
        else{

            this.context=context;

            this.intent=intent;
            if (extras.getString("extras").contains("https://"))
                postLink();
            else
                postImage();

        }

        cursorFunctions.loadCursorForFb("link");
        cursorFunctions.loadCursorForFb("image");

    }



    void postLink(){
        ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(Uri.parse(extras.getString("extras"))).build();
        ShareApi.share(content, null);
        notify.buildNotification("Posted on Facebook",extras.getString("extras"),extras.getString("rId"),
                "Link Shared:\n"+extras.getString("extras"));
    }

    void postImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(extras.getString("extras"));
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent conten = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareApi.share(conten, null);
        notify.buildNotification("Posted on Facebook",extras.getString("extras"),extras.getString("rId"),
                "Photo Shared:\nPath:"+extras.getString("extras"));
    }
//
//    public void loadCursor(String base){
//
//        mSelectionArgs[0] = base;
//        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
//        if(!cursor.moveToFirst())
//            return;
//        do{
//            check(cursor);
//
//        }while (cursor.moveToNext());
//    }

    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }
//    void check(Cursor cursor){
//        flag = cursor.getString(cursor.getColumnIndex("state")).trim();
//        if (flag.equalsIgnoreCase("true")) {
//            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
//                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim());
//            setStateToFalse(context,cursor.getString(cursor.getColumnIndex(Tasks.base)));
//        } else if (flag.equalsIgnoreCase("action")) {
//            setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
//                    cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim());
//            setStateToFalse(context, cursor.getString(cursor.getColumnIndex(Tasks.base)));
//        }
//    }
//
//    void schedule(String className, String extras) {
//        try {
//            intentPost = new Intent(context, Class.forName(className));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        intentPost.putExtra("extras", "" + extras);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Long time = new GregorianCalendar().getTimeInMillis() + 200;
//        intentPost.setData(Uri.parse("myalarms://" + time));
//        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
//                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
//    }
//
//
//    void setActions(String extras, String actions) {
//        intentPost = new Intent(context, ActionsReceiver.class);
//        intentPost.putExtra("extras", "" + extras);
//        intentPost.putExtra("actions", "" + actions);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Long time = new GregorianCalendar().getTimeInMillis() + 200;
//        intentPost.setData(Uri.parse("myalarms://" + time));
//        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
//                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
//    }
//
//    void setStateToFalse(Context context,String base){
//        ContentValues values = new ContentValues();
//        values.put(Tasks.base, base);
//        values.put(Tasks.state, "false");
//        String mSelectionClause = Tasks.base +  " LIKE ?";
//        String[] mSelectionArgs = {base};
//        int c = context.getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
//    }

}

