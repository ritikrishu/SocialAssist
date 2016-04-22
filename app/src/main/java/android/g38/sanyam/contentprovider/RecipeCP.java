package android.g38.sanyam.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SANYAM TYAGI on 3/29/2016.
 */
public class RecipeCP {
    static SharedPreferences forCp ;
    static public void  setIf(Context context,String image,String name,String data){
        forCp= context.getSharedPreferences("recipe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=forCp.edit();
        editor.putString("if",image);
        editor.putString("recipeName",name);
        editor.putString("data",data);
        editor.commit();
    }

    static public void  setThen(Context context,String image,String name){
        forCp= context.getSharedPreferences("recipe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putString("then",image);
        editor.putString("recipeName",name+" When "+forCp.getString("recipeName",""));
        editor.commit();
    }
    static public void  setExtra(Context context,String data){
        forCp= context.getSharedPreferences("recipe", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putString("data", forCp.getString("data","")+data);
        editor.commit();
    }

    static public Cursor getData(Context context){
        return  context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, null, null, null, null);
    }

    static public void saveToRecipeCp(Context context,String base){
        forCp = context.getSharedPreferences("recipe", Context.MODE_PRIVATE);
        ContentValues values = new ContentValues();
        values.put(Tasks.IF, forCp.getString("if",""));
        values.put(Tasks.THEN, forCp.getString("then",""));
        values.put(Tasks.RECIPE_NAME, forCp.getString("recipeName",""));
        values.put(Tasks.DATA, forCp.getString("data", ""));
        values.put(Tasks.BASE, base);
        Uri uri = context.getContentResolver().insert(Tasks.CONTENT_URI_FOR_RECIPE, values);

    }

}
