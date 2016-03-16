package android.g38.sanyam.twitter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.g38.socialassist.R;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.GregorianCalendar;

public class TweetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


     void tweetMessage(String status){
        Intent intentTweet= new Intent(TweetActivity.this, ScheduledTweet.class);
        intentTweet.putExtra("status",""+status);
        intentTweet.putExtra("consumerKey",""+getString(R.string.twitter_consumer_key));
        intentTweet.putExtra("consumerSecret",""+getString(R.string.twitter_consumer_secret));
        intentTweet.putExtra("callBackUrl",""+getString(R.string.twitter_callback));
        intentTweet.putExtra("oAuthVerifier",""+getString(R.string.twitter_oauth_verifier));
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis()+10*1000;
        alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(getApplicationContext(), 1,
                intentTweet, PendingIntent.FLAG_UPDATE_CURRENT));
    }


}
