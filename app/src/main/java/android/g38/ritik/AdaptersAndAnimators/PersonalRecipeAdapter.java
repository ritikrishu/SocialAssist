package android.g38.ritik.AdaptersAndAnimators;

import android.content.Intent;
import android.g38.socialassist.R;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.ArrayList;

/**
 * Created by ritik on 4/24/2016.
 */
public class PersonalRecipeAdapter extends RecyclerView.Adapter<PersonalRecipeAdapter.DataHolder> {

    Intent intent;
    ArrayList<String> arrayList;
    LayoutInflater mInflater;
    public PersonalRecipeAdapter(Intent intent, LayoutInflater mInflater){
        this.intent = intent;
        this.mInflater = mInflater;
        this.arrayList = intent.getStringArrayListExtra("data");
    }
    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_item_personal_recipe, parent, false);

        DataHolder dataObjectHolder = new DataHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {

        holder.ivIf.setImageResource(Integer.valueOf(arrayList.get(0)));
        holder.ivThen.setImageResource(Integer.valueOf(arrayList.get(1)));
        holder.tvRecipeName.setText(arrayList.get(2));
        holder.tvTrigger.setText(arrayList.get(3));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.container.setLayoutParams(layoutParams);
        YoYo.with(Techniques.RollIn).duration(1400).playOn(holder.ivIf);
        YoYo.with(Techniques.StandUp).duration(1400).playOn(holder.ivThen);
        YoYo.with(Techniques.Pulse).duration(1400).playOn(holder.tvIf);
        YoYo.with(Techniques.Landing).duration(1400).playOn(holder.tvThen);
        YoYo.with(Techniques.BounceIn).duration(1400).playOn(holder.tvRecipeName);
        YoYo.with(Techniques.FadeIn).duration(1400).playOn(holder.tvToggle);
        YoYo.with(Techniques.Shake).duration(1400).playOn(holder.tvTrigger);
        YoYo.with(Techniques.RotateIn).duration(5000).playOn(holder.button);
        YoYo.with(Techniques.Swing).duration(5000).playOn(holder.ivArrow);
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    public class DataHolder extends RecyclerView.ViewHolder {
        TextView tvIf, tvThen, tvRecipeName, tvToggle, tvTrigger;
        ImageView ivIf, ivThen, ivArrow;
        ToggleButton button;
        RelativeLayout container;
        public DataHolder(View itemView) {
            super(itemView);
            ivArrow = (ImageView) itemView.findViewById(R.id.ivArrow);
            tvIf = (TextView) itemView.findViewById(R.id.tvIf);
            tvThen = (TextView) itemView.findViewById(R.id.tvThen);
            tvRecipeName = (TextView) itemView.findViewById(R.id.tvRecipeName);
            tvToggle = (TextView) itemView.findViewById(R.id.tvToggle);
            tvTrigger = (TextView) itemView.findViewById(R.id.tvTrigger);
            ivIf = (ImageView) itemView.findViewById(R.id.ivIf);
            ivThen = (ImageView) itemView.findViewById(R.id.ivThen);
            button = (ToggleButton) itemView.findViewById(R.id.toggleButton);
            container = (RelativeLayout) itemView.findViewById(R.id.rlMain);
        }
    }
}
