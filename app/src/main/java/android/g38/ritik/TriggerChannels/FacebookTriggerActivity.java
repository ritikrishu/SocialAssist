package android.g38.ritik.TriggerChannels;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.g38.socialassist.R;
import android.widget.Button;
import android.widget.LinearLayout;

public class FacebookTriggerActivity extends AppCompatActivity {

    LinearLayout llAction, llTrigger;
    Button btnShareLink, btnSharePhoto, btnNewSharePhoto, btnNewShareLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_trigger);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        llAction = (LinearLayout)findViewById(R.id.llAction);
        llTrigger = (LinearLayout)findViewById(R.id.llTrigger);
        decideLayout();
        btnNewShareLink = (Button)findViewById(R.id.btnNewShareLink);
        btnNewSharePhoto = (Button)findViewById(R.id.btnNewSharePhoto);
        btnShareLink = (Button)findViewById(R.id.btnShareLink);
        btnSharePhoto = (Button)findViewById(R.id.btnSharePhoto);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        closeOptionsMenu();
        btnSharePhoto.setOnClickListener(new onClickAction());
        btnShareLink.setOnClickListener(new onClickAction());
        btnNewSharePhoto.setOnClickListener(new onClickAction());
        btnNewShareLink.setOnClickListener(new onClickAction());
    }
    private void decideLayout(){
        if(getIntent().getBooleanExtra("IF",true)) {
            llAction.setVisibility(View.GONE);
            llTrigger.setVisibility(View.VISIBLE);
        }
        else{
            llAction.setVisibility(View.VISIBLE);
            llTrigger.setVisibility(View.GONE);
        }
    }
    class onClickAction implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            if(v.getId()==R.id.btnNewShareLink){
                //Trigger button on new link shared by user


            }
            else if(v.getId()==R.id.btnNewSharePhoto){
                //Trigger button on new photo shared by user


            }
            else if(v.getId()==R.id.btnShareLink){
                //Action button to share a new link


            }
            else if(v.getId()==R.id.btnSharePhoto){
                //Action button to share a new photo


            }
        }
    }
}
