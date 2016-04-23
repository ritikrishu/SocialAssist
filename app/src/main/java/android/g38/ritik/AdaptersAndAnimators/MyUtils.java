package android.g38.ritik.AdaptersAndAnimators;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.animation.AnticipateInterpolator;

/**
 * Created by ritik on 4/23/2016.
 */
public class MyUtils {
    public static void homeActivityList(HomeListAdapter.DataObjectHolder holder){
        ObjectAnimator oa1 = ObjectAnimator.ofFloat(holder.itemView,"translationY",0,100);
        oa1.setDuration(1000);
        oa1.start();
    }
    public static void homeActivityListFinal(HomeListAdapter.DataObjectHolder holder, boolean goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(holder.itemView, "scaleX" ,0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(holder.itemView, "scaleY", 0.5F, 0.8F, 1.0F);
        ObjectAnimator animatorTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 300 : -300, 0);
        ObjectAnimator animatorTranslateX = ObjectAnimator.ofFloat(holder.itemView, "translationX", -50, 50, -30, 30, -20, 20, -5, 5, 0);
        animatorSet.playTogether(animatorTranslateX, animatorTranslateY, animatorScaleX, animatorScaleY);
        animatorSet.setInterpolator(new AnticipateInterpolator());
        animatorSet.setDuration(1000);
        animatorSet.start();
    }
}
