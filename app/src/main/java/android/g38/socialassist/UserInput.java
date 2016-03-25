package android.g38.socialassist;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.ForCp;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

public class UserInput extends AppCompatActivity {

    LinearLayout layout;
    static final int PICK_CONTACT = 1;
    String pickedNumber = "";
    TextView tv;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input);

        extras = getIntent().getExtras();
        String extmsg = extras.getString("msg");
        if (extmsg.equals("image")) {
            layout = (LinearLayout) findViewById(R.id.layout_image_picker);
            layout.setVisibility(View.VISIBLE);
            Button pick = (Button) findViewById(R.id.bgallery);
            pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult((new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)), 10);

                }
            });

        } else {
            layout = (LinearLayout) findViewById(R.id.layout_general);
            layout.setVisibility(View.VISIBLE);
            Button save = (Button) findViewById(R.id.bsave);
            final EditText input = (EditText) findViewById(R.id.etinput);
            TextView msg = (TextView) findViewById(R.id.tvmsg);
            msg.setText(extras.getString("msg"));

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForCp.setOthers(getApplicationContext(), input.getText().toString());
                    layout.setVisibility(View.GONE);
                    startActivity(new Intent(UserInput.this, CreateRecipeActivity.class));
                }
            });
        }


    }



    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == RESULT_OK && reqCode == 10) {
            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(projection[0]);
            String imagePath = cursor.getString(index);
            ForCp.setOthers(getApplicationContext(), imagePath);
            layout.setVisibility(View.GONE);
            startActivity(new Intent(UserInput.this, CreateRecipeActivity.class));
            cursor.close();

        }
    }
}
