package com.example.weforvegan;

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

        try{
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("message");
            userArray = new String[7];
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject location = jsonArray.getJSONObject(i);
                userArray[0] = location.optString("usr_id");
                userArray[1] = location.optString("usr_pw");
                userArray[2] = location.optString("usr_name");
                userArray[3]= location.optString("usr_sex");
                userArray[4]= location.optString("usr_birth");
                userArray[5]= location.optString("usr_vegantype");
                userArray[6]= location.optString("usr_likeidx");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userArray;
    }
}
