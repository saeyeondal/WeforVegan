package com.example.weforvegan;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class RecipeFrag extends AppCompatActivity {
    private WebView wv;
    Button closeBtn;
    static String selectedMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_frag);
        closeBtn = (Button)findViewById(R.id.close_btn);
        wv = findViewById(R.id.wv);
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

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
