package android.g38.sanyam.androidreceivers;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.sanyam.contentprovider.Tasks;
import android.net.Uri;
import android.util.Log;

import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 4/21/2016.
 */
public class CursorFunctions {
    static Cursor cursor;
    String flag = "";
    Intent intentPost;
    String mSelectionClause = Tasks.base +  " LIKE ?";
    String[] mSelectionArgs = {""};
    Context context;
    String rId="";
    String data="";
    public CursorFunctions(Context context){
        this.context=context;
    }

    public void loadCursor(String base){
        mSelectionArgs[0] = base;
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
        if(!cursor.moveToFirst())
            return;

        do{
            if(!(checkMode(cursor.getString(cursor.getColumnIndex(Tasks._ID)))))
                continue;

            check(cursor);
        }while (cursor.moveToNext());
    }
    void loadCursorForSpecific(String base,String specific){
        mSelectionArgs[0] = base;
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
        if(!cursor.moveToFirst())
            return;

        do{
            if(!(checkMode(cursor.getString(cursor.getColumnIndex(Tasks._ID)))))
                continue;
            if(base.equals("time")){
                if(specific.equals(cursor.getString(cursor.getColumnIndex(Tasks._ID)))){

                    check(cursor);
                }
            }else if(specific.contains(cursor.getString(cursor.getColumnIndex(Tasks.others)))){

                check(cursor);
            }

        }while (cursor.moveToNext());
    }
    public  void loadCursorForFb(String base){
        mSelectionArgs[0] = base;
        cursor = context.getContentResolver().query(Tasks.CONTENT_URI, null, mSelectionClause, mSelectionArgs, null);
        if(!cursor.moveToFirst())
            return;
        do{


            if(!(checkMode(cursor.getString(cursor.getColumnIndex(Tasks._ID)))))
                continue;

            checkForFb(cursor);
        }while (cursor.moveToNext());
    }


    void check(Cursor cursor){
        flag = cursor.getString(cursor.getColumnIndex("state")).trim();
        //RecipeCP.setStatusDone(context,cursor.getString(cursor.getColumnIndex(Tasks.base)));
        rId=cursor.getString(cursor.getColumnIndex(Tasks._ID));
        data=cursor.getString(cursor.getColumnIndex(Tasks.extras));
        if (flag.equalsIgnoreCase("true")) {
            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim());
        } else if (flag.equalsIgnoreCase("action")) {
            setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim());
        }
    }

    void checkForFb(Cursor cursor){
        flag = cursor.getString(cursor.getColumnIndex("state")).trim();
        rId=cursor.getString(cursor.getColumnIndex(Tasks._ID));
        data=cursor.getString(cursor.getColumnIndex(Tasks.extras));
        if (flag.equalsIgnoreCase("true")) {
            schedule(cursor.getString(cursor.getColumnIndex(Tasks.intent)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim());
            setStateToFalse(context,rId);
        } else if (flag.equalsIgnoreCase("action")) {
            setActions(cursor.getString(cursor.getColumnIndex(Tasks.extras)).trim(),
                    cursor.getString(cursor.getColumnIndex(Tasks.actions)).trim());
            setStateToFalse(context,rId);
        }
    }

    void schedule(String className, String extras) {
        try {
            intentPost = new Intent(context, Class.forName(className));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        intentPost.putExtra("extras", "" + extras);
        intentPost.putExtra("rId", rId);
        intentPost.putExtra("data", data);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis() + 200;
        intentPost.setData(Uri.parse("myalarms://" + time));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }


    void setActions(String extras, String actions) {
        intentPost = new Intent(context, ActionsReceiver.class);
        intentPost.putExtra("extras", "" + extras);
        intentPost.putExtra("actions", "" + actions);
        intentPost.putExtra("rId", rId);
        intentPost.putExtra("data", data);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Long time = new GregorianCalendar().getTimeInMillis() + 200;
        intentPost.setData(Uri.parse("myalarms://" + time));
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
    }

    public void setStateToFalse(Context context,String rId){
        ContentValues values = new ContentValues();
        values.put(Tasks.state, "false");
        String mSelectionClause = Tasks._ID +  " LIKE ?";
        String[] mSelectionArgs = {rId};
        int c = context.getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
    }

    private boolean checkMode(String id){

        Cursor c=context.getContentResolver().query(Tasks.CONTENT_URI_FOR_MODE, null, "_ID    LIKE ? "
                , new String[]{id}, null);
        c.moveToFirst();
        if((c.getString(c.getColumnIndex(Tasks.MODE)).trim().equals("off")))
            return false;
        return true;
    }
}
