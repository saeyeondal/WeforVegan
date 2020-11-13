package com.example.weforvegan;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

public class SearchFrag extends Fragment {
    static final ApiAdapter apiAdapter = new ApiAdapter();
    static final SnsAdapter snsAdapter = new SnsAdapter();
    static TextView searchText;
    Button searchBtn;
    ApiRecipe[] apiRecipes;
    SNSRecipe[] snsRecipes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ApiAdapter.items.clear();
        SnsAdapter.items.clear();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.search_frag, container, false);
        searchText = (TextView)rootView.findViewById(R.id.search_text);
        searchBtn = (Button)rootView.findViewById(R.id.search_btn);
        TabHost th = (TabHost)rootView.findViewById(R.id.th);

        th.setup();
        TabHost.TabSpec ts1 = th.newTabSpec("Tab1");
        ts1.setIndicator("일반레시피");
        ts1.setContent(R.id.general_recipe);
        th.addTab(ts1);
        TabHost.TabSpec ts2 = th.newTabSpec("Tab2");
        ts2.setIndicator("SNS 레시피");
        ts2.setContent(R.id.sns_recipe);
        th.addTab(ts2);
        th.setCurrentTab(0);

        System.out.println("apiAdapter:   " + apiAdapter);
        System.out.println("snsAdapter:   " + snsAdapter);

        final RecyclerView gen_recyclerView = rootView.findViewById(R.id.api_recyclerView);
        final RecyclerView sns_recyclerView = rootView.findViewById(R.id.sns_recyclerView);

        gen_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(gen_recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        gen_recyclerView.addItemDecoration(dividerItemDecoration);

        sns_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(sns_recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        sns_recyclerView.addItemDecoration(dividerItemDecoration2);

        GetRequest request = new GetRequest(getActivity().getApplicationContext());
        try {
            String response = request.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/rank").get();
            JsonParser jsonParser = new JsonParser();
            apiRecipes = jsonParser.get_api_recipe(response);
            snsRecipes = jsonParser.get_sns_recipe(response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(int i=0; i<apiRecipes.length; i++){
            apiAdapter.addItem(new Menu(apiRecipes[i].getApi_recipe_name(), "공공데이터 레시피"));
        }

        for(int i=0; i<snsRecipes.length; i++){
            snsAdapter.addItem(new Menu(snsRecipes[i].getSnsTitle(), snsRecipes[i].getSource()));
        }

        gen_recyclerView.setAdapter(apiAdapter);
        sns_recyclerView.setAdapter(snsAdapter);

        searchText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if((keyEvent.getAction() == KeyEvent.ACTION_DOWN) && (i == KeyEvent.KEYCODE_ENTER)){
                    searchBtn.performClick();
                    return true;
                }
                return false;
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApiAdapter.items.clear();
                SnsAdapter.items.clear();
                if(searchText.getText().toString().equals("두부")){
                    JsonParser jsonParser = new JsonParser();
                    String api_data = null;
                    InputStream inputStream = getResources().openRawResource(R.raw.api_json);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int i;
                    try{
                        i = inputStream.read();
                        while(i != -1){
                            byteArrayOutputStream.write(i);
                            i = inputStream.read();
                        }
                        api_data = new String(byteArrayOutputStream.toByteArray(), "UTF-8");
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String sns_data = null;
                    InputStream inputStream2 = getResources().openRawResource(R.raw.sns_json);
                    ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();
                    int i2;
                    try{
                        i2 = inputStream2.read();
                        while(i2 != -1){
                            byteArrayOutputStream2.write(i2);
                            i2 = inputStream2.read();
                        }
                        sns_data = new String(byteArrayOutputStream2.toByteArray(), "UTF-8");
                        inputStream2.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    apiRecipes = jsonParser.get_api_recipe(api_data);
                    snsRecipes = jsonParser.get_sns_recipe(sns_data);

                    ApiRecipe[] apiRecipes_txt = null;
                    SNSRecipe[] snsRecipes_txt = null;

                    for(int j=0; j<apiRecipes.length; j++){
                        apiAdapter.addItem(new Menu(apiRecipes_txt[j].getApi_recipe_name(), "공공데이터 레시피"));
                    }

                    for(int j=0; j<snsRecipes.length; j++) {
                        snsAdapter.addItem(new Menu(snsRecipes_txt[j].getSnsTitle(), snsRecipes[j].getSource()));
                    }
                }
                gen_recyclerView.setAdapter(apiAdapter);
                sns_recyclerView.setAdapter(snsAdapter);
            }
        });

        apiAdapter.setOnItemClickListener(new OnMenuItemClickListener_api() {
            @Override
            public void onItemClick(ApiAdapter.ViewHolder holder, View view, int position) {
                //가게 이름: item.getName(), 해시태그 내용: item.getHashtag()
                Intent intent = new Intent(getActivity(), ApiRecipeFrag.class); //파라메터는 현재 액티비티, 전환될 액티비티
                intent.putExtra("recipe_idx", Integer.toString(apiRecipes[position].getApi_idx()));
                intent.putExtra("api_recipe_name", apiRecipes[position].getApi_recipe_name());
                intent.putExtra("api_recipe_imgurl", apiRecipes[position].getApi_imgurl());
                intent.putExtra("api_recipe_ingredient", apiRecipes[position].getApi_ingredient());
                intent.putExtra("api_recipe_recipe", apiRecipes[position].getApi_recipe());
                startActivity(intent); //엑티비티 요청
            }
        });

        snsAdapter.setOnItemClickListener(new OnMenuItemClickListener_sns() {
            @Override
            public void onItemClick(SnsAdapter.ViewHolder holder, View view, int position) {
                //가게 이름: item.getName(), 해시태그 내용: item.getHashtag()
                Intent intent = new Intent(getActivity(), SnsRecipeFrag.class); //파라메터는 현재 액티비티, 전환될 액티비티
                intent.putExtra("sns_recipe_url", snsRecipes[position].getSnsUrl());
                intent.putExtra("recipe_idx", Integer.toString(snsRecipes[position].getSnsIdx()));
                intent.putExtra("sns_recipe_src", snsRecipes[position].getSource());
                startActivity(intent); //엑티비티 요청
            }
        });

        return rootView;
    }
}
