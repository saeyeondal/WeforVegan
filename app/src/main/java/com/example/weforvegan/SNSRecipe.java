package com.example.weforvegan;

public class SNSRecipe {
    String snsIdx;
    String snsUrl;
    String snsImgUrl;

    public SNSRecipe(String snsIdx, String snsUrl, String snsImgUrl){
        this.snsIdx = snsIdx;
        this.snsImgUrl = snsImgUrl;
        this.snsUrl = snsUrl;
    }

    public String getSnsIdx() {
        return snsIdx;
    }

    public void setSnsIdx(String snsIdx) {
        this.snsIdx = snsIdx;
    }

    public String getSnsUrl() {
        return snsUrl;
    }

    public void setSnsUrl(String snsUrl) {
        this.snsUrl = snsUrl;
    }

    public String getSnsImgUrl() {
        return snsImgUrl;
    }

    public void setSnsImgUrl(String snsImgUrl) {
        this.snsImgUrl = snsImgUrl;
    }
}
