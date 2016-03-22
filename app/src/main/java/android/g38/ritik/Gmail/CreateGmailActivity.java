package android.g38.ritik.Gmail;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.socialassist.HomeActivity;
import android.g38.socialassist.R;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;



import java.util.GregorianCalendar;

public class CreateGmailActivity extends AppCompatActivity {

    EditText to, sub, body;
    Button btnContinue;
    static boolean workDone = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_gmail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        btnContinue = (Button)findViewById(R.id.btnContinue);
        to = (EditText) findViewById(R.id.etAddress);
        sub = (EditText) findViewById(R.id.etSubject);
        body = (EditText) findViewById(R.id.etBody);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (to.getText().toString() == null)
                    Toast.makeText(CreateGmailActivity.this, "Email address not valid", Toast.LENGTH_SHORT).show();
                else if (sub.getText().toString() == null)
                    Toast.makeText(CreateGmailActivity.this, "Subject is empty", Toast.LENGTH_SHORT).show();
                else if (body.getText().toString() == null)
                    Toast.makeText(CreateGmailActivity.this, "Body is empty", Toast.LENGTH_SHORT).show();
                else {
                    SharedPreferences settings = getSharedPreferences("accountName", MODE_PRIVATE);
                    Intent intent = new Intent(CreateGmailActivity.this, ScheduleMail.class);
                    intent.putExtra("to", to.getText().toString());
                    intent.putExtra("sub", sub.getText().toString());
                    intent.putExtra("body", body.getText().toString());
                   // intent.putExtra("accountName", settings.getString("accountName", null));
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                    Long time = new GregorianCalendar().getTimeInMillis() + 10 * 1000;
                    alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(CreateGmailActivity.this, 1,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT));
                    workDone = true;
                    startActivity(new Intent(CreateGmailActivity.this, HomeActivity.class));
                    Toast.makeText(CreateGmailActivity.this, "Mail successfully scheduled", Toast.LENGTH_LONG).show();
                }
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onPause() {
        if(workDone)
            finish();
        super.onPause();
    }

    @Override
    protected void onResume() {
        workDone = false;
        super.onResume();
    }
}
