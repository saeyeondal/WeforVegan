package com.example.weforvegan;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
        final RecyclerView recyclerView = rootView.findViewById(R.id.menu_recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

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
                recyclerView.setAdapter(adapter);
            }
        });

        adapter.setOnItemClickListener(new OnMenuItemClickListener() {
            @Override
            public void onItemClick(MenuAdapter.ViewHolder holder, View view, int position) {
                Menu item = adapter.getItem(position);
                RecipeFrag.selectedMenu = item.getMenu();
                //가게 이름: item.getName(), 해시태그 내용: item.getHashtag()
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onFragmentChanged(0);
            }
        });



        return rootView;
    }
}
