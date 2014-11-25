package com.example.cloudint.cloudmain;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by PiskunovI on 25.11.2014.
 */
public class CloudikeFetchList extends AsyncTask<String,Void,Void> {

    String LOG_TAG = "CloudikeFetchList";

    @Override
    protected Void doInBackground(String... getParams) {
        ArrayList<String> metadataParams = new ArrayList<String>();

        try {

            for(String parameter : getParams){
                metadataParams.add(parameter);
            }

            String token = metadataParams.get(0);

            String url = "https://be-saas.cloudike.com/api/1/metadata/";

            List<BasicNameValuePair> setParams = new LinkedList<BasicNameValuePair>();
            setParams.add(new BasicNameValuePair("list", "false"));
            String paramString = URLEncodedUtils.format(setParams, "utf-8");

            url+="?"+paramString;

            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            get.setHeader("Mountbit-Auth",token);

            HttpResponse response = client.execute(get);

            BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }

            Log.d(LOG_TAG, total.toString());
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
