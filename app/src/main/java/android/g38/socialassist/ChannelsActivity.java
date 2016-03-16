package android.g38.socialassist;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.g38.sanyam.twitter.TweetActivity;
import android.g38.sanyam.twitter.WebViewActivity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ChannelsActivity extends AppCompatActivity {

    private static CallbackManager callbackManager;
    private static SharedPreferences sharedPreferencesFb;
    private static String PREF_NAME="SAMPLE_TWITTER_PREF";
    private static String PREF_KEY_OAUTH_TOKEN="OAUTH_TOKEN";
    private static String PREF_KEY_OAUTH_SECRET="OAUTH_SECRET";
    private static String PREF_KEY_TWITTER_LOGIN="IS_TWITTER_LOGED_IN";
    private static String PREF_USER_NAME="USER_NAME";
    public static final int WEB_VIEW_REQUEST_CODE=100;
    private static SharedPreferences sharedPreferences;
    private static Twitter twitter;
    private static RequestToken requestToken;
    private static String consumerKey=null;
    private static String consumerSecret=null;
    private static String callBackUrl=null;
    private static String oAuthVerifier=null;
    private static Button twitterLoginButton;
    private ImageView ivtwitter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        facebookLogin();
        twitterLogin();
    }

    //facebook function
    private void facebookLogin(){
        final LoginButton fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        final ImageView ivfb = (ImageView) findViewById(R.id.ivfb);
        sharedPreferencesFb = getSharedPreferences("login", getApplicationContext().MODE_PRIVATE);
        if(sharedPreferencesFb.getBoolean("facebook",false)) {
            ivfb.setVisibility(View.VISIBLE);
            fbLoginButton.setVisibility(View.GONE);
        }
        fbLoginButton.setPublishPermissions("publish_actions");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(ChannelsActivity.this, "Connected via Facebook", Toast.LENGTH_SHORT).show();
                        TextView tv = (TextView) findViewById(R.id.tv);
                        tv.setVisibility(View.VISIBLE);
                        ivfb.setVisibility(View.VISIBLE);
                        sharedPreferencesFb = getSharedPreferences("login", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferencesFb.edit();
                        editor.putBoolean("facebook", true);
                        editor.commit();
                        fbLoginButton.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(ChannelsActivity.this, "cancel", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(ChannelsActivity.this, "error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //twitter functions
    private void twitterLogin(){
        initTwitterConfig();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        ivtwitter=(ImageView)findViewById(R.id.ivtwitter);
        twitterLoginButton=(Button)findViewById(R.id.b_twitter_login);
        twitterLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginToTwitter();

            }
        });

        if(TextUtils.isEmpty(consumerKey)||TextUtils.isEmpty(consumerSecret)){
            return;
        }

        sharedPreferences=getSharedPreferences(PREF_NAME,0);
        boolean isLoggedIn = sharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN,false);

        if (isLoggedIn){
            twitterLoginButton.setVisibility(View.GONE);
            TextView tv = (TextView) findViewById(R.id.tv);
            tv.setVisibility(View.VISIBLE);
            ivtwitter.setVisibility(View.VISIBLE);


        }
        else{
            twitterLoginButton.setVisibility(View.VISIBLE);
            ivtwitter.setVisibility(View.GONE);

            Uri uri=getIntent().getData();
            if(uri!=null&&uri.toString().startsWith(callBackUrl)){
                String verifier=uri.getQueryParameter(oAuthVerifier);

                try{
                    AccessToken accessToken=twitter.getOAuthAccessToken(requestToken, verifier);

                    saveTwitterInfo(accessToken);

                    twitterLoginButton.setVisibility(View.GONE);
                    ivtwitter.setVisibility(View.VISIBLE);
                    TextView tv = (TextView) findViewById(R.id.tv);
                    tv.setVisibility(View.VISIBLE);

                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }

    private void initTwitterConfig() {
        consumerKey = getString(R.string.twitter_consumer_key);
        consumerSecret = getString(R.string.twitter_consumer_secret);
        callBackUrl = getString(R.string.twitter_callback);
        oAuthVerifier = getString(R.string.twitter_oauth_verifier);
    }


    private void saveTwitterInfo(AccessToken accessToken){
        long userId=accessToken.getUserId();
        final User user;
        try{
            user = twitter.showUser(userId);
            String userName=user.getName();

            SharedPreferences.Editor e= sharedPreferences.edit();
            e.putString(PREF_KEY_OAUTH_TOKEN,accessToken.getToken());
            e.putString(PREF_KEY_OAUTH_SECRET,accessToken.getTokenSecret());
            e.putBoolean(PREF_KEY_TWITTER_LOGIN, true);
            e.putString(PREF_USER_NAME, userName);
            e.commit();
            sharedPreferencesFb = getSharedPreferences("login", getApplicationContext().MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferencesFb.edit();
            editor.putBoolean("twitter", true);
            editor.commit();
        }
        catch (TwitterException e){
            e.printStackTrace();
        }
    }

    private void loginToTwitter(){
        boolean isLoggedIn = sharedPreferences.getBoolean(PREF_KEY_TWITTER_LOGIN,false);
        if(!isLoggedIn){
            final ConfigurationBuilder builder = new ConfigurationBuilder();
            builder.setOAuthConsumerKey(consumerKey);
            builder.setOAuthConsumerSecret(consumerSecret);

            final Configuration configuration = builder.build();
            final TwitterFactory factory=new TwitterFactory(configuration);
            twitter=factory.getInstance();

            try{
                requestToken=twitter.getOAuthRequestToken(callBackUrl);

                final Intent intent = new Intent(ChannelsActivity.this,WebViewActivity.class);
                intent.putExtra(WebViewActivity.EXTRA_URL,requestToken.getAuthenticationURL());
                startActivityForResult(intent,WEB_VIEW_REQUEST_CODE);

            }catch (TwitterException e){
                e.printStackTrace();
            }

        }
        else {
            twitterLoginButton.setVisibility(View.GONE);
            ivtwitter.setVisibility(View.VISIBLE);
            TextView tv = (TextView) findViewById(R.id.tv);
            tv.setVisibility(View.VISIBLE);
        }
    }



    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if(resultCode== Activity.RESULT_OK && requestCode==WEB_VIEW_REQUEST_CODE){
            String verifier = data.getExtras().getString(oAuthVerifier);
            try {

                AccessToken accessToken=twitter.getOAuthAccessToken(requestToken,verifier);

                saveTwitterInfo(accessToken);

                twitterLoginButton.setVisibility(View.GONE);
                ivtwitter.setVisibility(View.VISIBLE);

            }catch (Exception e){
                e.printStackTrace();
            }

    }

}
}
