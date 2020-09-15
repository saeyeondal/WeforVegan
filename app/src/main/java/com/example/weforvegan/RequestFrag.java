package com.example.weforvegan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RequestFrag extends Fragment {
    Button addRequestBtn;
    static boolean add_request_state=false;
    static final RequestAdapter adapter = new RequestAdapter();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.request_frag, container, false);
        addRequestBtn = (Button)rootView.findViewById(R.id.add_request_btn);

        RequestAdapter.items.clear();
        if(add_request_state==true){
            Toast.makeText(getActivity().getApplicationContext(), "접수가 완료되었습니다.", Toast.LENGTH_SHORT).show();
        }

        addRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onFragmentChanged(2);
            }
        });

        final RecyclerView recyclerView = rootView.findViewById(R.id.request_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), new LinearLayoutManager(getActivity()).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        adapter.addItem(new Request("레시피 업데이트 주기는 어떻게 되나요?", "답변대기", "ㅁㅇㄹㅇㄹㅇㄹㅇㄹㅇㄹㅇㄹㅇ\nㅇㄹㅇㄹㅇ\nㅇㄹㅇㄹㅇㄹㅇㄹㅇㄹㅇㄹㅇ"));
        adapter.addItem(new Request("영양소 기준은 어떻게 되나요?", "답변대기", "ㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹㄹ"));

        recyclerView.setAdapter(adapter);
        return rootView;
    }
}
