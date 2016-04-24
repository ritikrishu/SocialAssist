package android.g38.ritik.AdaptersAndAnimators;

import android.content.Context;
import android.g38.sanyam.contentprovider.RecipeCP;
import android.g38.sanyam.contentprovider.Tasks;
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
import java.util.HashMap;

/**
 * Created by ritik on 4/24/2016.
 */
public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.DataObjectHolder> {

    HashMap<String,ArrayList<String>> listHashMap;
    LayoutInflater mInflater;
    int count;

    public void getWholeList(Context context){
        listHashMap = RecipeCP.getDataForAdapter(context);
    }

    public RecipesAdapter(Context context, LayoutInflater mInflater){
            getWholeList(context);
            this.mInflater = mInflater;
            this.count = listHashMap.get(Tasks.MODE).size();
    }
    public void refresh(String query, Context context){
        listHashMap = RecipeCP.getDataForSearch(context,query);
        count = listHashMap.get(Tasks.RECIPE_NAME).size();
        if(count>0)
            notifyDataSetChanged();
    }
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.list_recipe, parent, false);

        DataObjectHolder dataObjectHolder = new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(final DataObjectHolder holder, int position) {
        holder.ivIf.setImageResource(Integer.valueOf(listHashMap.get(Tasks.IF).get(position)));
        holder.ivThen.setImageResource(Integer.valueOf(listHashMap.get(Tasks.THEN).get(position)));
        holder.shrtDes.setText(listHashMap.get(Tasks.RECIPE_NAME).get(position));
        holder.onOff.setSelected(listHashMap.get(Tasks.MODE).get(position).equalsIgnoreCase("on"));
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        holder.container.setLayoutParams(layoutParams);
        YoYo.with(Techniques.StandUp).duration(1000).playOn(holder.itemView);
        holder.onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonClick(holder);
            }
        });
    }


    @Override
    public int getItemCount() {
        return count;
    }

    public class DataObjectHolder extends RecyclerView.ViewHolder implements RecyclerView.OnClickListener{

        ImageView ivIf, ivThen;
        TextView shrtDes;
        ToggleButton onOff;
        RelativeLayout container;
        public DataObjectHolder(View itemView) {
            super(itemView);
            ivIf = (ImageView) itemView.findViewById(R.id.ivIf);
            ivThen = (ImageView) itemView.findViewById(R.id.ivThen);
            shrtDes = (TextView) itemView.findViewById(R.id.tvShrtDes);
            onOff = (ToggleButton) itemView.findViewById(R.id.tbOnOff);
            container = (RelativeLayout) itemView.findViewById(R.id.rlRecipe);

        }

        @Override
        public void onClick(View v) {

        }
    }


    private void buttonClick(DataObjectHolder holder){
        if(holder.onOff.getText().equals("ON")){
            //code for toogle button on
        }
        else{
            //code for toogle button off
        }
    }
}
