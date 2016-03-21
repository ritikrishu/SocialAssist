package android.g38.sanyam.facebook;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.g38.socialassist.R;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.facebook.FacebookSdk;
import com.facebook.share.ShareApi;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;

/**
 * Created by SANYAM TYAGI on 3/16/2016.
 */
public class ScheduledPost extends BroadcastReceiver
{

    Context context;
    Intent intent;
    Bundle extras;

    @Override
    public void onReceive(Context context, Intent intent) {
        FacebookSdk.sdkInitialize(context);
        if (!isNetworkAvailable(context)) {

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Could Not Post On Facebook").setContentText("No Internet Connection Available").setAutoCancel(true);

            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationmanager.notify(0, builder.build());
        }
        else{
            extras = intent.getExtras();
            this.context=context;
            this.intent=intent;
            postLink();
            // postImage();
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Posted on Facebook").setContentText(extras.getString("extra")).setAutoCancel(true);

            NotificationManager notificationmanager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);
            notificationmanager.notify(0, builder.build());
        }


    }



    void postLink(){
        ShareLinkContent content = new ShareLinkContent.Builder().setContentUrl(Uri.parse(extras.getString("extra"))).build();
        ShareApi.share(content, null);
    }

    void postImage(){
        Bitmap bitmap = BitmapFactory.decodeFile(extras.getString("imagePath"));
        SharePhoto photo = new SharePhoto.Builder()
                .setBitmap(bitmap)
                .build();
        SharePhotoContent conten = new SharePhotoContent.Builder()
                .addPhoto(photo)
                .build();
        ShareApi.share(conten, null);
    }


    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}

