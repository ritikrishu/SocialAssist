package android.g38.sanyam.twitter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;

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

    private Bundle extras;

    @Override
    public void onReceive(Context context, Intent intent) {

        extras=intent.getExtras();
        initTwitterConfig();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        if(TextUtils.isEmpty(consumerKey)||TextUtils.isEmpty(consumerSecret)){
            return;
        }
        sharedPreferences=context.getSharedPreferences(PREF_NAME, 0);
        new Tweet().execute(""+extras.getString("status"));

    }

    private void initTwitterConfig() {
        consumerKey = extras.getString("consumerKey");
        consumerSecret = extras.getString("consumerSecret");
        callBackUrl = extras.getString("callBackUrl");
        oAuthVerifier = extras.getString("oAuthVerifier");
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




