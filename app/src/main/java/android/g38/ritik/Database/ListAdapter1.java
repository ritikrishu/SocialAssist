package android.g38.ritik.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.g38.socialassist.R;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by ritik on 4/21/2016.
 */
public class ListAdapter1 extends BaseAdapter {
    private int count;
    private LayoutInflater mInflater;
    HashMap<String, ArrayList<String>> listHashMap;
    LinearLayout llEven, llOdd;
    TextView tvTime, tvSrtDes, tvDetail;
    int[] getImageResource = {R.drawable.ic_list_one, R.drawable.ic_list_two, R.drawable.ic_list_three, R.drawable.ic_list_four, R.drawable.ic_list_five, R.drawable.ic_list_six, R.drawable.ic_list_seven, R.drawable.ic_list_eight, R.drawable.ic_list_nine};
    ImageView ivEvent, ivTrigger, ivAction, ivFirst;
    public ListAdapter1(Context context, LayoutInflater mInflater){

        this.mInflater = mInflater;
        SocialAssistDBHelper dbHelper = new SocialAssistDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        this.listHashMap = SocialAssistDBHelper.getListItems(db);
        db.close();
        dbHelper.close();
        this.count = listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TIME).size() * 2;
    }
    @Override
    public int getCount() {
        return count + 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.list, parent, false);
        }
            llEven = (LinearLayout) convertView.findViewById(R.id.llEven);
            llOdd = (LinearLayout) convertView.findViewById(R.id.llOdd);
            tvTime = (TextView) convertView.findViewById(R.id.tvTime);
            tvSrtDes = (TextView) convertView.findViewById(R.id.tvShortDes);
            tvDetail = (TextView) convertView.findViewById(R.id.tvDetail);
            ivEvent = (ImageView) convertView.findViewById(R.id.ivEvent);
            ivAction = (ImageView) convertView.findViewById(R.id.ivAction);
            ivTrigger = (ImageView) convertView.findViewById(R.id.ivTrigger);
            ivFirst = (ImageView) convertView.findViewById(R.id.ivFirst);

        if(position == 0){
            llEven.setVisibility(View.GONE);
            llOdd.setVisibility(View.GONE);
            ivFirst.setVisibility(View.VISIBLE);
            ivFirst.setImageResource(generateSeggestion());
        }
        else if(position == 1){
            llEven.setVisibility(View.GONE);
            llOdd.setVisibility(View.VISIBLE);
            ivFirst.setVisibility(View.GONE);
            tvTime.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TIME).get(0));
            tvSrtDes.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_SHORTDES).get(0));
         //   ivEvent.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_EVENT).get(0)));
            ivAction.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_ACTION).get(0)));
            ivTrigger.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TRIGGER).get(0)));
        }
        else if(!(position%2 == 0)){
            llEven.setVisibility(View.GONE);
            llOdd.setVisibility(View.VISIBLE);
            ivFirst.setVisibility(View.GONE);
            tvTime.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TIME).get(position/2 - 1));
            tvSrtDes.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_SHORTDES).get(position/2 - 1));
            setIvEvent(position);
            setIvAction(position);
            setIvTrigger(position);
        }
        else {
            ivFirst.setVisibility(View.GONE);
            llOdd.setVisibility(View.GONE);
            llEven.setVisibility(View.VISIBLE);
            tvDetail.setText(listHashMap.get(DataBaseContracter.OddEntry.COLUMN_DETAIL).get(position/2 - 1));
        }

        return convertView;
    }

    private int generateSeggestion(){
        return getImageResource[new Random().nextInt(9)];
    }

    private void setIvEvent(int position){
        ivEvent.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_EVENT).get(position/2 - 1)));
    }
    private void setIvAction(int position){
        ivAction.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_ACTION).get(position/2 - 1)));
    }
    private void setIvTrigger(int position){
        ivTrigger.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TRIGGER).get(position/2 - 1)));
    }
}
