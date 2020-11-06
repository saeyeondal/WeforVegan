package com.example.weforvegan;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.concurrent.ExecutionException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class MypageFrag extends Fragment {
    private static final int REQUEST_CODE = 0;
    private ImageView imageView;
    String usr_id, usr_pwd, usr_phone, usr_vegantype;
    String usr_vegantype_txt;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_page, container, false);

        Log.d("LOG", "마이페이지");

        final EditText usr_id_txt = (EditText)rootView.findViewById(R.id.edit_id);
        final EditText usr_pwd_txt = (EditText)rootView.findViewById(R.id.edit_pwd);
        final EditText usr_pwd_check_txt = (EditText)rootView.findViewById(R.id.edit_pwd_check);
        final EditText usr_phone_txt = (EditText)rootView.findViewById(R.id.edit_phone);
        final Button vegan1_1 = (Button)rootView.findViewById(R.id.vegan1_1);
        final Button vegan1_2 = (Button)rootView.findViewById(R.id.vegan1_2);
        final Button vegan2_1 = (Button)rootView.findViewById(R.id.vegan2_1);
        final Button vegan2_2 = (Button)rootView.findViewById(R.id.vegan2_2);
        final Button vegan3_1 = (Button)rootView.findViewById(R.id.vegan3_1);
        final Button vegan3_2 = (Button)rootView.findViewById(R.id.vegan3_2);
        final Button vegan4_1 = (Button)rootView.findViewById(R.id.vegan4_1);
        final Button vegan4_2 = (Button)rootView.findViewById(R.id.vegan4_2);
        final TextView saveButton = (TextView) rootView.findViewById(R.id.saveButton);

        GetRequest httpTask = new GetRequest(getActivity().getApplicationContext());
        try {
            Log.d("LOG", "보냄");
            String response = httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/mypage").get();
            Log.d("LOG", "RESPONSE: " + response);
            JsonParser json_result= new JsonParser();
            String[] user_inform = new String[7];
            user_inform = json_result.inform_parse(response);
            /*usr_id = user_inform[0];
            usr_pwd = user_inform[1];
            usr_phone = user_inform[2];
            usr_vegantype = user_inform[5];*/
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


       // usr_id_txt.setText(usr_id);
       // usr_pwd_txt.setText(usr_id);
       // usr_phone_txt.setText(usr_phone);

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
        /*if(usr_vegantype.equals("비건")) {
            vegan1_1.setVisibility(View.GONE);
            vegan1_2.setVisibility(View.VISIBLE);
        }
        else if(usr_vegantype.equals("락토")){
            vegan2_1.setVisibility(View.GONE);
            vegan2_2.setVisibility(View.VISIBLE);
        }
        else if(usr_vegantype.equals("오보")) {
            vegan3_1.setVisibility(View.GONE);
            vegan3_2.setVisibility(View.VISIBLE);
        }
        else if(usr_vegantype.equals("락토오보")) {
            vegan4_1.setVisibility(View.GONE);
            vegan4_2.setVisibility(View.VISIBLE);
        }*/

        vegan1_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_vegantype_txt = "비건";
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
                usr_vegantype_txt = "락토";
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
                usr_vegantype_txt = "오보";
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
                usr_vegantype_txt = "락토오보";
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
                /*if(usr_pwd_txt.getText().toString().equals(usr_pwd_check_txt.getText().toString())){
                    PostRequest request = new PostRequest(getActivity().getApplicationContext());
                    try {
                        String response = request.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/mypage", "id", usr_id_txt.getText().toString(),
                                "pwd", usr_pwd_txt.getText().toString(), "name", usr_phone_txt.getText().toString(), "vegantype", usr_vegantype_txt).get();
                        System.out.println("회원 정보 수정: \n" + response);
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Toast myToast = Toast.makeText(getContext(),"정보가 수정되었습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    Toast myToast = Toast.makeText(getContext(),"비밀번호 두 개가 일치하지 않습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }*/
            }
        });
        return rootView;
    }
    /*
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
    }*/
}
