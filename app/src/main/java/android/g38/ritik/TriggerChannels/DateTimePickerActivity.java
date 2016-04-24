package android.g38.ritik.TriggerChannels;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.ritik.Gmail.GmailActionsActivity;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.socialassist.CreateRecipeActivity;
import android.g38.socialassist.SelectTriggerActivity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.g38.socialassist.R;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateTimePickerActivity extends AppCompatActivity {
    private int mYear, mMonth, mDay, mHour, mMinute;
    final Calendar c = Calendar.getInstance();
    String datePicked="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pickDate();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }



    void pickDate(){
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        datePicked+=dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        pickTime();

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
        datePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    onBackPressed();
                }
            }
        });
    }

    void pickTime(){
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        datePicked+=" "+hourOfDay + ":" + minute;

                            //int diff=(hourOfDay-c.getTime().getHours())*60*60+(minute-c.getTime().getMinutes())*60;
                        int diff=(((hourOfDay*60+minute)-
                                (c.getTime().getHours()*60+c.getTime().getMinutes())))*30*1000;
                        setData(Long.valueOf(diff));


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
        timePickerDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (which == DialogInterface.BUTTON_NEGATIVE) {
                    onBackPressed();
                }
            }
        });
    }

    void setData(Long time){
        SharedPreferences sharedPreferences=getSharedPreferences("time",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("alarm",""+time);
        editor.apply();
        ForCp.setBase(getApplicationContext(), "time");
        RecipeCP.setIf(getApplicationContext(),""+R.drawable.ic_dateandtime_channel,"Its "+datePicked,datePicked);
        startActivity(new Intent(getApplicationContext(), CreateRecipeActivity.class));
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
