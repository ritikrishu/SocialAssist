package android.g38.sanyam.contentprovider;

/**
 * Created by SANYAM TYAGI on 3/21/2016.
 */
import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;


public class Tasks extends ContentProvider {



    static final public String PROVIDER_NAME = "android.g38.sanyam.contentprovider";
    static final public String URL = "content://" + PROVIDER_NAME + "/tasks";
    static final public String URL_RECIPE = "content://" + PROVIDER_NAME + "/recipe";
    static final public Uri CONTENT_URI = Uri.parse(URL);
    static final public Uri CONTENT_URI_FOR_RECIPE = Uri.parse(URL_RECIPE);

    //FOR TABLE ONE
    static final public String _ID = "_id";
    static final public String extras = "extras";
    static final public String intent = "intent";
    static final public String base = "base";
    static final public String state = "state";
    static final public String others = "others";
    static final public String actions = "actions";

    private static HashMap<String, String> PROJECTION_MAP;

    static final int TASKS = 1;
    static final int TASKS_ID = 2;

    //FOR TABLE RECIPE

    static final public String IF = "if";
    static final public String THEN = "then_col";
    static final public String RECIPE_NAME = "recipe_name";
    static final public String DATA = "data";
    static final public String BASE = "base";

    static final int RECIPE = 3;
    static final int RECIPE_ID = 4;

    static final UriMatcher uriMatcher;
    static{
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "tasks", TASKS);
        uriMatcher.addURI(PROVIDER_NAME, "tasks/#", TASKS_ID);
        uriMatcher.addURI(PROVIDER_NAME, "recipe", RECIPE);
        uriMatcher.addURI(PROVIDER_NAME, "recipe/#", RECIPE_ID);
    }

    /**
     * Database specific constant declarations
     */
    private SQLiteDatabase db;
    static final String DATABASE_NAME = "SA";
    static final String TABLE_NAME = "tasks";
    static final String TABLE_NAME_RECIPE = "recipe";
    static final int DATABASE_VERSION = 1;
    static final String CREATE_DB_TABLE =
            " CREATE TABLE " + TABLE_NAME +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " extras TEXT, " +" base TEXT, "+" state TEXT, "+" others TEXT, "+" actions TEXT, "+
                    " intent TEXT);";

    static final String CREATE_DB_TABLE_RECIPE =
            " CREATE TABLE " + TABLE_NAME_RECIPE +
                    " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    " if TEXT, " +" then_col TEXT, "+" recipe_name TEXT, "+" data TEXT, "+" base TEXT );";

    /**
     * Helper class that actually creates and manages
     * the provider's underlying data repository.
     */
    private static class DatabaseHelper extends SQLiteOpenHelper {
        DatabaseHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            db.execSQL(CREATE_DB_TABLE);
            db.execSQL(CREATE_DB_TABLE_RECIPE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
            onCreate(db);
            db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME_RECIPE);
            onCreate(db);
        }
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        DatabaseHelper dbHelper = new DatabaseHelper(context);

        /**
         * Create a write able database which will trigger its
         * creation if it doesn't already exist.
         */
        db = dbHelper.getWritableDatabase();
        return (db == null)? false:true;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Uri _uri = null;
        switch (uriMatcher.match(uri)){
            case TASKS:
                long _ID1 = db.insert(TABLE_NAME, "", values);
                //---if added successfully---
                if (_ID1 > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI, _ID1);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            case RECIPE:
                long _ID2 = db.insert(TABLE_NAME_RECIPE, "", values);
                //---if added successfully---
                if (_ID2 > 0) {
                    _uri = ContentUris.withAppendedId(CONTENT_URI_FOR_RECIPE, _ID2);
                    getContext().getContentResolver().notifyChange(_uri, null);
                }
                break;
            default: throw new SQLException("Failed to insert row into " + uri);
        }
        return _uri;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(TABLE_NAME);

        switch (uriMatcher.match(uri)) {
            case TASKS:
                qb.setTables(TABLE_NAME);
                qb.setProjectionMap(PROJECTION_MAP);
                break;

            case TASKS_ID:
                qb.setTables(TABLE_NAME);
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;

            case RECIPE:
                qb.setTables(TABLE_NAME_RECIPE);
                qb.setProjectionMap(PROJECTION_MAP);
                break;

            case RECIPE_ID:
                qb.setTables(TABLE_NAME_RECIPE);
                qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
                break;


            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }


        Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, null);

        /**
         * register to watch a content URI for changes
         */
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case TASKS:
                count = db.delete(TABLE_NAME, selection, selectionArgs);
                break;

            case TASKS_ID:
                String id = uri.getPathSegments().get(1);
                count = db.delete( TABLE_NAME, _ID +  " = " + id +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            case RECIPE:
                count = db.delete(TABLE_NAME_RECIPE, selection, selectionArgs);
                break;

            case RECIPE_ID:
                String idR = uri.getPathSegments().get(1);
                count = db.delete( TABLE_NAME_RECIPE, _ID +  " = " + idR +
                        (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int count = 0;

        switch (uriMatcher.match(uri)){
            case TASKS:
                count = db.update(TABLE_NAME, values, selection, selectionArgs);
                break;

            case TASKS_ID:
                count = db.update(TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            case RECIPE:
                count = db.update(TABLE_NAME_RECIPE, values, selection, selectionArgs);
                break;

            case RECIPE_ID:
                count = db.update(TABLE_NAME_RECIPE, values, _ID + " = " + uri.getPathSegments().get(1) +
                        (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri );
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){

            case TASKS:
                return "vnd.android.cursor.dir/vnd.example.tasks";

            case TASKS_ID:
                return "vnd.android.cursor.item/vnd.example.tasks";

            case RECIPE:
                return "vnd.android.cursor.dir/vnd.example.recipe";

            case RECIPE_ID:
                return "vnd.android.cursor.item/vnd.example.recipe";

            default:
                throw new IllegalArgumentException("Unsupported URI: " + uri);
        }
    }
}