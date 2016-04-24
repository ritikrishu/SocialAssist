package android.g38.sanyam.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

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
        String data=forCp.getString("data","");
        if(data.contains("time")) {
            data.replace("time", "");
        }
        values.put(Tasks.DATA, data);
        values.put(Tasks.RECIPE_NAME, forCp.getString("recipeName",""));
        values.put(Tasks.DATA, forCp.getString("data", ""));
        values.put(Tasks.BASE, base);
        Uri uri = context.getContentResolver().insert(Tasks.CONTENT_URI_FOR_RECIPE, values);
        ModeCp.insert(context,"on");

    }

    static public int getCount(Context context){
        return getData(context).getCount();
    }

    public static HashMap<String,ArrayList<String>> getDataForAdapter(final Context context){

        HashMap<String,ArrayList<String>> listHashMap = new HashMap<>();
        final ArrayList<String> valuesIf = new ArrayList<>();
        final ArrayList<String> valuesThen = new ArrayList<>();
        final ArrayList<String> valuesShrtDes = new ArrayList<>();
        final ArrayList<String> valuesToogle = new ArrayList<>();

        Thread tIf = new Thread(){
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, new String[]{Tasks.IF}, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext())
                        valuesIf.add(cursor.getString(0));
                    cursor.close();
                }
            }
        };

        Thread tThen = new Thread(){
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, new String[]{Tasks.THEN}, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext())
                        valuesThen.add(cursor.getString(0));
                    cursor.close();
                }
            }
        };

        Thread tShrtDes = new Thread(){
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, new String[]{Tasks.RECIPE_NAME}, null, null, null);
                if(cursor != null) {
                    while (cursor.moveToNext())
                        valuesShrtDes.add(cursor.getString(0));
                    cursor.close();
                }
            }
        };

        Thread tToggleButton = new Thread(){
            @Override
            public void run() {
                Cursor cursor= context.getContentResolver().query(Tasks.CONTENT_URI_FOR_MODE, new String[]{"MODE"}, null, null, null);;
                if(cursor != null) {
                    while (cursor.moveToNext())
                        valuesToogle.add(cursor.getString(0));
                    cursor.close();
                }
            }
        };

        tIf.start();
        tThen.start();
        tShrtDes.start();
        tToggleButton.start();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while (tIf.isAlive() || tThen.isAlive() || tShrtDes.isAlive() || tToggleButton.isAlive()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        listHashMap.put(Tasks.IF, valuesIf);
        listHashMap.put(Tasks.THEN, valuesThen);
        listHashMap.put(Tasks.RECIPE_NAME,valuesShrtDes);
        listHashMap.put(Tasks.MODE, valuesToogle);

        return  listHashMap;
        //MODE
    }


    public static HashMap<String,ArrayList<String>> getDataForSearch(final Context context, final String query) {
        HashMap<String, ArrayList<String>> listHashMap = new HashMap<>();
        final ArrayList<String> valuesIf = new ArrayList<>();
        final ArrayList<String> valuesThen = new ArrayList<>();
        final ArrayList<String> valuesShrtDes = new ArrayList<>();
        final ArrayList<String> valuesToogle = new ArrayList<>();


        Thread tIf = new Thread() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, new String[]{Tasks.IF,Tasks.RECIPE_NAME}, Tasks.RECIPE_NAME, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext())
                        if(cursor.getString(1).contains(query))
                            valuesIf.add(cursor.getString(0));
                    cursor.close();
                }
            }
        };

        Thread tThen = new Thread() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, new String[]{Tasks.THEN, Tasks.RECIPE_NAME}, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext())
                        if(cursor.getString(1).contains(query))
                            valuesThen.add(cursor.getString(0));
                    cursor.close();
                }
            }
        };

        Thread tShrtDes = new Thread() {
            @Override
            public void run() {
                Cursor cursor = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, new String[]{Tasks.RECIPE_NAME}, null, null, null);
                Cursor cursorMod = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_MODE, new String[]{"MODE"}, null, null, null);
                ;
                if (cursor != null && cursorMod != null) {
                    while (cursor.moveToNext() && cursorMod.moveToNext()) {
                        if (cursor.getString(0).contains(query)) {
                            valuesShrtDes.add(cursor.getString(0));
                            valuesToogle.add(cursorMod.getString(0));
                        }
                        cursor.close();
                    }
                }
            }
        };

        tIf.start();
        tThen.start();
        tShrtDes.start();
        try

        {
            Thread.sleep(20);
        }

        catch(
                InterruptedException e
                )

        {
            e.printStackTrace();
        }

        while(tIf.isAlive()||tThen.isAlive()||tShrtDes.isAlive())

        {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        listHashMap.put(Tasks.IF,valuesIf);
        listHashMap.put(Tasks.THEN,valuesThen);
        listHashMap.put(Tasks.RECIPE_NAME,valuesShrtDes);
        listHashMap.put(Tasks.MODE,valuesToogle);


        return listHashMap;
    }
}
