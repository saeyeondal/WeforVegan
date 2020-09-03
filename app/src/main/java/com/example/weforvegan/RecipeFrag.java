package com.example.weforvegan;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecipeFrag extends Fragment {
    private WebView wv;
    static String selectedMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recipe_frag, container, false);
        wv = (WebView)rootView.findViewById(R.id.wv);
        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        switch(selectedMenu){
            case "채식을 위한 고기뺀 '잡채';당면이 불지 않는 비법":
                wv.loadUrl("https://www.10000recipe.com/recipe/6864194");
                break;
            case "2그릇 순삭 가능!! 간단하게 매콤 잡채 만드는 법, 별미 잡채, 잡채밥, 채식":
                wv.loadUrl("https://www.10000recipe.com/recipe/6907522");
                break;
            case "[비건채식] 잡채":
                wv.loadUrl("https://www.10000recipe.com/recipe/6865656");
                break;
        }


        return rootView;
    }
}
