package android.g38.sanyam.androidreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.Tasks;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 3/23/2016.
 */
public class WifiReceiver extends BroadcastReceiver {

    static Cursor cursor;
    String cAnyFlag = "";
    String dAnyFlag = "";
    String cSpecificFlag = "";
    String dSpecificFlag = "";
    Intent intentPost;
    static String id = "";
    Boolean flag=true;

    @Override
    public void onReceive(Context context, Intent intent) {
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, null, null, null);
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        //WifiManager.NETWORK_STATE_CHANGED_ACTION

        if (info != null && info.isConnected()) {
            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            id = wifiInfo.getSSID();
            flag=true;
            cursor.moveToFirst();
            cursor.move(6);
            cAnyFlag = cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
            if (cAnyFlag.equalsIgnoreCase("true")) {
                schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
            } else if (cAnyFlag.equalsIgnoreCase("action")) {
                setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
            }
            cursor.moveToFirst();
            cursor.move(8);

            cSpecificFlag = cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
            if (cSpecificFlag.equalsIgnoreCase("true") &&
                    id.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))) {
                schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
            } else if (cSpecificFlag.equalsIgnoreCase("action") &&
                    id.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))) {

                setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
            }


        } else {
            cursor.moveToFirst();
            cursor.move(7);
            dAnyFlag = cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
            if (dAnyFlag.equalsIgnoreCase("true")&&flag) {
                flag=false;
                schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
            } else if (cAnyFlag.equalsIgnoreCase("action")&&flag) {
                flag=false;
                setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
            }
            cursor.moveToFirst();
            cursor.move(9);
            dSpecificFlag = cursor.getString(cursor.getColumnIndex(Tasks.state)).trim();
            if (dSpecificFlag.equalsIgnoreCase("true") &&
                    id.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))) {
                id="";
                schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
            } else if (dSpecificFlag.equalsIgnoreCase("action") &&
                    id.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))) {
                id="";
                setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                        cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
            }
        }
    }

    void schedule(String className, String extras, Context context) {
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
