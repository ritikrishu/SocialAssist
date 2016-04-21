package android.g38.sanyam.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.g38.sanyam.facebook.Post;
import android.g38.socialassist.HomeActivity;
import android.net.Uri;
import android.widget.Toast;

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

            String mSelectionClause = Tasks.base +  " LIKE ?";
            String[] mSelectionArgs = {forCp.getString("base","")};
            //int c = context.getContentResolver().update(Tasks.CONTENT_URI, values, mSelectionClause, mSelectionArgs);
            context.getContentResolver().insert(Tasks.CONTENT_URI,values);

            RecipeCP.saveToRecipeCp(context,forCp.getString("base",""));
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

//    static public void insert(Context context){
//        ContentValues values = new ContentValues();
////        values.put(Tasks.base, "pluggedIn");
////        values.put(Tasks.state, "false");
////        Uri uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "pluggedOut");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "below15");
////        values.put(Tasks.state, "false");
////        Uri uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "newSms");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "newSmsString");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "newSmsNumber");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "cAny");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "dAny");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "cSpecific");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "dSpecific");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "cBlue");
////        values.put(Tasks.state, "false");
////        uri =context. getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "dBlue");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "link");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "image");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
////        values = new ContentValues();
////        values.put(Tasks.base, "tweet");
////        values.put(Tasks.state, "false");
////        uri = context.getContentResolver().insert(Tasks.CONTENT_URI, values);
//    }

}
