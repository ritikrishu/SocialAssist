package android.g38.socialassist;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
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

public class ChannelsActivity extends AppCompatActivity {

    CallbackManager callbackManager;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();

        setContentView(R.layout.activity_channels);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //facebook Login


        final LoginButton fbLoginButton = (LoginButton) findViewById(R.id.fb_login_button);
        fbLoginButton.setPublishPermissions("publish_actions");
        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(ChannelsActivity.this, "Connected via Facebook", Toast.LENGTH_SHORT).show();
                        TextView tv = (TextView)findViewById(R.id.tv);
                        tv.setVisibility(View.VISIBLE);
                        ImageView ivfb = (ImageView)findViewById(R.id.ivfb);
                        ivfb.setVisibility(View.VISIBLE);
                        sharedPreferences=getSharedPreferences("login", getApplicationContext().MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putBoolean("facebook",true);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}
