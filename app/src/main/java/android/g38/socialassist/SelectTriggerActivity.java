package android.g38.socialassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.g38.ritik.TriggerChannels.BatteryTriggerActivity;
import android.g38.ritik.TriggerChannels.SMSTriggerActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class SelectTriggerActivity extends AppCompatActivity {

    private ImageView ivBattery, ivFacebook, ivTwitter, ivDevice, ivDatenTime, ivSms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ivBattery = (ImageView)findViewById(R.id.ivBattery);
        ivDatenTime = (ImageView)findViewById(R.id.ivDatenTime);
        ivDevice = (ImageView)findViewById(R.id.ivDevice);
        ivFacebook = (ImageView)findViewById(R.id.ivFacebookChannel);
        ivTwitter = (ImageView)findViewById(R.id.ivTwitterChannel);
        ivSms = (ImageView)findViewById(R.id.ivSms);
        ivBattery.setOnClickListener(new OnclickAction());
        ivDatenTime.setOnClickListener(new OnclickAction());
        ivDevice.setOnClickListener(new OnclickAction());
        ivFacebook.setOnClickListener(new OnclickAction());
        ivTwitter.setOnClickListener(new OnclickAction());
        ivSms.setOnClickListener(new OnclickAction());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    class OnclickAction implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivBattery :
                    startActivity(new Intent(SelectTriggerActivity.this, BatteryTriggerActivity.class));
                    break;
                case R.id.ivDatenTime :

                    break;
                case R.id.ivDevice :

                    break;
                case R.id.ivFacebookChannel :

                    break;
                case R.id.ivTwitterChannel :

                    break;
                case R.id.ivSms :
                    startActivity(new Intent(SelectTriggerActivity.this, SMSTriggerActivity.class));
                    break;
                default:

            }
        }
    }
}

