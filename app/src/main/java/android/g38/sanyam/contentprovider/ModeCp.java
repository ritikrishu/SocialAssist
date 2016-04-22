package android.g38.sanyam.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

/**
 * Created by SANYAM TYAGI on 4/23/2016.
 */
public class ModeCp {

    static public void insert(Context context, String mode){
        ContentValues values = new ContentValues();
        values.put(Tasks.MODE, mode);
        Uri uri = context.getContentResolver().insert(Tasks.CONTENT_URI_FOR_MODE, values);

    }

    static public Cursor getData(Context context){
        return  context.getContentResolver().query(Tasks.CONTENT_URI_FOR_MODE, null, null, null, null);
    }

    static public void changeMode(String id,String mode,Context context){
        ContentValues values = new ContentValues();
        values.put(Tasks.MODE, mode);
        String mSelectionClause = Tasks._ID +  " LIKE ?";
        String[] mSelectionArgs = {id};
        context.getContentResolver().update(Tasks.CONTENT_URI_FOR_MODE, values, mSelectionClause, mSelectionArgs);
    }

}
