package com.example.weforvegan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ResultFrag extends Fragment {
    public static String barcodeNumber;
    TextView productName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.result_frag, container, false);
        productName = (TextView)rootView.findViewById(R.id.product_name);
        //productName.setText(barcodeNumber);

        return rootView;
    }
}
