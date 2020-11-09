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

import com.google.android.material.tabs.TabLayout;

import java.util.concurrent.ExecutionException;

public class SearchFrag extends Fragment {
    static final MenuAdapter adapter = new MenuAdapter();
    static TextView searchText;
    Button searchBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MenuAdapter.items.clear();
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.search_frag, container, false);
        searchText = (TextView)rootView.findViewById(R.id.search_text);
        searchBtn = (Button)rootView.findViewById(R.id.search_btn);
        TabHost th = (TabHost)rootView.findViewById(R.id.th);

        PostRequest request = new PostRequest(getActivity().getApplicationContext());
        try {
            String response = request.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/Barcode.php", "BAR_NUM", "8801024949960").get();
            System.out.println("바코드: \n" + response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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

        final RecyclerView gen_recyclerView = rootView.findViewById(R.id.general_recyclerView);
        final RecyclerView sns_recyclerView = rootView.findViewById(R.id.sns_recyclerView);

        gen_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(gen_recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        gen_recyclerView.addItemDecoration(dividerItemDecoration);

        sns_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        DividerItemDecoration dividerItemDecoration2 = new DividerItemDecoration(sns_recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        sns_recyclerView.addItemDecoration(dividerItemDecoration2);

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
                MenuAdapter.items.clear();
                if(searchText.getText().toString().equals("잡채")){
                    adapter.addItem(new Menu("채식을 위한 고기뺀 '잡채';당면이 불지 않는 비법", "만개의 레시피"));
                    adapter.addItem(new Menu("2그릇 순삭 가능!! 간단하게 매콤 잡채 만드는 법, 별미 잡채, 잡채밥, 채식", "만개의 레시피"));
                    adapter.addItem(new Menu("[비건채식] 잡채", "만개의 레시피"));
                }
                gen_recyclerView.setAdapter(adapter);
                sns_recyclerView.setAdapter(adapter);
            }
        });

        adapter.setOnItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuAdapter.ViewHolder holder, View view, int position) {
                Menu item = adapter.getItem(position);
                RecipeFrag.selectedMenu = item.getMenu();
                //가게 이름: item.getName(), 해시태그 내용: item.getHashtag()
                Intent intent = new Intent(getActivity(), RecipeFrag.class); //파라메터는 현재 액티비티, 전환될 액티비티
                startActivity(intent); //엑티비티 요청
            }
        });



        return rootView;
    }
}
