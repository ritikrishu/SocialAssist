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

    final SmsManager sms = SmsManager.getDefault();
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        final Bundle bundle = intent.getExtras();
        this.context=context;
        CursorFunctions cursorFunctions=new CursorFunctions(context);
        try {
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    cursorFunctions.loadCursor("newSms");
                    cursorFunctions.loadCursor("newSmsString");
                    cursorFunctions.loadCursor("newSmsNumber");


                }
            }

        } catch (Exception e) {
            Log.e("SmsReceiver", "Exception smsReceiver" +e);

        }
    }

}