package com.example.weforvegan;

public class ApiRecipe {
    int api_idx;
    String api_recipe_name;
    String api_imgurl;
    String api_recipe;
    String api_ingredient;
    String api_source;

    public ApiRecipe(int api_idx, String api_recipe_name, String api_imgurl, String api_ingredient, String api_recipe, String api_source){
        this.api_idx = api_idx;
        this.api_recipe_name = api_recipe_name;
        this.api_imgurl = api_imgurl;
        this.api_recipe = api_recipe;
        this.api_ingredient = api_ingredient;
        this.api_source = api_source;
    }

    public int getApi_idx() {
        return api_idx;
    }

    public void setApi_idx(int api_idx) {
        this.api_idx = api_idx;
    }

    public String getApi_recipe_name() {
        return api_recipe_name;
    }

    public void setApi_recipe_name(String api_recipe_name) {
        this.api_recipe_name = api_recipe_name;
    }

    public String getApi_imgurl() {
        return api_imgurl;
    }

    public void setApi_imgurl(String api_imgurl) {
        this.api_imgurl = api_imgurl;
    }

    public String getApi_ingredient() {
        return api_ingredient;
    }

    public void setApi_ingredient(String api_ingredient) {
        this.api_ingredient = api_ingredient;
    }

    public String getApi_recipe() {
        return api_recipe;
    }

    public void setApi_recipe(String api_recipe) {
        this.api_recipe = api_recipe;
    }

    public String getApi_source(){return api_source;}

    public void setApi_source(String api_source) {this.api_source = api_source;}

}
