package android.g38.socialassist;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.sanyam.contentprovider.Tasks;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Long2;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class CreateRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("forCp",MODE_PRIVATE);
        Toast.makeText(getApplicationContext(),""+sharedPreferences.getString("base","blank"), Toast.LENGTH_SHORT).show();


//        Cursor c = managedQuery(Tasks.CONTENT_URI, null, null, null, null);
//
//        if (c.moveToFirst()) {
//            do{
//                Toast.makeText(this,
//                        c.getString(c.getColumnIndex(Tasks._ID)) +
//                                ", " + c.getString(c.getColumnIndex(Tasks.base)) +
//                                ", " + c.getString(c.getColumnIndex(Tasks.state))+
//                                ", " + c.getString(c.getColumnIndex(Tasks.actions))+
//                                ", " + c.getString(c.getColumnIndex(Tasks.extras)),
//                        Toast.LENGTH_SHORT).show();
//            } while (c.moveToNext());
//        }




    }

    public void onIFClick(View v){
        Intent intent = new Intent(CreateRecipeActivity.this, SelectTriggerActivity.class);
        intent.putExtra("IF",true);
        startActivity(intent);
        finish();
        Log.e("finish", "Create");
    }
    public void onThenClick(View v){
        Intent intent = new Intent(CreateRecipeActivity.this, SelectTriggerActivity.class);
        intent.putExtra("IF",false);
        startActivity(intent);
        finish();
        Log.e("finish", "Create then");
    }


}
