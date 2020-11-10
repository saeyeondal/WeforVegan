package com.example.weforvegan;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutionException;

public class SnsRecipeFrag extends AppCompatActivity {
    private WebView wv;
    Button closeBtn;
    Button favorite_btn1;
    Boolean favorite_selected;
    String recipe_idx;
    String sns_recipe_url;
    String sns_recipe_src;
    TextView sns_recipe_src_txt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 액션바 없애기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sns_recipe_frag);

        closeBtn = (Button)findViewById(R.id.close_btn);
        favorite_btn1 = (Button)findViewById(R.id.favorite_btn);
        sns_recipe_src_txt = (TextView)findViewById(R.id.sns_recipe_source);
        wv = findViewById(R.id.wv);

        recipe_idx = getIntent().getStringExtra("recipe_idx");
        sns_recipe_url = getIntent().getStringExtra("sns_recipe_url");
        sns_recipe_src = getIntent().getStringExtra("sns_recipe_src");
        sns_recipe_src_txt.setText(sns_recipe_src);

        GetRequest request = new GetRequest(getApplicationContext());
        try {
            String response = request.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart/index?rp_idx=" + recipe_idx).get();
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
                    Toast.makeText(getApplicationContext(), "찜 목록에 추가되었습니다.", Toast.LENGTH_SHORT).show();
                }
                else {
                    favorite_btn1.setSelected(false);
                    favorite_selected = false;
                    PostRequest httpTask = new PostRequest(getApplicationContext());
                    httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart/cancle", "rp_idx", recipe_idx);
                    Toast.makeText(getApplicationContext(), "찜 목록에서 삭제되었습니다.", Toast.LENGTH_SHORT).show();
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
