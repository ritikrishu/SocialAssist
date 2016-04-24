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
import android.widget.ImageView;
import android.widget.Toast;

public class CreateRecipeActivity extends AppCompatActivity {

    String base="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SharedPreferences sharedPreferences = getSharedPreferences("forCp",MODE_PRIVATE);
        Toast.makeText(getApplicationContext(),""+sharedPreferences.getString("base","blank"), Toast.LENGTH_SHORT).show();

        ImageView ivIf =(ImageView)findViewById(R.id.ivIf);
        ImageView ivThen =(ImageView)findViewById(R.id.ivThen);
        base=sharedPreferences.getString("base","blank");
        if(!(base.equals(""))) {

            if(base.equals("pluggedIn")||base.equals("pluggedOut")||base.equals("below15"))
                ivIf.setImageResource(R.drawable.ic_battery_channel);
            else if(base.equals("newSms")||base.equals("newSmsString")||base.equals("newSmsNumber"))
                ivIf.setImageResource(R.drawable.ic_sms_channel);
            else if(base.equals("cAny")||base.equals("dAny")||base.equals("cSpecific")||
                    base.equals("dSpecific")||base.equals("cBlue")||base.equals("dBlue"))
                ivIf.setImageResource(R.drawable.ic_device_channel);
            else if(base.equals("link")||base.equals("image"))
                ivIf.setImageResource(R.drawable.ic_facebook_channel);
            else if(base.equals("tweet"))
                ivIf.setImageResource(R.drawable.ic_twitter_channel);
            else if(base.equals("time"))
                ivIf.setImageResource(R.drawable.ic_dateandtime_channel);

            ivThen.setImageResource(R.drawable.create_then);

        }



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
        if(!(base.equals(""))) {
            Intent intent = new Intent(CreateRecipeActivity.this, SelectTriggerActivity.class);
            intent.putExtra("IF", false);
            startActivity(intent);
            finish();
            Log.e("finish", "Create then");
        }
    }


}
