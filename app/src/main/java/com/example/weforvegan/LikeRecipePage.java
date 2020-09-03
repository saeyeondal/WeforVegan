package com.example.weforvegan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;


public class LikeRecipePage extends Activity {
    Handler handler = new Handler();

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.likerecipe_page);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    final ImageView iv1 = (ImageView)findViewById(R.id.imageView1);
                    final ImageView iv2 = (ImageView)findViewById(R.id.imageView2);
                    final ImageView iv3 = (ImageView)findViewById(R.id.imageView3);
                    URL url = new URL("https://recipe1.ezmember.co.kr/cache/recipe/2019/03/05/d2e237f1f404e0cfefa723400c63937e1.jpg");
                    InputStream is = url.openStream();
                    final Bitmap bm = BitmapFactory.decodeStream(is);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            iv1.setImageBitmap(bm);
                            iv2.setImageBitmap(bm);
                            iv3.setImageBitmap(bm);
                        }
                    });
                    iv1.setImageBitmap(bm); //비트맵 객체로 보여주기
                    iv2.setImageBitmap(bm);
                    iv3.setImageBitmap(bm);
                } catch(Exception e){
                }
            }
        });
        t.start();
    }
}