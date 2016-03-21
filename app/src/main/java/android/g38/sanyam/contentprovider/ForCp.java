package android.g38.sanyam.contentprovider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by SANYAM TYAGI on 3/21/2016.
 */
public class ForCp {
    static SharedPreferences forCp ;
    static public void  setBase(Context context,String base){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        if(!(forCp.getBoolean("addDone",false))){
            SharedPreferences.Editor editor=forCp.edit();
            editor.clear();
            editor.putBoolean("add", true);
            editor.putString("base",base);
            editor.commit();

        }
    }

    static public void  setDone(Context context){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putBoolean("adddone",true);
        editor.commit();
    }

    static public void  setOthers(Context context,String others){
        forCp= context.getSharedPreferences("forCp", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = forCp.edit();
        editor.putBoolean("others",true);
        editor.putString("data",others);
        editor.commit();
    }

}
