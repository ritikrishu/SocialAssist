package android.g38.sanyam.androidreceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.g38.sanyam.contentprovider.ModeCp;
import android.util.Log;

/**
 * Created by SANYAM TYAGI on 4/24/2016.
 */
public class TimeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        CursorFunctions cursorFunctions=new CursorFunctions(context);
        cursorFunctions.loadCursorForSpecific("time",intent.getExtras().getString("id",""));
       // ModeCp.changeMode(intent.getExtras().getString("id",""),"off",context);
    }
}
