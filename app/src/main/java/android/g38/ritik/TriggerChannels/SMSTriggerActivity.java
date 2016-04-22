package android.g38.ritik.TriggerChannels;

import android.content.Intent;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.sanyam.facebook.FacebookActivity;
import android.g38.socialassist.CreateRecipeActivity;
import android.g38.socialassist.UserInput;
import android.g38.socialassist.UserInputThen;
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

public class SMSTriggerActivity extends AppCompatActivity {

    Button newSms,newSmsString,newSmsNumber, btnSendSMS;
    LinearLayout llAction, llTrigger;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smstrigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        llAction = (LinearLayout)findViewById(R.id.llAction);
        llTrigger = (LinearLayout)findViewById(R.id.llTrigger);
        decideLayout();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btnSendSMS = (Button)findViewById(R.id.btnSMSSend);
        newSms=(Button)findViewById(R.id.btnANSR);
        newSmsString=(Button)findViewById(R.id.btnNSRMS);
        newSmsNumber=(Button)findViewById(R.id.btnNSRFPN);

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecipeCP.setThen(getApplicationContext(), "" + R.drawable.ic_sms_channel, btnSendSMS.getText().toString());
                Intent intent = new Intent(SMSTriggerActivity.this, UserInputThen.class);
                intent.putExtra("msg", "sms");
                startActivity(intent);

            }
        });

        newSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "newSms");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_sms_channel, newSms.getText().toString(),"Received New Message.");
                startActivity(new Intent(SMSTriggerActivity.this, CreateRecipeActivity.class));

            }
        });

        newSmsString.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "newSmsString");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_sms_channel, newSmsString.getText().toString(),"Received New Message" +
                        "Containig ");
                Intent intent = new Intent(SMSTriggerActivity.this, UserInput.class);
                intent.putExtra("msg", "Enter Message to Search");
                startActivity(intent);

            }
        });

        newSmsNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ForCp.setBase(getApplicationContext(), "newSmsNumber");
                RecipeCP.setIf(getApplicationContext(), "" + R.drawable.ic_sms_channel, newSmsNumber.getText().toString(),
                        "Received new message from ");
                Intent intent = new Intent(SMSTriggerActivity.this, UserInput.class);
                intent.putExtra("msg", "Enter Number to Search");
                startActivity(intent);

            }
        });
    }
    private void decideLayout(){
        if(getIntent().getBooleanExtra("IF",true)){
            llTrigger.setVisibility(View.VISIBLE);
            llAction.setVisibility(View.GONE);
        }
        else{
            llTrigger.setVisibility(View.GONE);
            llAction.setVisibility(View.VISIBLE);
        }
    }
}
