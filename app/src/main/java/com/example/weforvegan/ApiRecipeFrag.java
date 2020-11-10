package com.example.weforvegan;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class ApiRecipeFrag extends AppCompatActivity {
    Button closeBtn;
    Button favorite_btn1;
    Boolean favorite_selected;
    String recipe_idx;
    String api_recipe_imgurl;
    String api_recipe_name;
    String api_recipe_ingredient;
    String api_recipe_recipe;
    TextView api_recipe_name_txt;
    TextView api_recipe_ingredient_txt;
    TextView api_recipe_recipe_txt;
    Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE); // 액션바 없애기
        super.onCreate(savedInstanceState);
        setContentView(R.layout.api_recipe_frag);

        closeBtn = (Button)findViewById(R.id.close_btn);
        favorite_btn1 = (Button)findViewById(R.id.favorite_btn);
        api_recipe_name_txt = (TextView)findViewById(R.id.api_recipe_name);
        api_recipe_ingredient_txt = (TextView)findViewById(R.id.api_recipe_ingredient);
        api_recipe_recipe_txt = (TextView)findViewById(R.id.api_recipe_recipe);

        recipe_idx = getIntent().getStringExtra("recipe_idx");
        api_recipe_name = getIntent().getStringExtra("api_recipe_name");
        api_recipe_imgurl = getIntent().getStringExtra("api_recipe_imgurl");
        api_recipe_ingredient = getIntent().getStringExtra("api_recipe_ingredient");
        api_recipe_recipe = getIntent().getStringExtra("api_recipe_recipe");

        Thread imgload = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final ImageView api_recipe_imgurl_img = (ImageView)findViewById(R.id.api_recipe_img);
                    URL url = new URL(api_recipe_imgurl);
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            api_recipe_imgurl_img.setImageBitmap(bm);
                        }
                    });
                    api_recipe_imgurl_img.setImageBitmap(bm);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        imgload.start();
        api_recipe_name_txt.setText(api_recipe_name);
        api_recipe_ingredient_txt.setText(api_recipe_ingredient);
        api_recipe_recipe_txt.setText(api_recipe_recipe);

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

