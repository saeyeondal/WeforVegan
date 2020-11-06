package com.example.weforvegan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRequest extends AsyncTask<String, Void, String> {
    HttpURLConnection con;
    Context get_context;

    public GetRequest(Context get_context){
        this.get_context = get_context;
    }
    @Override
    protected String doInBackground(String... urlstr) {
        String result = null;
        try{
            URL url = new URL(urlstr[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setDoInput(true);

            SharedPreferences pref = get_context.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE);
            String sessionid = pref.getString("sessionid", null);
            if(sessionid != null){
                con.setRequestProperty("Cookie", sessionid);
            }

            InputStreamReader inputStrteam = new InputStreamReader(con.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(inputStrteam);
            StringBuilder builder = new StringBuilder();
            String resultStr;

            while((resultStr = reader.readLine()) != null){
                builder.append(resultStr + "\n");
            }
            result = builder.toString();
            reader.close();
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }
}