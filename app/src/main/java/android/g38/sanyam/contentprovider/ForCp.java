package android.g38.sanyam.contentprovider;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.g38.sanyam.androidreceivers.ActionsReceiver;
import android.g38.sanyam.androidreceivers.TimeReceiver;
import android.g38.sanyam.facebook.Post;
import android.g38.socialassist.HomeActivity;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.GregorianCalendar;

/**
 * Created by SANYAM TYAGI on 3/21/2016.
 */
public class ForCp {
    static SharedPreferences forCp ;
    static public void  setBase(Context context,String base){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        if(!(forCp.getBoolean("addDone",false))){
            SharedPreferences.Editor editor=forCp.edit();
            editor.clear();
            editor.putBoolean("add", true);
            editor.putString("base",base);
            editor.commit();

        }
    }

    static public void  setDone(Context context){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putBoolean("adddone", true);
        editor.putString("base","");
        editor.commit();
    }

    static public void  setOthers(Context context,String others){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putBoolean("others",true);
        editor.putString("data", others);
        editor.commit();
    }

    static public void  setActions(Context context,String actions){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putString("actions", actions);
        editor.commit();
    }

    static public void  setContent(Context context,String className,String extras){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=forCp.edit();
        editor.putString("className", className);
        editor.putString("extras",extras);
        editor.putBoolean("launch", true);
        editor.commit();
    }

    static public Boolean  checkLaunchFlag(Context context){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        return forCp.getBoolean("launch",false);
    }

    static public String  getClassName(Context context){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        return forCp.getString("className", "");
    }
    static public String  getextra(Context context){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        return forCp.getString("extras", "");
    }

    static public void   checkLaunch(Context context){
        if(ForCp.checkLaunchFlag(context)){
            Post.schedule(ForCp.getClassName(context),
                    ForCp.getextra(context), context,
            context.getContentResolver().query(Tasks.CONTENT_URI,null,null,null,null).getCount());

        }
    }

    static public void saveToCp(Context context,String extras,String intent){
        SharedPreferences forCp = context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        if(forCp.getBoolean("add",false)){
            ContentValues values = new ContentValues();
            values.put(Tasks.extras, extras);
            values.put(Tasks.intent, intent);
            values.put(Tasks.base, forCp.getString("base",""));
            values.put(Tasks.state, "true");
            values.put(Tasks.others, forCp.getString("data",""));
            values.put(Tasks.actions,forCp.getString("actions",""));
//            String mSelectionClause = Tasks.base +  " LIKE ?";
//            String[] mSelectionArgs = {forCp.getString("base","")};
//
//            int c = context.getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
            context.getContentResolver().insert(Tasks.CONTENT_URI,values);
            RecipeCP.saveToRecipeCp(context,forCp.getString("base",""));

            checkForTime(forCp.getString("base",""),context,RecipeCP.getCount(context)-1);
            setDone(context);

            //uncomment <code>
            //  SharedPreferences.Editor editor = forCp.edit();
            //editor.putBoolean("adddone",true);
            //editor.commit();
            //
            // </code>
        }
    }

    static public void saveToCpForAction(Context context,String extras){
        SharedPreferences forCp = context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        if(forCp.getBoolean("add",false)){
            ContentValues values = new ContentValues();
            values.put(Tasks.extras, extras);
            values.put(Tasks.base, forCp.getString("base",""));
            values.put(Tasks.state, "action");
            values.put(Tasks.others, forCp.getString("data",""));
            values.put(Tasks.actions, forCp.getString("actions", ""));

            context.getContentResolver().insert(Tasks.CONTENT_URI,values);

            RecipeCP.saveToRecipeCp(context,forCp.getString("base",""));
            checkForTime(forCp.getString("base",""),context,RecipeCP.getCount(context)-1);
            setDone(context);

        }
    }

    public static void setStateToTrue(Context context,String base){
        ContentValues values = new ContentValues();
        values.put(Tasks.base, base);
        values.put(Tasks.state, "true");
        String mSelectionClause = Tasks.base +  " LIKE ?";
        String[] mSelectionArgs = {base};
        int c = context.getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
    }

   static void checkForTime(String base,Context context,int id){
       if(base.equalsIgnoreCase("time")){
           SharedPreferences sharedPreferences=context.getSharedPreferences("time",Context.MODE_PRIVATE);
           Intent intentPost = new Intent(context, TimeReceiver.class);

           intentPost.putExtra("id",""+id);
           AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
           Long time =new GregorianCalendar().getTimeInMillis() +Long.parseLong(sharedPreferences.getString("alarm",""));

           intentPost.setData(Uri.parse("myalarms://"+id));
           alarmManager.set(AlarmManager.RTC_WAKEUP, time, PendingIntent.getBroadcast(context, 1,
                   intentPost, PendingIntent.FLAG_UPDATE_CURRENT));
       }

    }
}
