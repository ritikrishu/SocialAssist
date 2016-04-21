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
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {

        final String action = intent.getAction();
        this.context=context;
        CursorFunctions cursorFunctions=new CursorFunctions(context);
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

            cursorFunctions.loadCursor("cBlue");

        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)){
            cursorFunctions.loadCursor("dBlue");

        }
    }
}
