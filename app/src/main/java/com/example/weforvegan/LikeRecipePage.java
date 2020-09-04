package com.example.weforvegan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.InputStream;
import java.net.URL;


public class LikeRecipePage extends Fragment {
    Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.likerecipe_page, container, false);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                // TODO Auto-generated method stub
                try{
                    final ImageView iv1 = (ImageView)rootView.findViewById(R.id.imageView1);
                    final ImageView iv2 = (ImageView)rootView.findViewById(R.id.imageView2);
                    final ImageView iv3 = (ImageView)rootView.findViewById(R.id.imageView3);
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
        return rootView;
    }
}