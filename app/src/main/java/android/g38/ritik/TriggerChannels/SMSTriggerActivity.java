package android.g38.ritik.TriggerChannels;

import android.content.Intent;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.facebook.FacebookActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.g38.socialassist.R;
import android.widget.Button;

public class SMSTriggerActivity extends AppCompatActivity {

    Button newSms,newSmsString,newSmsNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smstrigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        newSms=(Button)findViewById(R.id.btnANSR);
        newSmsString=(Button)findViewById(R.id.btnNSRMS);
        newSmsNumber=(Button)findViewById(R.id.btnNSRFPN);


        newSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "newSms");
                startActivity(new Intent(SMSTriggerActivity.this, FacebookActivity.class));
            }
        });

        newSmsString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "newSmsString");
                ForCp.setOthers(getApplicationContext(), "message");
                startActivity(new Intent(SMSTriggerActivity.this,FacebookActivity.class));
            }
        });

        newSmsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "newSmsNumber");
                ForCp.setOthers(getApplicationContext(), "8527311497");
                startActivity(new Intent(SMSTriggerActivity.this,FacebookActivity.class));
            }
        });
    }
}
