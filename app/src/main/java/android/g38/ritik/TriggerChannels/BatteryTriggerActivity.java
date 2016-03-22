package android.g38.ritik.TriggerChannels;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.LinearLayout;

public class BatteryTriggerActivity extends AppCompatActivity {

    Button pluggedIn,pluggedOut,below15;
    SharedPreferences forCp ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pluggedIn=(Button)findViewById(R.id.btnPlugged);
        pluggedOut=(Button)findViewById(R.id.btnUnplugged);
        below15=(Button)findViewById(R.id.btnBtryCritical);
        forCp= getSharedPreferences("forCp", Context.MODE_PRIVATE);

        pluggedIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "pluggedIn");
                startActivity(new Intent(BatteryTriggerActivity.this,FacebookActivity.class));
            }
        });

        pluggedOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "pluggedOut");
                startActivity(new Intent(BatteryTriggerActivity.this,FacebookActivity.class));
            }
        });

        below15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "below15");
                startActivity(new Intent(BatteryTriggerActivity.this,FacebookActivity.class));
            }
        });
    }

//    void setBase(String base){
//        if(!(forCp.getBoolean("addDone",false))){
//            SharedPreferences.Editor editor=forCp.edit();
//            editor.clear();
//            editor.putBoolean("add", true);
//            editor.putString("base",base);
//            editor.commit();
//            startActivity(new Intent(BatteryTriggerActivity.this,FacebookActivity.class));
//        }
//    }

}
