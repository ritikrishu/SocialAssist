package android.g38.socialassist;

import android.g38.ritik.AdaptersAndAnimators.PersonalRecipeAdapter;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class PersonalRecipeActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_recipe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (RecyclerView)findViewById(R.id.rvMyPersonalRecipes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(PersonalRecipeActivity.this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        recyclerView.setAdapter(new PersonalRecipeAdapter(getIntent(),getLayoutInflater(),PersonalRecipeActivity.this ));
    }
}
