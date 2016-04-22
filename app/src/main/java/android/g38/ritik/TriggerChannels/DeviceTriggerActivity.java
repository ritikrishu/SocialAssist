package android.g38.ritik.TriggerChannels;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.contentprovider.RecipeCP;
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
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_device_channel, cAny.getText().toString(),
                        "Connected to a Wifi network");
                launchCreate();
            }
        });


        dAny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "dAny");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_device_channel, dAny.getText().toString(),
                        "Disconnected from a Wifi network");
                launchCreate();
            }
        });


        cSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "cSpecific");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_device_channel, cSpecific.getText().toString(),"Connected to ");
                Intent intent = new Intent(DeviceTriggerActivity.this, UserInput.class);
                intent.putExtra("msg", "Enter Wifi Name");
                startActivity(intent);

            }
        });


        dSpecific.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "dSpecific");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_device_channel, dSpecific.getText().toString(),"Disconnected from ");
                Intent intent = new Intent(DeviceTriggerActivity.this, UserInput.class);
                intent.putExtra("msg", "Enter Wifi Name");
                startActivity(intent);

            }
        });


        cBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "cBlue");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_device_channel, cBlue.getText().toString(),"Connected to a " +
                        "bluetooth device.");
                launchCreate();
            }
        });


        dBlue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "dBlue");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_device_channel, dBlue.getText().toString(),
                        "Disconnected from a bluetooth device.");
                launchCreate();
            }
        });



    }

    void thenChannel(){
        final Button onWifi=(Button)findViewById(R.id.btnWifiOn);
        final Button offWifi=(Button)findViewById(R.id.btnWifiOff);
        final Button mute=(Button)findViewById(R.id.btnMuteRingtone);
        final Button setVolume=(Button)findViewById(R.id.btnSetRingVolume);
        final Button onBluetooth=(Button)findViewById(R.id.btnBluetoothOn);
        final Button launchMaps=(Button)findViewById(R.id.btnMapsLaunch);
        final Button setWallpaper=(Button)findViewById(R.id.btnUpdateWallpaper);

        onWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, onWifi.getText().toString());
                ForCp.saveToCpForAction(getApplicationContext(), "onw");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        offWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, offWifi.getText().toString());
                ForCp.saveToCpForAction(getApplicationContext(), "ofw");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        mute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, mute.getText().toString());
                ForCp.saveToCpForAction(getApplicationContext(), "mut");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        onBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, onBluetooth.getText().toString());
                ForCp.saveToCpForAction(getApplicationContext(), "onb");
                ForCp.checkLaunch(getApplicationContext());
                launchHome();
            }
        });
        setVolume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, setVolume.getText().toString());
                Intent intent = new Intent(DeviceTriggerActivity.this, UserInputThen.class);
                intent.putExtra("msg", "Enter Ringer Volume");
                intent.putExtra("tag", "vol");
                startActivity(intent);

            }
        });
        setWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, setWallpaper.getText().toString());

                Intent intent = new Intent(DeviceTriggerActivity.this, UserInputThen.class);
                intent.putExtra("msg", "image");
                intent.putExtra("tag", "wal");
                startActivity(intent);

            }
        });
        launchMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_device_channel, launchMaps.getText().toString());
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
