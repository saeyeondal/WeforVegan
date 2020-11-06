package com.example.weforvegan;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonParser {
    public static String message_parse(String jsonString){
        String result = "";
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            result = jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String[] inform_parse(String jsonString){
        String[] userArray = null;

        Log.d("LOG", "들어왔어");
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject result = jsonObject.getJSONObject("message");
            Log.d("LOG", result.toString());
            userArray = new String[7];
            userArray[0] = result.optString("usr_id");
            userArray[1] = result.optString("usr_pw");
            userArray[2] = result.optString("usr_name");
            userArray[3]= result.optString("usr_sex");
            userArray[4]= result.optString("usr_birth");
            userArray[5]= result.optString("usr_vegantype");
            userArray[6]= result.optString("usr_likeidx");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userArray;
    }
}
