package com.example.cloudint.cloudmain;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
    //String url = "https://be-saas.cloudike.com/api/1/metadata_full_listing/";

    @Override
    protected Void doInBackground(String... getParams) {
        ArrayList<String> metadataParams = new ArrayList<String>();

        try {

            for(String parameter : getParams){
                metadataParams.add(parameter);
            }

            String token = metadataParams.get(0);

            String url = "https://be-saas.cloudike.com/api/1/metadata_full_listing/";

            String listing_request_id = "";

            //Get listing_request_id
            listing_request_id = cloudikeRequest("listing_request_id", token, url, listing_request_id);
            listing_request_id = getListingRequestId(listing_request_id);

            Log.d(LOG_TAG, listing_request_id);

            //Get jsonURL
            String jsonURL = cloudikeRequest("jsonURL", token, url, listing_request_id);

            Log.d(LOG_TAG, jsonURL);

            jsonURL = getJsonURL(jsonURL);

            Log.d(LOG_TAG, jsonURL);

            String jsonObject = getJsonObject(jsonURL);

            Log.d(LOG_TAG, jsonObject);




        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    String cloudikeRequest (String item, String token, String url, String listing_request_id) throws IOException {
        Log.d(LOG_TAG, item + " requested");

        if (item.equals("jsonURL")){
            List<BasicNameValuePair> setParams = new LinkedList<BasicNameValuePair>();
            setParams.add(new BasicNameValuePair("listing_request_id", listing_request_id));
            String paramString = URLEncodedUtils.format(setParams, "utf-8");
            url+="?"+paramString;
        }
        HttpClient client = new DefaultHttpClient();
        HttpGet get = new HttpGet(url);
        get.setHeader("Mountbit-Auth", token);

        HttpResponse response = client.execute(get);
        return getResponse(response);
    }

    String getResponse(HttpResponse response) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return total.toString();
    }

    String getJsonURL(String response) {
        try {
            JsonObject o = new JsonParser().parse(response).getAsJsonObject();
            String url = o.getAsJsonPrimitive("url").toString().substring(1, o.getAsJsonPrimitive("url").toString().length() - 1);
            return url;
        }catch (Exception e){
            e.printStackTrace();
            getJsonURL(response);
        }
      return "";
    }

    String getListingRequestId(String response){
        JsonObject o = new JsonParser().parse(response).getAsJsonObject();
        String listing_request = o.getAsJsonPrimitive("listing_request_id").toString().substring(1,o.getAsJsonPrimitive("listing_request_id").toString().length()-1);
        return listing_request;
    }

    String getJsonObject(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        try{
            InputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader r = new BufferedReader(new InputStreamReader(in));
            StringBuilder total = new StringBuilder();
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line);
            }
            return total.toString();
        }
        finally {
            connection.disconnect();
        }

    }


}
