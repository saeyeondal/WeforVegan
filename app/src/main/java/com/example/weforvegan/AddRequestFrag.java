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

public class AddRequestFrag extends Fragment {
    Button submitBtn;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.add_request_frag, container, false);
        submitBtn = (Button)rootView.findViewById(R.id.submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestFrag.add_request_state = true;
                MainActivity mainActivity = (MainActivity)getActivity();
                mainActivity.onFragmentChanged(3);
            }
        });

        return rootView;
    }
}
