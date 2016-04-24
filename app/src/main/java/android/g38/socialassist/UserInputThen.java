package android.g38.socialassist;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.contentprovider.ForCp;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class UserInputThen extends AppCompatActivity {

    LinearLayout layout;
    static final int PICK_CONTACT = 1;
    String pickedNumber = "";
    TextView tv;
    Bundle extras;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_input_then);

        extras = getIntent().getExtras();
        String extmsg = extras.getString("msg");
        if (extmsg.equals("sms")) {
            layout = (LinearLayout) findViewById(R.id.layout_sms);
            layout.setVisibility(View.VISIBLE);
            tv = (TextView) findViewById(R.id.tvname);
            Button pick = (Button) findViewById(R.id.bpick);
            pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    pickContact();
                }
            });
            Button save = (Button) findViewById(R.id.bdone);
            final EditText input = (EditText) findViewById(R.id.etmsg);
            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String extras = pickedNumber + "!@#" + input.getText();
                    String intent = "android.g38.sanyam.androidreceivers.ScheduledSMS";
                    ForCp.saveToCp(getApplicationContext(), extras, intent);
                    ForCp.checkLaunch(getApplicationContext());
                    layout.setVisibility(View.GONE);
                    launchHome();
                }
            });

        } else if (extmsg.equals("image")) {
            layout = (LinearLayout) findViewById(R.id.layout_image_pickerthen);
            layout.setVisibility(View.VISIBLE);
            Button pick = (Button) findViewById(R.id.bgallerythen);
            pick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivityForResult((new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)), 10);

                }
            });

        } else {
            layout = (LinearLayout) findViewById(R.id.layout_generalthen);
            layout.setVisibility(View.VISIBLE);
            Button save = (Button) findViewById(R.id.bsavethen);
            final EditText input = (EditText) findViewById(R.id.etinputthen);
            TextView msg = (TextView) findViewById(R.id.tvmsgthen);
            msg.setText(extras.getString("msg"));

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ForCp.setActions(getApplicationContext(), input.getText().toString());
                    ForCp.saveToCpForAction(getApplicationContext(), extras.getString("tag"));
                    ForCp.checkLaunch(getApplicationContext());
                    layout.setVisibility(View.GONE);
                    launchHome();

                }
            });
        }


    }

    private void pickContact() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, PICK_CONTACT);

    }

    @Override
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && reqCode == PICK_CONTACT) {

            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {


                String id = c.getString(c.getColumnIndexOrThrow(ContactsContract.Contacts._ID));

                String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                if (hasPhone.equalsIgnoreCase("1")) {
                    Cursor phones = getContentResolver().query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + id,
                            null, null);
                    phones.moveToFirst();
                    pickedNumber = phones.getString(phones.getColumnIndex("data1"));
                    phones.close();
                }
                String pickedName = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                tv.setText("" + pickedName + ":" + pickedNumber);

            }
        }

        if (resultCode == RESULT_OK && reqCode == 10) {
            Uri uri = data.getData();
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(projection[0]);
            String imagePath = cursor.getString(index);
            ForCp.setActions(getApplicationContext(), imagePath);
            ForCp.saveToCpForAction(getApplicationContext(), extras.getString("tag"));
            ForCp.checkLaunch(getApplicationContext());
            layout.setVisibility(View.GONE);
            launchHome();
            cursor.close();

        }
    }
    void launchHome(){
        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }
}
