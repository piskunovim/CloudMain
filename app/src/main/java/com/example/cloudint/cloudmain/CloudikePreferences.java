package com.example.cloudint.cloudmain;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * Created by PiskunovI on 25.11.2014.
 */
public class CloudikePreferences {

    Context context;
    SharedPreferences cloudPref;

    CloudikePreferences(Context _context){
        this.context = _context;
    }

    void savePref(String title, String preference) {
        cloudPref = context.getSharedPreferences("CloudikePref", Context.MODE_PRIVATE);
        Editor ed = cloudPref.edit();
        ed.putString(title, preference);
        ed.commit();
    }

    String loadPref(String title) {
        cloudPref = context.getSharedPreferences("CloudikePref", Context.MODE_PRIVATE);
        String savedText = cloudPref.getString(title, "");
        return savedText;
    }
}
