package com.example.weforvegan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RecommendFrag extends Fragment {
    Button show_receipe1;
    Button show_receipe2;
    Button show_receipe3;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.recommend_frag, container, false);

        show_receipe1 = (Button)rootView.findViewById(R.id.show_recipe1);
        show_receipe2 = (Button)rootView.findViewById(R.id.show_recipe2);
        show_receipe3 = (Button)rootView.findViewById(R.id.show_recipe3);

        return rootView;
    }
}
