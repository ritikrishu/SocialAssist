package android.g38.sanyam.facebook;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.ForCp;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.CreateRecipeActivity;
import android.g38.socialassist.HomeActivity;
import android.g38.socialassist.R;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class FacebookActivity extends AppCompatActivity {

    LinearLayout layout;
    TextView tv;
    Bundle extras;
    String extra="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_then);
        extras = getIntent().getExtras();
        if (extras.getString("msg").equals("image")) {
            layout = (LinearLayout) findViewById(R.id.layout_image_pickerthen);
            layout.setVisibility(View.VISIBLE);
            Button pick = (Button) findViewById(R.id.bgallerythen);
            pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult((new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)), 10);

                }
            });

        } else {
            layout = (LinearLayout) findViewById(R.id.layout_generalthen);
            layout.setVisibility(View.VISIBLE);
            Button save = (Button) findViewById(R.id.bsavethen);
            final EditText input = (EditText) findViewById(R.id.etinputthen);
            TextView msg = (TextView) findViewById(R.id.tvmsgthen);
            msg.setText(extras.getString("msg"));

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    extra="https://" + input.getText().toString();
                    checkIfThen();

                }
            });
        }


    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);


        if (resultCode == RESULT_OK && reqCode == 10) {
            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(projection[0]);
            extra = cursor.getString(index);

            checkIfThen();
            cursor.close();

        }
    }

    void checkIfThen(){
        if(extras.getString("tag","").equals("then")){
            ForCp.saveToCp(getApplicationContext(), extra, "android.g38.sanyam.facebook.ScheduledPost");
            ForCp.checkLaunch(getApplicationContext());
            layout.setVisibility(View.GONE);
           launchHome();
        }else {
            String base="";
            if(extra.contains("https://"))
                base="link";
            else
                base="image";
            ForCp.setBase(getApplicationContext(), base);
            ForCp.setContent(getApplicationContext(), "android.g38.sanyam.facebook.ScheduledPost", extra);
           // ForCp.setStateToTrue(getApplicationContext(),base);
            launchCreate();
        }
    }
    void launchHome(){
        startActivity(new Intent(FacebookActivity.this, HomeActivity.class));
    }
    void launchCreate(){startActivity(new Intent(FacebookActivity.this, CreateRecipeActivity.class));}

//    Button image = (Button)findViewById(R.id.image);
//    image.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            startActivityForResult((new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)),Picked_Image);
//        }
//    });
//
//
//
//
//
//
//    Button button = (Button)findViewById(R.id.button);
//    button.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            intentPost= new Intent(MainActivity.this, ScheduledPost.class);
//            intentPost.putExtra("link","https://developers.facebook.com");
//            intentPost.putExtra("imagePath",""+imagePath);
//            AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//            Long time = new GregorianCalendar().getTimeInMillis()+60*1000;
//            alarmManager.set(AlarmManager.RTC_WAKEUP,time, PendingIntent.getBroadcast(getApplicationContext(), 1,
//                    intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
//        }
//    });
//}
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        //for image
//        if(resultCode==RESULT_OK && requestCode==Picked_Image){
//            Uri uri = data.getData();
//            String[] projection = {MediaStore.Images.Media.DATA};
//            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
//            cursor.moveToFirst();
//            int index = cursor.getColumnIndex(projection[0]);
//            imagePath = cursor.getString(index);
//            cursor.close();
//
//        }
//
//        callbackManager.onActivityResult(requestCode, resultCode, data);
//    }
}
