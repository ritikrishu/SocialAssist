package android.g38.sanyam.androidreceivers;

import android.app.WallpaperManager;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;

import java.io.IOException;

/**
 * Created by SANYAM TYAGI on 3/24/2016.
 */
public class ActionsReceiver extends BroadcastReceiver{

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();
        String actions=extras.getString("actions","");
        String ext = extras.getString("extras","");
        setActions(ext,actions,context);
    }

    void setActions(String extras,String actions,Context context){

        switch (extras){

            case "onw":WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        wifiManager.setWifiEnabled(true);
                        break;
            case "ofw":WifiManager wifiManagerOff = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                        wifiManagerOff.setWifiEnabled(false);
                        break;
            case "onb":BluetoothAdapter bluetooth = BluetoothAdapter.getDefaultAdapter();
                        bluetooth.enable();
                        break;
            case "mut":AudioManager myAudioManager = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                        myAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        break;
            case "vol":AudioManager myAudioManagerVol = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
                         myAudioManagerVol.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                        myAudioManagerVol.setStreamVolume(AudioManager.STREAM_RING, Integer.valueOf(actions), 0);
                        break;
            case "wal":Bitmap bitmap = BitmapFactory.decodeFile(actions);

                            WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
                            try {
                                wallpaperManager.setBitmap(bitmap);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        break;
            case "map": String uri = "http://maps.google.com/";
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        break;
            default:break;

        }
    }


}
