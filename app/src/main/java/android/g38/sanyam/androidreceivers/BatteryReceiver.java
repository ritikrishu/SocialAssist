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
import android.content.SharedPreferences;
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

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        this.context=context;
        SharedPreferences sp=context.getSharedPreferences("battery",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();
        editor.putBoolean("flag",true);
        editor.putString("level","Battery level is "+level+" %.");
        editor.apply();
       CursorFunctions cursorFunctions=new CursorFunctions(context);

        if (isCharging) {
            cursorFunctions.loadCursor("pluggedIn");

        } else {
            cursorFunctions.loadCursor("pluggedOut");

        }

        if (level < 15) {
            cursorFunctions.loadCursor("below15");
        }


    }

//
}
