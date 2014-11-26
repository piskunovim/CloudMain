package com.example.cloudint.cloudmain;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by PiskunovI on 25.11.2014.
 */
public class MainPageActivity extends Activity {
    CloudikePreferences cloudikePreferences = new CloudikePreferences(this);

    String LOG_TAG = "MainPageActivity";

    @InjectView(R.id.token) TextView tokenTextView;
    @InjectView(R.id.listView) ListView listView;

    @OnClick(R.id.buttonRefresh)
    public void submit(View view) {
        getFilesList();
    }

    @OnClick(R.id.buttonLogout)
    public void logout(View view) {
        cloudikePreferences.removePref("token");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }

    FilesArray filesArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        ButterKnife.inject(this);

         getFilesList();

    }

    void getFilesList(){
        tokenTextView.setText("Token: "+ cloudikePreferences.loadPref("token"));

        CloudikeFetchList cloudikeFetchList = new CloudikeFetchList();
        cloudikeFetchList.execute(cloudikePreferences.loadPref("token"));

        try {
            filesArray = cloudikeFetchList.get();
            if (filesArray != null) {
                ArrayList<String> arrayListItems = new ArrayList<String>();
                ArrayList<String> arrayListSize = new ArrayList<String>();

                for (int i = 0; i < filesArray.items.size(); i++) {
                    Log.d(LOG_TAG, "Files path: " + filesArray.items.get(i).path);
                    arrayListItems.add(filesArray.items.get(i).path);
                    Log.d(LOG_TAG, "Files MB: " + filesArray.items.get(i).mbytes);
                    arrayListSize.add(filesArray.items.get(i).mbytes);
                }

                ListingArrayAdapter adapter = new ListingArrayAdapter(MainPageActivity.this, arrayListItems.toArray(new String[arrayListItems.size()]), arrayListSize.toArray(new String[arrayListItems.size()]));

                listView.setAdapter(adapter);
            }
            else{
                tokenTextView.setText("Упс! Приложению не удалось связаться с сервером. Попробуйте нажать кнопку \"Обновить\" или произвести авторизацию заново.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


public class ListingArrayAdapter extends ArrayAdapter<String>{


    @InjectView(R.id.itemTextView) TextView itemTextView;
    @InjectView(R.id.sizeTextView) TextView sizeTextView;

        private final Activity context;
        private final String[] items;
        private final String[] size;

        public ListingArrayAdapter(Activity context, String[] items, String[] size) {
            super(context, R.layout.list_item, items);
            this.context = context;
            this.items = items;
            this.size = size;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.list_item, null, true);
            ButterKnife.inject(this, rowView);


            itemTextView.setText("Путь: " + items[position]);
            sizeTextView.setText(size[position] + "MB");
            return rowView;
        }
    }


}
