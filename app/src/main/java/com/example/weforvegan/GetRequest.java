package com.example.weforvegan;

import android.os.AsyncTask;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRequest extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... urlstr) {
        String result = null;
        try{
            URL url = new URL(urlstr[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setDoInput(true);

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

    /*public static String[] jsonParser_users(String jsonString){
        String[] userArray = null;

        try{
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("user_select");
            userArray = new String[12];
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject location = jsonArray.getJSONObject(i);
                userArray[0] = location.optString("user_name");
                userArray[1] = location.optString("user_id");
                userArray[2] = location.optString("user_passwd");
                userArray[3]= location.optString("user_email");
                userArray[4]= location.optString("user_phone");
                userArray[5]= location.optString("user_stickNum");
                userArray[6]= location.optString("user_address");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userArray;
    }*/
}