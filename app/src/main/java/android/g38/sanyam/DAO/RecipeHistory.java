package android.g38.sanyam.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Html;

import java.util.ArrayList;

/**
 * Created by SANYAM TYAGI on 4/21/2016.
 */
public class RecipeHistory extends SQLiteOpenHelper {

    public static String DATABASENAME="database.db";
    public static String TABLEDISPLAY="Recipe_Data";
    public static String ID="ID";
    static final public String IF = "if";
    static final public String THEN = "then_col";
    static final public String RECIPE_NAME = "recipe_name";
    static final public String DATA = "data";
    static final public String TIME = "time";
    static final public String STATUS = "status";
    static final public String BASE = "base";


    //call the constructor to create the db.
    public RecipeHistory(Context context) {
        super(context, DATABASENAME, null, 1);
        //create the db
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery="create table "+TABLEDISPLAY+" ("+ID+" TEXT, if TEXT,  then_col TEXT,  recipe_name TEXT," +
                "data TEXT, time TEXT, base TEXT, status TEXT)";
        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertData(BeanRecipe obj){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
                cv.put(ID,obj.getId());
                cv.put(IF,obj.getIf());
                cv.put(THEN,obj.getThen());
                cv.put(DATA,obj.getData());
                cv.put(TIME,obj.getTime());
                cv.put(BASE,obj.getBase());
                cv.put(RECIPE_NAME,obj.getName());
                cv.put(STATUS,obj.getStatus());
                db.insert(TABLEDISPLAY, null, cv);
    }


    public Cursor getData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT * FROM "+ TABLEDISPLAY+" ORDER BY ID,time DESC",null);
        return result;
    }

    public void delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLEDISPLAY,null,null);

    }

}



