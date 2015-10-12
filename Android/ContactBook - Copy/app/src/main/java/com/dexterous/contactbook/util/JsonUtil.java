package com.dexterous.contactbook.util;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Honey on 4/12/2015.
 */
public class JsonUtil {

    public JSONArray readJsonFeed(String url) {
        Log.e("KARTIK", url);
        StringBuilder stringBuilder = new StringBuilder();
        JSONArray jsonArray = new JSONArray();
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);

            StatusLine statusLine = httpResponse.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity httpEntity = httpResponse.getEntity();
                InputStream inputStream = httpEntity.getContent();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
            }
            jsonArray = new JSONArray(stringBuilder.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    public static String getData(JSONObject jsonObject, String key) {
        try {
            String data = jsonObject.getString(key);
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
