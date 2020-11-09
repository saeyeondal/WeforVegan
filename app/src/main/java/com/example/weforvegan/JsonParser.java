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

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject result = jsonObject.getJSONObject("message");
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

    //LikeRecipePage.java 에서 사용
    public static String[] like_receipe_parse1(String jsonString){
        String[] api_recipeArray = null;

        try{
            JSONArray api_jsonArray = new JSONObject(jsonString).getJSONArray("api_recipe");
            api_recipeArray = new String[3];
            LikeRecipePage.api_recipe_count = api_jsonArray.length();

            for(int i=0; i<api_jsonArray.length(); i++) {
                JSONObject location = api_jsonArray.getJSONObject(i);
                api_recipeArray[0] = location.optString("recipe_rp_idx");
                api_recipeArray[1] = location.optString("recipe_rp_name");
                api_recipeArray[2] = location.optString("recipe_rp_source");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return api_recipeArray;
    }
    //LikeRecipePage.java 에서 사용
    public static String[] like_receipe_parse2(String jsonString){
        String[] sns_recipeArray = null;

        try{
            JSONArray api_jsonArray = new JSONObject(jsonString).getJSONArray("sns_recipe");
            sns_recipeArray = new String[3];
            LikeRecipePage.sns_recipe_count = api_jsonArray.length();

            for(int i=0; i<api_jsonArray.length(); i++) {
                JSONObject location = api_jsonArray.getJSONObject(i);
                sns_recipeArray[0] = location.optString("recipe_rp_idx");
                sns_recipeArray[1] = location.optString("recipe_rp_name");
                sns_recipeArray[2] = location.optString("recipe_rp_source");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sns_recipeArray;
    }

    //api 레시피 가져오는 곳에서 사용
    /*public static String[] get_api_recipe(String jsonString){

        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            JSONObject result = jsonObject.getJSONObject("api_recipe");
            String api_idx = result.optString("recipe_rp_idx");
            String api_recipe_name = result.optString("recipe_rp_name");
            String api_imgurl = result.optString("api_imgurlbig");
            String api_ingredient = result.optString("api_recipe");
            String api_reicpe = result.optString("api_ingredient");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userArray;
    }*/
}
