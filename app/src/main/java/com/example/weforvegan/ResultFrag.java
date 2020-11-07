package com.example.weforvegan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutionException;

public class ResultFrag extends Fragment {
    public static String barcodeNumber;
    TextView productName;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.result_frag, container, false);
        productName = (TextView)rootView.findViewById(R.id.product_name);
        //productName.setText(barcodeNumber);

        PostRequest request = new PostRequest(getActivity().getApplicationContext());
        try {
            String response = request.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/text1.php", "BAR_NUM", "8801024949960").get();
            System.out.println("바코드: \n" + response);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return rootView;
    }
}
