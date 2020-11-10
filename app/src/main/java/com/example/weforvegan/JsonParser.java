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
            api_recipeArray = new String[api_jsonArray.length()*3];
            LikeRecipePage.api_recipe_count = api_jsonArray.length();

            for(int i=0; i< api_jsonArray.length()*3; i=i+3) {
                JSONObject location = api_jsonArray.getJSONObject(i);
                api_recipeArray[i] = location.optString("recipe_rp_idx");
                api_recipeArray[i+1] = location.optString("recipe_rp_name");
                api_recipeArray[i+2] = location.optString("recipe_rp_source");
                System.out.println(i+"="+ api_recipeArray[i]);
                System.out.println(i+"="+api_recipeArray[i+1]);
                System.out.println(i+"="+api_recipeArray[i+2]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return api_recipeArray;
    }
    //LikeRecipePage.java 에서 사용
    public String[] like_receipe_parse2(String jsonString){
        String[] sns_recipeArray = null;

        try{
            JSONArray api_jsonArray = new JSONObject(jsonString).getJSONArray("sns_recipe");
            sns_recipeArray = new String[api_jsonArray.length()*3];  //sns recipe가 3개면 9개 생성
            LikeRecipePage.sns_recipe_count = api_jsonArray.length();

            for(int i=0; i<api_jsonArray.length()*3; i=i+3) {
                JSONObject sns_recipe = api_jsonArray.getJSONObject(i);
                sns_recipeArray[i] = sns_recipe.optString("recipe_rp_idx");
                sns_recipeArray[i+1] = sns_recipe.optString("recipe_rp_name");
                sns_recipeArray[i+2] = sns_recipe.optString("recipe_rp_source");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return sns_recipeArray;
    }

    //api 레시피 가져오는 곳에서 사용
    public ApiRecipe[] get_api_recipe(String jsonString){
        ApiRecipe[] apiRecipes = null;
        try{
            JSONArray api_jsonArray = new JSONObject(jsonString).getJSONArray("api_recipe");
            apiRecipes = new ApiRecipe[api_jsonArray.length()];
            for(int i=0; i<api_jsonArray.length(); i++) {
                JSONObject api_recipes= api_jsonArray.getJSONObject(i);
                int api_idx = Integer.parseInt(api_recipes.optString("recipe_rp_idx"));
                String api_recipe_name = api_recipes.optString("recipe_rp_name");
                String api_imgurl = api_recipes.optString("api_imgurlbig");
                String api_ingredient = api_recipes.optString("api_recipe");
                String api_recipe = api_recipes.optString("api_ingredient");
                String api_source = api_recipes.optString("api_source");
                apiRecipes[i] = new ApiRecipe(api_idx, api_recipe_name, api_imgurl, api_ingredient, api_recipe, api_source);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return apiRecipes;
    }

    public SNSRecipe[] get_sns_recipe(String jsonString){
        SNSRecipe[] snsRecipes = null;
        try{
            JSONArray api_jsonArray = new JSONObject(jsonString).getJSONArray("sns_recipe");
            snsRecipes = new SNSRecipe[api_jsonArray.length()];
            for(int i=0; i<api_jsonArray.length(); i++) {
                JSONObject sns_recipes= api_jsonArray.getJSONObject(i);
                int sns_idx = Integer.parseInt(sns_recipes.optString("recipe_rp_idx"));
                String sns_recipe_name = sns_recipes.optString("recipe_rp_name");
                String sns_source = sns_recipes.optString("recipe_rp_source");
                String sns_url = sns_recipes.optString("sns_url");
                String sns_imgurl=sns_recipes.optString("sns_imgurl");
                snsRecipes[i] = new SNSRecipe(sns_idx, sns_recipe_name, sns_url, sns_imgurl, sns_source);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return snsRecipes;
    }

    public String get_is_like(String jsonString){
        String result = "";
        try{
            JSONObject jsonObject = new JSONObject(jsonString);
            result = jsonObject.getString("isLike");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return result;
    }
}
