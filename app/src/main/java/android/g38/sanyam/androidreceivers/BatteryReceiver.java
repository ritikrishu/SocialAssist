package android.g38.sanyam.androidreceivers;

/**
 * Created by SANYAM TYAGI on 3/21/2016.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 3/20/2016.
 */
public class BatteryReceiver extends BroadcastReceiver {
    static Cursor cursor;
    String flag = "";
    Intent intentPost;

    @Override
    public void onReceive(Context context, Intent intent) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, null, null, null);

        if (isCharging) {
            cursor.moveToFirst();
            check(cursor,context);
        } else {
            cursor.moveToFirst();
            cursor.move(1);
            check(cursor,context);
        }
        if (level < 15) {
            cursor.moveToFirst();
            cursor.move(2);
            check(cursor,context);
        }

    }

    void check(Cursor cursor,Context context){
        flag = cursor.getString(cursor.getColumnIndex("state")).trim();
        RecipeCP.setStatusDone(context,cursor.getString(cursor.getColumnIndex(Tasks.base)));
        if (flag.equalsIgnoreCase("true")) {
            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(), context);
        } else if (flag.equalsIgnoreCase("action")) {
            setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim(), context);
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
