package android.g38.sanyam.androidreceivers;

/**
 * Created by SANYAM TYAGI on 3/22/2016.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.Tasks;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;
import android.widget.Toast;

import java.util.GregorianCalendar;

public class ReadMessages extends BroadcastReceiver {

    static Cursor cursor;
    String newSmsFlag = "";
    String newSmsStringFlag = "";
    String newSmsNumberFlag = "";
    Intent intentPost;
    final SmsManager sms = SmsManager.getDefault();

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, null, null, null);
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();

                    cursor.moveToFirst();
                    cursor.move(3);
                    newSmsFlag = cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
                    if(newSmsFlag.equalsIgnoreCase("true")){

                        schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                                cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),context);
                    }
                    cursor.moveToNext();
                    newSmsStringFlag=cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
                    if(newSmsStringFlag.equalsIgnoreCase("true")){
                        if(message.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))){

                            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),context);
                        }
                    }
                    cursor.moveToNext();
                    newSmsNumberFlag=cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
                    if(newSmsNumberFlag.equalsIgnoreCase("true")){
                        if(senderNum.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))){

                            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),context);
                        }
                    }

                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }
    void schedule(String className,String extras,Context context){
        try {
            intentPost = new Intent(context, Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        intentPost.putExtra("extras", "" + extras);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis() + 500;
        intentPost.setData(Uri.parse("myalarms://" + Math.random()));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}