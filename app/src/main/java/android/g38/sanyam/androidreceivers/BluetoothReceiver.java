package android.g38.sanyam.androidreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.Tasks;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 3/23/2016.
 */
public class BluetoothReceiver extends BroadcastReceiver {
    static Cursor cursor;
    String flag = "";
    Intent intentPost;

    @Override
    public void onReceive(Context context, Intent intent) {
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, null, null, null);
        final String action = intent.getAction();

        if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
            final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, BluetoothAdapter.ERROR);
            switch(state) {
                case BluetoothAdapter.STATE_OFF:
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:

                    break;
                case BluetoothAdapter.STATE_ON:

                    break;
                case BluetoothAdapter.STATE_TURNING_ON:

                    break;
            }

        }



        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {

            //BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
            //device.getName()
            cursor.moveToFirst();
            cursor.move(10);
            check(cursor,context);

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
            cursor.moveToFirst();
            cursor.move(11);
            check(cursor,context);

        }
    }

    void check(Cursor cursor,Context context){
        flag = cursor.getString(cursor.getColumnIndex("state")).trim();
        if (flag.equalsIgnoreCase("true")) {
            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
        } else if (flag.equalsIgnoreCase("action")) {
            setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
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
        intentPost.setData(Uri.parse("myalarms://" + time));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    void setActions(String extras, String actions, Context context) {
        intentPost = new Intent(context, ActionsReceiver.class);
        intentPost.putExtra("extras", "" + extras);
        intentPost.putExtra("actions", "" + actions);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis() + 200;
        intentPost.setData(Uri.parse("myalarms://" + time));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }
}
