package android.g38.socialassist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.g38.ritik.AdaptersAndAnimators.HomeListAdapter;
import android.g38.ritik.Database.SocialAssistDBHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ProgressDialog dialog;
    RecyclerView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        dialog = new ProgressDialog(this);
        listView = (RecyclerView) findViewById(R.id.lvMain);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this, CreateRecipeActivity.class));
            }
        });

        fab.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                SocialAssistDBHelper dbHelper = new SocialAssistDBHelper(HomeActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                SocialAssistDBHelper.fakeInsert(db);
                db.close();
                dbHelper.close();
                return true;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.inflateHeaderView(R.layout.nav_header_home);

        listView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));

        SharedPreferences sharedPreferences = getSharedPreferences("forCp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("base","");
        editor.apply();

    }
	
//	private void getLayoutDetails(){
//        String str = "";
//		RecipeHistory recipeHistory = new RecipeHistory(getApplicationContext());
//        Cursor c=recipeHistory.getData();
//        if (c.moveToFirst()) {
//            do{
//                str+=c.getString(c.getColumnIndex(RecipeHistory.ID)) +
//                        ", " +c.getString(c.getColumnIndex(RecipeHistory.IF)) +
//                        ", " +c.getString(c.getColumnIndex(RecipeHistory.THEN)) +
//                        ", " +c.getString(c.getColumnIndex(RecipeHistory.RECIPE_NAME)) +
//                        ", " + c.getString(c.getColumnIndex(RecipeHistory.DATA)) +
//                        ", " + c.getString(c.getColumnIndex(RecipeHistory.STATUS))+
//                        ", " + c.getString(c.getColumnIndex(RecipeHistory.TIME)) +"\n";
//
//            } while (c.moveToNext());
//        }
//	}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            startActivity(new Intent(HomeActivity.this,SettingsActivity.class));
//        }
//        else if(id == R.id.action_recipe)
//            startActivity(new Intent(HomeActivity.this,RecipesActivity.class));

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(new HomeListAdapter(HomeActivity.this, getLayoutInflater()));
        SharedPreferences sharedPreferences = getSharedPreferences("forCp",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("base","");
        editor.apply();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.home) {
        } else if (id == R.id.my_recipe) {
            Intent intent = new Intent(getApplicationContext(), RecipesActivity.class);
            startActivity(intent);
        } else if (id == R.id.create_recipe) {
            Intent intent = new Intent(getApplicationContext(), CreateRecipeActivity.class);
            startActivity(intent);
        } else if (id == R.id.add_channels) {
            Intent intent = new Intent(getApplicationContext(), ChannelsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
