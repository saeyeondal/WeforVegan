package com.example.weforvegan;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ExecutionException;


public class LikeRecipePage extends Fragment {
    Handler handler = new Handler();
    String api_rp_idx, api_rp_name, api_rp_source;
    String sns_rp_idx, sns_rp_name, sns_rp_source;
    static int api_recipe_count;
    static int sns_recipe_count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.likerecipe_page, container, false);

        //recipe icon image
        final ImageView iv1 = (ImageView)rootView.findViewById(R.id.imageView1);
        final ImageView iv2 = (ImageView)rootView.findViewById(R.id.imageView2);
        final ImageView iv3 = (ImageView)rootView.findViewById(R.id.imageView3);
        final ImageView iv4 = (ImageView)rootView.findViewById(R.id.imageView4);
        final ImageView iv5 = (ImageView)rootView.findViewById(R.id.imageView5);
        final ImageView iv6 = (ImageView)rootView.findViewById(R.id.imageView6);

        //heart button
        final Button bt1 = (Button)rootView.findViewById(R.id.heart1);
        final Button bt2 = (Button)rootView.findViewById(R.id.heart2);
        final Button bt3 = (Button)rootView.findViewById(R.id.heart3);
        final Button bt4 = (Button)rootView.findViewById(R.id.heart4);
        final Button bt5 = (Button)rootView.findViewById(R.id.heart5);
        final Button bt6 = (Button)rootView.findViewById(R.id.heart6);

        //recipe source name
        final TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
        final TextView tv2 = (TextView)rootView.findViewById(R.id.textView2);
        final TextView tv3 = (TextView)rootView.findViewById(R.id.textView3);
        final TextView tv4 = (TextView)rootView.findViewById(R.id.textView4);
        final TextView tv5 = (TextView)rootView.findViewById(R.id.textView5);
        final TextView tv6 = (TextView)rootView.findViewById(R.id.textView6);


        GetRequest httpTask = new GetRequest(getActivity().getApplicationContext());
        try {
            String response = httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart").get();
            JsonParser json_result= new JsonParser();
            System.out.println(response);

            String[] api_recipe_inform = new String[3];
            String[] sns_recipe_inform = new String[3];

            api_recipe_inform = json_result.like_receipe_parse1(response);
            sns_recipe_inform = json_result.like_receipe_parse2(response);

            api_rp_idx = api_recipe_inform[0];
            api_rp_name = api_recipe_inform[1];
            api_rp_source = api_recipe_inform[2];

            sns_rp_idx = sns_recipe_inform[0];
            sns_rp_name = sns_recipe_inform[1];
            sns_rp_source = sns_recipe_inform[2];

            System.out.println("from api:"+ api_rp_idx+ api_rp_name+ api_rp_source);
            System.out.println("from sns:"+ sns_rp_idx+ sns_rp_name+ sns_rp_source);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}