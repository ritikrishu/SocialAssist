package android.g38.sanyam.facebook;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.GregorianCalendar;

public class FacebookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
