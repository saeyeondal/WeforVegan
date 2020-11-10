package com.example.weforvegan;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import java.util.concurrent.ExecutionException;

public class RecipeFrag extends AppCompatActivity {
    private WebView wv;
    Button closeBtn;
    static String selectedMenu;
    Button favorite_btn1;
    Boolean favorite_selected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 액션바 없애기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_frag);

        closeBtn = (Button)findViewById(R.id.close_btn);
        favorite_btn1 = (Button)findViewById(R.id.favorite_btn);
        wv = findViewById(R.id.wv);

        final String recipe_idx = getIntent().getStringExtra("recipe_idx");
        final String sns_recipe_url = getIntent().getStringExtra("sns_recipe_url");
        final String sns_recipe_src = getIntent().getStringExtra("sns_recipe_src");

        GetRequest request = new GetRequest(getApplicationContext());
        try {
            String response = request.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart/index?rp_idx=" + recipe_idx).get();
            System.out.println(response);
            JsonParser jsonParser = new JsonParser();
            String isLike = jsonParser.get_is_like(response);
            if(isLike.equals("true")) {
                favorite_selected = true;
                favorite_btn1.setSelected(true);
            }
            else {
                favorite_selected = false;
                favorite_btn1.setSelected(false);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        favorite_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!favorite_selected) {
                    favorite_btn1.setSelected(true);
                    favorite_selected = true;
                    PostRequest httpTask = new PostRequest(getApplicationContext());
                    httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart/add", "rp_idx", recipe_idx);
                }
                else {
                    favorite_btn1.setSelected(false);
                    favorite_selected = false;
                    PostRequest httpTask = new PostRequest(getApplicationContext());
                    httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart/cancle", "rp_idx", recipe_idx);
                }
            }
        });

        WebSettings webSettings = wv.getSettings();
        webSettings.setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl(sns_recipe_url);

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
