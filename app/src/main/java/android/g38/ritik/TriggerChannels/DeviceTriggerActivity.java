package android.g38.ritik.TriggerChannels;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.g38.socialassist.R;
import android.widget.LinearLayout;

public class DeviceTriggerActivity extends AppCompatActivity {

    LinearLayout llAction, llTrigger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llAction = (LinearLayout) findViewById(R.id.llAction);
        llTrigger = (LinearLayout) findViewById(R.id.llTrigger);
        decideLayout();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void decideLayout(){
        if(getIntent().getBooleanExtra("IF",true)) {
            llTrigger.setVisibility(View.VISIBLE);
            llAction.setVisibility(View.GONE);
        }
        else{
            llTrigger.setVisibility(View.GONE);
            llAction.setVisibility(View.VISIBLE);
        }
    }
}
