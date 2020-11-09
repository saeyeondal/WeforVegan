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

        //recipe icon image
        final ImageView iv1 = (ImageView)rootView.findViewById(R.id.imageView1);
        final ImageView iv2 = (ImageView)rootView.findViewById(R.id.imageView2);
        final ImageView iv3 = (ImageView)rootView.findViewById(R.id.imageView3);
        final ImageView iv4 = (ImageView)rootView.findViewById(R.id.imageView4);
        final ImageView iv5 = (ImageView)rootView.findViewById(R.id.imageView5);
        final ImageView iv6 = (ImageView)rootView.findViewById(R.id.imageView6);

        int[] img_ids = new int[6];
        img_ids[0] = R.id.imageView1;
        img_ids[1] = R.id.imageView2;
        img_ids[2] = R.id.imageView3;
        img_ids[3] = R.id.imageView4;
        img_ids[4] = R.id.imageView5;
        img_ids[5] = R.id.imageView6;

        //heart button
       /*
        final Button bt1 = (Button)rootView.findViewById(R.id.heart1);
        final Button bt2 = (Button)rootView.findViewById(R.id.heart2);
        final Button bt3 = (Button)rootView.findViewById(R.id.heart3);
        final Button bt4 = (Button)rootView.findViewById(R.id.heart4);
        final Button bt5 = (Button)rootView.findViewById(R.id.heart5);
        final Button bt6 = (Button)rootView.findViewById(R.id.heart6);
*/
        int[] heart_ids = new int[6];
        heart_ids[0] = R.id.heart1;
        heart_ids[1] = R.id.heart2;
        heart_ids[2] = R.id.heart3;
        heart_ids[3] = R.id.heart4;
        heart_ids[4] = R.id.heart5;
        heart_ids[5] = R.id.heart6;

        //recipe source name
 /*     final TextView tv1 = (TextView)rootView.findViewById(R.id.textView1);
        final TextView tv2 = (TextView)rootView.findViewById(R.id.textView2);
        final TextView tv3 = (TextView)rootView.findViewById(R.id.textView3);
        final TextView tv4 = (TextView)rootView.findViewById(R.id.textView4);
        final TextView tv5 = (TextView)rootView.findViewById(R.id.textView5);
        final TextView tv6 = (TextView)rootView.findViewById(R.id.textView6);
*/
        int[] source_ids = new int[6];
        source_ids[0] = R.id.textView1;
        source_ids[1] = R.id.textView2;
        source_ids[2] = R.id.textView3;
        source_ids[3] = R.id.textView4;
        source_ids[4] = R.id.textView5;
        source_ids[5] = R.id.textView6;

        //recipe name
        final TextView r1 = (TextView)rootView.findViewById(R.id.recipeName1);
        final TextView r2 = (TextView)rootView.findViewById(R.id.recipeName2);
        final TextView r3 = (TextView)rootView.findViewById(R.id.recipeName3);
        final TextView r4 = (TextView)rootView.findViewById(R.id.recipeName4);
        final TextView r5 = (TextView)rootView.findViewById(R.id.recipeName5);
        final TextView r6 = (TextView)rootView.findViewById(R.id.recipeName6);


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
            System.out.println(response);

            String[] api_recipe_inform = new String[api_recipe_count*3];
            String[] sns_recipe_inform = new String[sns_recipe_count*3];

            api_recipe_inform = json_result.like_receipe_parse1(response);
            sns_recipe_inform = json_result.like_receipe_parse2(response);

            String [] api_rp_idx = new String[api_recipe_count];
            String [] api_rp_name = new String[api_recipe_count];
            String [] api_rp_source = new String[api_recipe_count];

            String [] sns_rp_idx = new String[sns_recipe_count];
            String [] sns_rp_name = new  String[sns_recipe_count];
            String [] sns_rp_source = new String[sns_recipe_count];

            System.out.println(api_recipe_count);

            for(int i=0; i< (api_recipe_count*3); i=i+3){
                for(int j=0; j<api_recipe_count; j++){
                    api_rp_idx[j] = api_recipe_inform[i];
                    api_rp_name[j] = api_recipe_inform[i+1];
                    api_rp_source[j] = api_recipe_inform[i+2];
                    if((api_recipe_count+i) == 7) break;
                    ((ImageView)rootView.findViewById(img_ids[j])).setImageResource(R.drawable.api);
                    if((api_rp_source[j]!= null) && (api_rp_name[j] != null)){
                        ((TextView)rootView.findViewById(source_ids[j])).setText(api_rp_source[j]);
                        ((TextView)rootView.findViewById(name_ids[j])).setText(api_rp_name[j]);
                    }
                }
            }


            for(int i=0; i< (sns_recipe_count*3) ; i=i+3){
                for(int j=0; j<sns_recipe_count; j++){
                    sns_rp_idx[j] = sns_recipe_inform[i];
                    sns_rp_name[j] = sns_recipe_inform[i+1];
                    sns_rp_source[j] = sns_recipe_inform[i+2];

                    if((api_recipe_count+j)== 7 || (sns_recipe_count+j) == 7) break;

                    if((sns_rp_source[j] != null) && (sns_rp_source[j].equals("twitter")))
                        ((ImageView)rootView.findViewById(img_ids[api_recipe_count+j])).setImageResource(R.drawable.twitter);
                    else if((sns_rp_source[j] != null)&& (sns_rp_source[j].equals("instagram")))
                        ((ImageView)rootView.findViewById(img_ids[api_recipe_count+j])).setImageResource(R.drawable.instagram);
                    else if((sns_rp_source[j] != null) && (sns_rp_source[j].equals("10000recipe")))
                        ((ImageView)rootView.findViewById(img_ids[api_recipe_count+j])).setImageResource(R.drawable.manrecipe);
                    else
                        System.out.println("이미지 없음");

                    if((sns_rp_source[j]!= null) && (sns_rp_name[j]!=null)){
                        ((TextView)rootView.findViewById(source_ids[api_recipe_count+j])).setText(api_rp_source[j]);
                        ((TextView)rootView.findViewById(name_ids[api_recipe_count+j])).setText(api_rp_name[j]);
                    }
                }
            }

            System.out.println("from api:"+ api_rp_idx+ api_rp_name+ api_rp_source);
            System.out.println("from sns:"+ sns_rp_idx+ sns_rp_name+ sns_rp_source);
            System.out.println(api_recipe_count);
            System.out.println(sns_recipe_count);

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return rootView;
    }
}