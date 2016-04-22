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
    static String id = "";
    Boolean flag=true;
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        this.context = context;
        CursorFunctions cursorFunctions = new CursorFunctions(context);
        //WifiManager.NETWORK_STATE_CHANGED_ACTION

        if (info != null && info.isConnected()) {

            WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            id = wifiInfo.getSSID();
            flag = true;

            cursorFunctions.loadCursorForSpecific("cSpecific", id);
            cursorFunctions.loadCursor("cAny");


        } else {

            if (!(id.equals(""))) {
                Log.i("sanyam", "then----" + id);
                cursorFunctions.loadCursorForSpecific("dSpecific", id);
                cursorFunctions.loadCursor("dAny");
                id = "";
                flag = false;
            }


        }


    }
}
