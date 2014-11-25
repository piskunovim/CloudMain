package com.example.cloudint.cloudmain;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by PiskunovI on 25.11.2014.
 */
public class MainPageActivity extends Activity {
    CloudikePreferences cloudikePreferences = new CloudikePreferences(this);

    //String LOG_TAG = "MainPageActivity";

    @InjectView(R.id.token) TextView tokenTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.inject(this);

        tokenTextView.setText("Token: "+ cloudikePreferences.loadPref("token"));

        CloudikeFetchList cloudikeFetchList = new CloudikeFetchList();
        cloudikeFetchList.execute(cloudikePreferences.loadPref("token"));
    }
}
