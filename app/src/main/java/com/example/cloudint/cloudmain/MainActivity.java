package com.example.cloudint.cloudmain;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

import java.util.concurrent.ExecutionException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class MainActivity extends ActionBarActivity {

    String LOG_TAG = "MainActivity";
    CloudikePreferences cloudikePreferences = new CloudikePreferences(this);

    @InjectView(R.id.username) EditText username;
    @InjectView(R.id.password) EditText password;
    @InjectView(R.id.send_button) Button button;

    @OnClick(R.id.send_button)
    public void submit(View view) {
       button.setText("Подождите...");
       sendRequest(username.getText().toString(),password.getText().toString());
       signIn();
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);


    }

    void sendRequest(String username, String password)
    {
        Log.d(LOG_TAG, "Username: " + username + " Password: " + password);

        CloudikeFetchToken cloudikeFetch = new CloudikeFetchToken();
        cloudikeFetch.execute(username,password);

        String token = new String();
        try {
            token = cloudikeFetch.get();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        Log.d(LOG_TAG, token);

        cloudikePreferences.savePref("token", token);


        button.setText("Войти");
    }

    void signIn(){

        String token = cloudikePreferences.loadPref("token");
        if (token.length() > 0){


            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
            this.finish();

        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Ошибка авторизации")
                    .setMessage("Внимание! Произошла ошибка авторизации на сервере Cloudike. Пожалуйста. " +
                            "проверьте наличие на вашем устройстве доступа к сети интернет и правильность вводимых данных.")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    })
                    .show();

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
