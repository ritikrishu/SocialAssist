package android.g38.socialassist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.g38.sanyam.contentprovider.Tasks;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CreateRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //code for creating the state checker CP.
        SharedPreferences sc = getSharedPreferences("sc", Context.MODE_PRIVATE);
        if(!(sc.getBoolean("created",false))){

            ContentValues values = new ContentValues();
            values.put(Tasks.base, "pluggedIn");
            values.put(Tasks.state, "false");
            Uri uri = getContentResolver().insert(Tasks.CONTENT_URI, values);
            values = new ContentValues();
            values.put(Tasks.base, "pluggedOut");
            values.put(Tasks.state, "false");
            uri = getContentResolver().insert(Tasks.CONTENT_URI, values);
            values = new ContentValues();
            values.put(Tasks.base, "below15");
            values.put(Tasks.state, "false");
            uri = getContentResolver().insert(Tasks.CONTENT_URI, values);
            SharedPreferences.Editor editor=sc.edit();
            editor.putBoolean("created", true);
            editor.commit();

        }
//        Cursor c = managedQuery(Tasks.CONTENT_URI, null, null, null, null);
//
//        if (c.moveToFirst()) {
//            do{
//                Toast.makeText(this,
//                        c.getString(c.getColumnIndex(Flags._ID)) +
//                                ", " + c.getString(c.getColumnIndex(Tasks.base)) +
//                                ", " + c.getString(c.getColumnIndex(Tasks.state)),
//                        Toast.LENGTH_SHORT).show();
//            } while (c.moveToNext());
//        }


    }

    public void onIFClick(View v){
        startActivity(new Intent(CreateRecipeActivity.this, SelectTriggerActivity.class));
    }
    public void onThenClick(View v){

    }
}
