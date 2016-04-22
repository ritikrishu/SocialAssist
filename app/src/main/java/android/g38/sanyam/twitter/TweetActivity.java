package android.g38.sanyam.twitter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.g38.ritik.TriggerChannels.TwitterTriggerActivity;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.socialassist.CreateRecipeActivity;
import android.g38.socialassist.HomeActivity;
import android.g38.socialassist.R;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.GregorianCalendar;

public class TweetActivity extends AppCompatActivity {

    LinearLayout layout;
    TextView tv;
    Bundle extras;
    static String extra="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_then);
        extras = getIntent().getExtras();
        layout = (LinearLayout) findViewById(R.id.layout_generalthen);
        layout.setVisibility(View.VISIBLE);
        Button save = (Button) findViewById(R.id.bsavethen);
        final EditText input = (EditText) findViewById(R.id.etinputthen);
        TextView msg = (TextView) findViewById(R.id.tvmsgthen);
        msg.setText(extras.getString("msg"));

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                extra=input.getText().toString();
                createExtra(extra);
                checkIfThen();

            }
        });

    }
    void checkIfThen(){
        if(extras.getString("tag","").equals("then")){
            ForCp.saveToCp(getApplicationContext(), extra, "android.g38.sanyam.twitter.ScheduledTweet");
            ForCp.checkLaunch(getApplicationContext());
            layout.setVisibility(View.GONE);
            launchHome();
        }else {

            ForCp.setBase(getApplicationContext(), "tweet");
            ForCp.setContent(getApplicationContext(), "android.g38.sanyam.twitter.ScheduledTweet", extra);
            RecipeCP.setExtra(getApplication(),extra);
            // ForCp.setStateToTrue(getApplicationContext(),base);
            launchCreate();
        }
    }
    void launchHome(){
        startActivity(new Intent(TweetActivity.this, HomeActivity.class));
    }
    void launchCreate(){startActivity(new Intent(TweetActivity.this, CreateRecipeActivity.class));}

    void createExtra(String status){
        extra+="---"+getString(R.string.twitter_consumer_key)+
                    "---"+getString(R.string.twitter_consumer_secret)+
                        "---"+getString(R.string.twitter_callback)+
                             "---"+getString(R.string.twitter_oauth_verifier);
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
