package android.g38.sanyam.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.g38.sanyam.DAO.BeanRecipe;
import android.g38.sanyam.DAO.RecipeHistory;
import android.g38.sanyam.contentprovider.Tasks;
import android.g38.socialassist.HomeActivity;
import android.g38.socialassist.R;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by SANYAM TYAGI on 4/21/2016.
 */
public class Notify {
    Context context;
    String rId;
    String data;
    public Notify(Context context){
        this.context=context;
    }
    public void buildNotification(String title, String msg,String rId,String data){
        this.rId=rId;
        this.data=data;
        if(title.equals("action")){
            changeStatusValue("done");
            return;
        }
        Intent notificationIntent = new Intent(context,HomeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,notificationIntent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context).setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title).setContentText(msg).setAutoCancel(true)
                .setContentIntent(pendingIntent).setDefaults(Notification.DEFAULT_VIBRATE);


        NotificationManager notificationmanager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationmanager.notify(0, builder.build());

        if(msg.equals("Provided email ID is not valid")||msg.equals("No Internet Connection Available")){
            changeStatusValue("failed");
        }else {
            changeStatusValue("done");
        }
    }
    public void changeStatusValue( String status){
        ContentValues values = new ContentValues();
        values.put(Tasks.STATUS, status);
        values.put(Tasks.DATA, data);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd \n HH:mm");
        values.put(Tasks.TIME, df.format(Calendar.getInstance().getTime()));
        String mSelectionClause = Tasks._ID +  " LIKE ?";
        String[] mSelectionArgs = {rId};
        int c = context.getContentResolver().update(Tasks.CONTENT_URI_FOR_RECIPE, values, mSelectionClause, mSelectionArgs);
        storeRecipe();
    }

    private void storeRecipe() {
        RecipeHistory history=new RecipeHistory(context);
        String mSelectionClause = Tasks._ID +  " LIKE ?";
        String[] mSelectionArgs = {rId};
        Cursor c = context.getContentResolver().query(Tasks.CONTENT_URI_FOR_RECIPE, null, mSelectionClause, mSelectionArgs, null);

        if(c.moveToFirst()){
            BeanRecipe beanRecipe=new BeanRecipe();
            beanRecipe.setIf(c.getString(c.getColumnIndex(Tasks.IF)));
            beanRecipe.setThen(c.getString(c.getColumnIndex(Tasks.THEN)));
            beanRecipe.setData(c.getString(c.getColumnIndex(Tasks.DATA)));
            beanRecipe.setBase(c.getString(c.getColumnIndex(Tasks.BASE)));
            beanRecipe.setStatus(c.getString(c.getColumnIndex(Tasks.STATUS)));
            beanRecipe.setTime(c.getString(c.getColumnIndex(Tasks.TIME)));
            beanRecipe.setId(c.getString(c.getColumnIndex(Tasks._ID)));
            beanRecipe.setName(c.getString(c.getColumnIndex(Tasks.RECIPE_NAME)));
            history.insertData(beanRecipe);

        }
    }
    //"Provided email ID is not valid"
    //"No Internet Connection Available"

}
