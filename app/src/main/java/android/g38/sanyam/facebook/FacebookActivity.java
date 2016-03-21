package android.g38.sanyam.facebook;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.HomeActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class FacebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences forCp = getSharedPreferences("forCp", Context.MODE_PRIVATE);
        if(forCp.getBoolean("add",false)){
        ContentValues values = new ContentValues();
        values.put(Tasks.extras, "https://developers.facebook.com");
        values.put(Tasks.intent, "android.g38.sanyam.facebook.ScheduledPost");
        values.put(Tasks.base, forCp.getString("base",""));
        values.put(Tasks.state, "true");

            if(forCp.getBoolean("others",false)){
                values.put(Tasks.others, forCp.getString("data",""));
            }else{
                values.put(Tasks.others, "");
            }
        String mSelectionClause = Tasks.base +  " LIKE ?";
        String[] mSelectionArgs = {forCp.getString("base","")};

        int c = getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
            //uncomment <code>
            //  SharedPreferences.Editor editor = forCp.edit();
            //editor.putBoolean("adddone",true);
            //editor.commit();
            //
            // </code>

        startActivity(new Intent(FacebookActivity.this, HomeActivity.class));
    }
    }

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
