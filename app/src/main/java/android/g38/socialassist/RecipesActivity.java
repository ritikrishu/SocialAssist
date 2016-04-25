package android.g38.socialassist;

import android.g38.ritik.AdaptersAndAnimators.RecipesAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.SearchView;

public class RecipesActivity extends AppCompatActivity {
    SearchView searchView;
    RecyclerView recyclerView;
    RecipesAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        searchView = (SearchView)findViewById(R.id.searchView);
        recyclerView = (RecyclerView)findViewById(R.id.rvMyRecipes);
        recyclerView.setLayoutManager(new LinearLayoutManager(RecipesActivity.this));

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new RecipesAdapter(RecipesActivity.this,getLayoutInflater()));
    }
}
