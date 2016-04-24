package android.g38.ritik.AdaptersAndAnimators;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.g38.ritik.Database.DataBaseContracter;
import android.g38.ritik.Database.SocialAssistDBHelper;
import android.g38.socialassist.R;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Created by ritik on 4/23/2016.
 */
public class HomeListAdapter extends RecyclerView.Adapter<HomeListAdapter.DataObjectHolder> {

    private int count, previousPosition = 0;
    HashMap<String, ArrayList<String>> listHashMap;
    LayoutInflater mInflater;
    int[] getImageResource = {R.drawable.ic_list_one, R.drawable.ic_list_two, R.drawable.ic_list_three, R.drawable.ic_list_four, R.drawable.ic_list_five, R.drawable.ic_list_six, R.drawable.ic_list_seven, R.drawable.ic_list_eight, R.drawable.ic_list_nine};
    public HomeListAdapter(Context context, LayoutInflater mLayoutInflater){
        this.mInflater = mLayoutInflater;
        SocialAssistDBHelper dbHelper = new SocialAssistDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        this.listHashMap = SocialAssistDBHelper.getListItems(db);
        db.close();
        dbHelper.close();
        count = listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TIME).size() * 2 + 1;
    }


    public class DataObjectHolder extends RecyclerView.ViewHolder
            implements View
            .OnClickListener{
        LinearLayout llEven, llOdd;
        TextView tvTime, tvSrtDes, tvDetail;

        ImageView ivEvent, ivTrigger, ivAction, ivFirst;

        RelativeLayout container;

        public DataObjectHolder(View itemView) {
            super(itemView);
            llEven = (LinearLayout) itemView.findViewById(R.id.llEven);
            llOdd = (LinearLayout) itemView.findViewById(R.id.llOdd);
            tvTime = (TextView) itemView.findViewById(R.id.tvTime);
            tvSrtDes = (TextView) itemView.findViewById(R.id.tvShortDes);
            tvDetail = (TextView) itemView.findViewById(R.id.tvDetail);
            ivEvent = (ImageView) itemView.findViewById(R.id.ivEvent);
            ivAction = (ImageView) itemView.findViewById(R.id.ivAction);
            ivTrigger = (ImageView) itemView.findViewById(R.id.ivTrigger);
            ivFirst = (ImageView) itemView.findViewById(R.id.ivFirst);
            container = (RelativeLayout) itemView.findViewById(R.id.llMain);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Log.i("position", ""+getAdapterPosition());
        }
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {

        if (position == 0) {
            holder.llEven.setVisibility(View.GONE);
            holder.llOdd.setVisibility(View.GONE);
            holder.ivFirst.setVisibility(View.VISIBLE);
            holder.ivFirst.setImageResource(generateSeggestion());
        }
        else if (position == 1) {
            holder.llEven.setVisibility(View.GONE);
            holder.llOdd.setVisibility(View.VISIBLE);
            holder.ivFirst.setVisibility(View.GONE);
            holder.tvTime.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TIME).get(0));
            holder.tvSrtDes.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_SHORTDES).get(0));
            if(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_EVENT).get(0).equals("done"))
                holder.ivEvent.setImageResource(R.drawable.ic_icon_done);
            else
                holder.ivEvent.setImageResource(R.drawable.ic_icon_failed);
            holder.ivAction.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_ACTION).get(0)));
            holder.ivTrigger.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TRIGGER).get(0)));
        }
        else if (!(position % 2 == 0)) {
            holder.llEven.setVisibility(View.GONE);
            holder.llOdd.setVisibility(View.VISIBLE);
            holder.ivFirst.setVisibility(View.GONE);
            holder.tvTime.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TIME).get(Double.valueOf(Math.ceil((float)position / 2)).intValue() - 1));
            holder.tvSrtDes.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_SHORTDES).get(Double.valueOf(Math.ceil((float)position / 2)).intValue() - 1));

            if(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_EVENT).get(Double.valueOf(Math.ceil((float)position / 2)).intValue() - 1).equals("done"))
                holder.ivEvent.setImageResource(R.drawable.ic_icon_done);
            else
                holder.ivEvent.setImageResource(R.drawable.ic_icon_failed);

            holder.ivAction.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_ACTION).get(Double.valueOf(Math.ceil((float)position / 2)).intValue() - 1)));

            holder.ivTrigger.setImageResource(Integer.valueOf(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_TRIGGER).get(Double.valueOf(Math.ceil((float)position / 2)).intValue() - 1)));
        }
        else {
            holder.ivFirst.setVisibility(View.GONE);
            holder.llOdd.setVisibility(View.GONE);
            holder.llEven.setVisibility(View.VISIBLE);
            holder.tvDetail.setText(listHashMap.get(DataBaseContracter.EvenEntry.COLUMN_DETAIL).get(position / 2 - 1));
        }

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.container.setLayoutParams(layoutParams);
        YoYo.with(Techniques.RollIn).duration(500).playOn(holder.itemView);
    }

    @Override
    public int getItemCount() {
        return count;
    }
    private int generateSeggestion(){
        return getImageResource[new Random().nextInt(9)];
    }
}
