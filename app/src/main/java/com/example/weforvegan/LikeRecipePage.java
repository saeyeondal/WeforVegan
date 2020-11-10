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
    static int api_recipe_count;
    static int sns_recipe_count;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.likerecipe_page, container, false);

        int[] img_ids = new int[6];
        img_ids[0] = R.id.imageView1;
        img_ids[1] = R.id.imageView2;
        img_ids[2] = R.id.imageView3;
        img_ids[3] = R.id.imageView4;
        img_ids[4] = R.id.imageView5;
        img_ids[5] = R.id.imageView6;

        int[] heart_ids = new int[6];
        heart_ids[0] = R.id.heart1;
        heart_ids[1] = R.id.heart2;
        heart_ids[2] = R.id.heart3;
        heart_ids[3] = R.id.heart4;
        heart_ids[4] = R.id.heart5;
        heart_ids[5] = R.id.heart6;

        int[] source_ids = new int[6];
        source_ids[0] = R.id.textView1;
        source_ids[1] = R.id.textView2;
        source_ids[2] = R.id.textView3;
        source_ids[3] = R.id.textView4;
        source_ids[4] = R.id.textView5;
        source_ids[5] = R.id.textView6;

        int[] name_ids = new int[6];
        name_ids[0] = R.id.recipeName1;
        name_ids[1] = R.id.recipeName2;
        name_ids[2] = R.id.recipeName3;
        name_ids[3] = R.id.recipeName4;
        name_ids[4] = R.id.recipeName5;
        name_ids[5] = R.id.recipeName6;


        GetRequest httpTask = new GetRequest(getActivity().getApplicationContext());
        try {
            String response = httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/heart").get();
            JsonParser json_result= new JsonParser();

            ApiRecipe[] apiRecipeLike = json_result.get_api_recipe(response);
            for(int i=0; i< apiRecipeLike.length; i++){
                    if((apiRecipeLike.length+i) == 7) break;
                    ((ImageView)rootView.findViewById(img_ids[i])).setImageResource(R.drawable.api);
                    if((apiRecipeLike[i].getApi_source()!= null) && (apiRecipeLike[i].getApi_recipe_name() != null)){
                        ((TextView)rootView.findViewById(source_ids[i])).setText("api");
                        ((TextView)rootView.findViewById(name_ids[i])).setText(apiRecipeLike[i].getApi_recipe_name());
                        System.out.println("source:"+ apiRecipeLike[i].getApi_recipe_name());
                    }
            }

            SNSRecipe[] snsRecipeLike = json_result.get_sns_recipe(response);
            for(int i=0; i< snsRecipeLike.length; i++){
                System.out.println(snsRecipeLike[i].getSource() + snsRecipeLike[i].getSnsTitle());
                if((apiRecipeLike.length + i + 1) >= 7) continue;
                System.out.println("Api 길이:"+ apiRecipeLike.length + "SNS 길이" +  snsRecipeLike.length);
                if((snsRecipeLike[i].getSource() != null) && (snsRecipeLike[i].getSource().equals("twitter"))){
                    ((ImageView)rootView.findViewById(img_ids[apiRecipeLike.length+i])).setImageResource(R.drawable.twitter);
                }
                else if((snsRecipeLike[i].getSource() != null)&& (snsRecipeLike[i].getSource().equals("instagram")))
                    ((ImageView)rootView.findViewById(img_ids[apiRecipeLike.length+i])).setImageResource(R.drawable.instagram);
                else if((snsRecipeLike[i].getSource() != null) && (snsRecipeLike[i].getSource().equals("10000recipe")))
                    ((ImageView)rootView.findViewById(img_ids[apiRecipeLike.length+i])).setImageResource(R.drawable.manrecipe);
                else
                    System.out.println("이미지 없음");
                if((snsRecipeLike[i].getSource()!= null) && (snsRecipeLike[i].getSnsTitle() != null)){
                    ((TextView)rootView.findViewById(source_ids[apiRecipeLike.length+i])).setText(snsRecipeLike[i].getSource());
                    ((TextView)rootView.findViewById(name_ids[apiRecipeLike.length+i])).setText(snsRecipeLike[i].getSnsTitle());
                    System.out.println("source:"+ snsRecipeLike[i].getSource());
                    System.out.println("name:" + snsRecipeLike[i].getSnsTitle());
                }
            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}