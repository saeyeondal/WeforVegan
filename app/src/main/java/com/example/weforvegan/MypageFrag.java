package com.example.weforvegan;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.InputStream;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MypageFrag extends Fragment {
    private static final int REQUEST_CODE = 0;
    private ImageView imageView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_page, container, false);


        final Button vegan1_1 = (Button)rootView.findViewById(R.id.vegan1_1);
        final Button vegan1_2 = (Button)rootView.findViewById(R.id.vegan1_2);
        final Button vegan2_1 = (Button)rootView.findViewById(R.id.vegan2_1);
        final Button vegan2_2 = (Button)rootView.findViewById(R.id.vegan2_2);
        final Button vegan3_1 = (Button)rootView.findViewById(R.id.vegan3_1);
        final Button vegan3_2 = (Button)rootView.findViewById(R.id.vegan3_2);
        final Button vegan4_1 = (Button)rootView.findViewById(R.id.vegan4_1);
        final Button vegan4_2 = (Button)rootView.findViewById(R.id.vegan4_2);
        final TextView saveButton = (TextView) rootView.findViewById(R.id.saveButton);

/* 갤러리에서 이미지 가져오는 부분 삭제함
        imageView.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
*/
        if(RegisterPage.vegetarian_state == 0) {
            vegan1_1.setVisibility(View.GONE);
            vegan1_2.setVisibility(View.VISIBLE);
        }
        else if(RegisterPage.vegetarian_state == 1){
            vegan2_1.setVisibility(View.GONE);
            vegan2_2.setVisibility(View.VISIBLE);
        }
        else if(RegisterPage.vegetarian_state == 2) {
            vegan3_1.setVisibility(View.GONE);
            vegan3_2.setVisibility(View.VISIBLE);
        }
        else if(RegisterPage.vegetarian_state == 3) {
            vegan4_1.setVisibility(View.GONE);
            vegan4_2.setVisibility(View.VISIBLE);
        }

        vegan1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegan1_1.setVisibility(View.GONE);
                vegan1_2.setVisibility(View.VISIBLE);
                vegan2_1.setVisibility(View.VISIBLE);
                vegan2_2.setVisibility(View.GONE);
                vegan3_1.setVisibility(View.VISIBLE);
                vegan3_2.setVisibility(View.GONE);
                vegan4_1.setVisibility(View.VISIBLE);
                vegan4_2.setVisibility(View.GONE);
            }
        });
        vegan2_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegan2_1.setVisibility(View.GONE);
                vegan2_2.setVisibility(View.VISIBLE);
                vegan1_1.setVisibility(View.VISIBLE);
                vegan1_2.setVisibility(View.GONE);
                vegan3_1.setVisibility(View.VISIBLE);
                vegan3_2.setVisibility(View.GONE);
                vegan4_1.setVisibility(View.VISIBLE);
                vegan4_2.setVisibility(View.GONE);
            }
        });
        vegan3_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegan3_1.setVisibility(View.GONE);
                vegan3_2.setVisibility(View.VISIBLE);
                vegan2_1.setVisibility(View.VISIBLE);
                vegan2_2.setVisibility(View.GONE);
                vegan1_1.setVisibility(View.VISIBLE);
                vegan1_2.setVisibility(View.GONE);
                vegan4_1.setVisibility(View.VISIBLE);
                vegan4_2.setVisibility(View.GONE);
            }
        });
        vegan4_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegan4_1.setVisibility(View.GONE);
                vegan4_2.setVisibility(View.VISIBLE);
                vegan2_1.setVisibility(View.VISIBLE);
                vegan2_2.setVisibility(View.GONE);
                vegan3_1.setVisibility(View.VISIBLE);
                vegan3_2.setVisibility(View.GONE);
                vegan1_1.setVisibility(View.VISIBLE);
                vegan1_2.setVisibility(View.GONE);
            }
        });


        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Toast myToast = Toast.makeText(getContext(),"정보가 수정되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
        return rootView;
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == REQUEST_CODE)
        {
            if(resultCode == RESULT_OK)
            {
                try{
                    InputStream in = getActivity().getContentResolver().openInputStream(data.getData());

                    Bitmap img = BitmapFactory.decodeStream(in);
                    in.close();

                    imageView.setImageBitmap(img);
                }catch(Exception e)
                {

                }
            }
            else if(resultCode == RESULT_CANCELED)
            {
                Toast.makeText(getContext(), "사진 선택 취소", Toast.LENGTH_LONG).show();
            }
        }
    }

}
