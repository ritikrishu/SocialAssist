package android.g38.ritik.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.g38.ritik.Database.DataBaseContracter;

/**
 * Created by ritik on 3/17/2016.
 */
public class SocialAssistDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "socialAssist";
    public static final int DATABASE_VERSION = 1;
    public SocialAssistDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_ODDENTRY = "CREATE TABLE " + DataBaseContracter.OddEntry.TABLE_NAME + " ( " + DataBaseContracter.OddEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " +
                DataBaseContracter.OddEntry.COLUMN_DAY +" TEXT NOT NULL, " + DataBaseContracter.OddEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                DataBaseContracter.OddEntry.COLUMN_ACTION + " TEXT NOT NULL , " + DataBaseContracter.OddEntry.COLUMN_TRIGGER + " TEXT NOT NULL, " +
                DataBaseContracter.OddEntry.COLUMN_EVENT + " TEXT NOT NULL, " + DataBaseContracter.OddEntry.COLUMN_SHORTDES + " TEXT NOT NULL " +
                "FOREIGN KEY ( " + DataBaseContracter.OddEntry._ID + " ) REFERENCES " + DataBaseContracter.OddEntry.TABLE_NAME + " ( " + DataBaseContracter.OddEntry._ID + " ) ;";
        final String CREATE_EVENENTRY = "CREATE TABLE " + DataBaseContracter.EvenEntry.TABLE_NAME + " ( " + DataBaseContracter.EvenEntry._ID + "INTEGER PRIMARY KEY" +
                DataBaseContracter.EvenEntry.COLUMN_DETAIL +" TEXT NOT NULL ) ; ";
        db.execSQL(CREATE_EVENENTRY);
        db.execSQL(CREATE_ODDENTRY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContracter.OddEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DataBaseContracter.EvenEntry.TABLE_NAME);
        onCreate(db);
    }
}
