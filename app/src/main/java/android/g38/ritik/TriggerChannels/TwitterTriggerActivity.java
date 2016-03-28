package android.g38.ritik.TriggerChannels;

import android.content.Intent;
import android.g38.sanyam.facebook.FacebookActivity;
import android.g38.sanyam.twitter.TweetActivity;
import android.g38.socialassist.R;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class TwitterTriggerActivity extends AppCompatActivity {

    TextView tvTwitter;
    Button tweet;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tvTwitter=(TextView)findViewById(R.id.tvtwitter);
        tweet=(Button)findViewById(R.id.btnTweet);
        intent = new Intent(TwitterTriggerActivity.this, TweetActivity.class);
        intent.putExtra("msg", "Enter Tweet To Share");
        decideLayout();
        tweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
    }
    private void decideLayout(){
        if(getIntent().getBooleanExtra("IF",true)) {
            tvTwitter.setText(R.string.title_activity_select_trigger);
            tweet.setText("New tweet by you");

        }
        else{
            tvTwitter.setText(R.string.select_action);
            tweet.setText("tweet by you");
            intent.putExtra("tag", "then");

        }
    }

}
