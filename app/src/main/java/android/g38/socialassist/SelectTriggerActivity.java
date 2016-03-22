package android.g38.socialassist;

import android.content.DialogInterface;
import android.content.Intent;
import android.g38.ritik.TriggerChannels.BatteryTriggerActivity;
import android.g38.ritik.TriggerChannels.DateTimePickerActivity;
import android.g38.ritik.TriggerChannels.DeviceTriggerActivity;
import android.g38.ritik.TriggerChannels.SMSTriggerActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class SelectTriggerActivity extends AppCompatActivity {

    private ImageView ivBattery, ivFacebook, ivTwitter, ivDevice, ivDatenTime, ivSms;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_trigger);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivBattery = (ImageView)findViewById(R.id.ivBattery);
        ivDatenTime = (ImageView)findViewById(R.id.ivDatenTime);
        ivDevice = (ImageView)findViewById(R.id.ivDevice);
        ivFacebook = (ImageView)findViewById(R.id.ivFacebookChannel);
        ivTwitter = (ImageView)findViewById(R.id.ivTwitterChannel);
        ivSms = (ImageView)findViewById(R.id.ivSms);
        decideLayout();
        setSupportActionBar(toolbar);
        ivBattery.setOnClickListener(new OnclickAction());
        ivDatenTime.setOnClickListener(new OnclickAction());
        ivDevice.setOnClickListener(new OnclickAction());
        ivFacebook.setOnClickListener(new OnclickAction());
        ivTwitter.setOnClickListener(new OnclickAction());
        ivSms.setOnClickListener(new OnclickAction());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void decideLayout(){
        if(getIntent().getBooleanExtra("IF",true))
            toolbar.setTitle("Select Trigger");
        else {
            toolbar.setTitle("Select Action");
            ivBattery.setVisibility(View.GONE);
            ivDatenTime.setVisibility(View.GONE);
        }
    }
    class OnclickAction implements View.OnClickListener {

        Intent intent;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.ivBattery :
                    intent = new Intent(SelectTriggerActivity.this, BatteryTriggerActivity.class);
                    intent.putExtra("IF",getIntent().getBooleanExtra("IF", true));
                    startActivity(intent);
                    break;
                case R.id.ivDatenTime :
                    intent = new Intent(SelectTriggerActivity.this, DateTimePickerActivity.class);
                    intent.putExtra("IF",getIntent().getBooleanExtra("IF", true));
                    startActivity(intent);
                    break;
                case R.id.ivDevice :
                    intent = new Intent(SelectTriggerActivity.this, DeviceTriggerActivity.class);
                    intent.putExtra("IF",getIntent().getBooleanExtra("IF", true));
                    startActivity(intent);
                    break;
                case R.id.ivFacebookChannel :

                    break;
                case R.id.ivTwitterChannel :

                    break;
                case R.id.ivSms :
                    intent = new Intent(SelectTriggerActivity.this, SMSTriggerActivity.class);
                    intent.putExtra("IF",getIntent().getBooleanExtra("IF", true));
                    startActivity(intent);
                    break;
                default:

            }
        }
    }
}

