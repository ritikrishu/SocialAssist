package android.g38.ritik.TriggerChannels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.facebook.FacebookActivity;
import android.g38.socialassist.CreateRecipeActivity;
import android.g38.socialassist.HomeActivity;
import android.g38.socialassist.UserInput;
import android.g38.socialassist.UserInputThen;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.g38.socialassist.R;
import android.widget.Button;
import android.widget.LinearLayout;

public class DeviceTriggerActivity extends AppCompatActivity {

    LinearLayout llAction, llTrigger;
    SharedPreferences forCp ;
    static Button cAny,dAny,cSpecific,dSpecific,cBlue,dBlue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llAction = (LinearLayout) findViewById(R.id.llAction);
        llTrigger = (LinearLayout) findViewById(R.id.llTrigger);
        forCp= getSharedPreferences("forCp", Context.MODE_PRIVATE);
        decideLayout();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void decideLayout(){
        if(getIntent().getBooleanExtra("IF",true)) {
            llTrigger.setVisibility(View.VISIBLE);
            llAction.setVisibility(View.GONE);
            ifChannel();
        }
        else{
            llTrigger.setVisibility(View.GONE);
            llAction.setVisibility(View.VISIBLE);
            thenChannel();
        }
    }

    void ifChannel(){
        cAny =(Button) findViewById(R.id.btnCAny);
        dAny =(Button) findViewById(R.id.btnDAny);
        cSpecific =(Button) findViewById(R.id.btnCSpecific);
        dSpecific =(Button) findViewById(R.id.btnDSpecific);
        cBlue =(Button) findViewById(R.id.btnConnectbt);
        dBlue =(Button) findViewById(R.id.btnDisConnectbt);

        cAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "cAny");
                launchCreate();
            }
        });


        dAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "dAny");
                launchCreate();
            }
        });


        cSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "cSpecific");
                Intent intent = new Intent(DeviceTriggerActivity.this, UserInput.class);
                intent.putExtra("msg", "Enter Wifi Name");
                startActivity(intent);

            }
        });


        dSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "dSpecific");
                Intent intent = new Intent(DeviceTriggerActivity.this, UserInput.class);
                intent.putExtra("msg", "Enter Wifi Name");
                startActivity(intent);

            }
        });


        cBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "cBlue");
                launchCreate();
            }
        });


        dBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "dBlue");
                launchCreate();
            }
        });



    }

    void thenChannel(){
        Button onWifi=(Button)findViewById(R.id.btnWifiOn);
        Button offWifi=(Button)findViewById(R.id.btnWifiOff);
        Button mute=(Button)findViewById(R.id.btnMuteRingtone);
        Button setVolume=(Button)findViewById(R.id.btnSetRingVolume);
        Button onBluetooth=(Button)findViewById(R.id.btnBluetoothOn);
        Button launchMaps=(Button)findViewById(R.id.btnMapsLaunch);
        Button setWallpaper=(Button)findViewById(R.id.btnUpdateWallpaper);

        onWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.saveToCpForAction(getApplicationContext(), "onw");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        offWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.saveToCpForAction(getApplicationContext(), "ofw");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.saveToCpForAction(getApplicationContext(), "mut");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        onBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.saveToCpForAction(getApplicationContext(), "onb");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        setVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DeviceTriggerActivity.this, UserInputThen.class);
                intent.putExtra("msg", "Enter Ringer Volume");
                intent.putExtra("tag", "vol");
                startActivity(intent);

            }
        });
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(DeviceTriggerActivity.this, UserInputThen.class);
                intent.putExtra("msg", "image");
                intent.putExtra("tag", "wal");
                startActivity(intent);

            }
        });
        launchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.saveToCpForAction(getApplicationContext(), "map");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });

    }
    void launchHome(){
        startActivity(new Intent(DeviceTriggerActivity.this, HomeActivity.class));
    }
    void launchCreate(){startActivity(new Intent(DeviceTriggerActivity.this, CreateRecipeActivity.class));}
}
