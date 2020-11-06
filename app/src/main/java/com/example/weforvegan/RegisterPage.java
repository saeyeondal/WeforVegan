package com.example.weforvegan;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class RegisterPage extends Activity {
    TextView id_textview, password_textview, password_check, phone_textView;
    Button id_check;
    Button registerBtn;
    Button vegan1, vegan2, lacto1, lacto2, ovo1, ovo2, lacto_ovo1, lacto_ovo2;
    RadioGroup radioGroup;
    RadioButton male, female;

    String vegan_type;
    static Integer vegetarian_state = 0;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.register_page);

        id_textview = (TextView)findViewById(R.id.register_page_id);
        password_textview= (TextView)findViewById(R.id.register_page_password);
        password_check = (TextView)findViewById(R.id.register_page_password_check);
        phone_textView = (TextView)findViewById(R.id.register_phone);
        id_check = (Button)findViewById(R.id.id_check);
        radioGroup = (RadioGroup)findViewById(R.id.register_page_rgroup);
        male = (RadioButton)findViewById(R.id.register_page_male);
        female = (RadioButton)findViewById(R.id.register_page_female);
        registerBtn = (Button)findViewById(R.id.register);

        vegan1 = (Button)findViewById(R.id.vegan1_1);
        vegan2 = (Button)findViewById(R.id.vegan1_2);
        lacto1 = (Button)findViewById(R.id.vegan2_1);
        lacto2 = (Button)findViewById(R.id.vegan2_2);
        ovo1 = (Button)findViewById(R.id.vegan3_1);
        ovo2 = (Button)findViewById(R.id.vegan3_2);
        lacto_ovo1 = (Button)findViewById(R.id.vegan4_1);
        lacto_ovo2 = (Button)findViewById(R.id.vegan4_2);

        final Spinner mSpinner = findViewById(R.id.register_page_spinner_age);
        String[] ages = getResources().getStringArray(R.array.age_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getApplicationContext(), R.layout.support_simple_spinner_dropdown_item, ages);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);


        //선택된 값 가져오기
        mSpinner.getSelectedItem().toString();

        //선택된 값에 대한 변경 이벤트
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // 여기에 추가
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        id_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getApplicationContext(),"사용 가능한 아이디입니다.", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        RadioButton rd = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
        final String rd_s = rd.getText().toString();

        vegan1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vegan1.setVisibility(View.GONE);
                vegan2.setVisibility(View.VISIBLE);
                lacto1.setVisibility(View.VISIBLE);
                ovo1.setVisibility(View.VISIBLE);
                lacto_ovo1.setVisibility(View.VISIBLE);
                lacto2.setVisibility(View.GONE);
                ovo2.setVisibility(View.GONE);
                lacto_ovo2.setVisibility(View.GONE);

                vegan_type = "비건";
                vegetarian_state = 0;
            }
        });

        lacto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lacto1.setVisibility(View.GONE);
                lacto2.setVisibility(View.VISIBLE);
                vegan1.setVisibility(View.VISIBLE);
                ovo1.setVisibility(View.VISIBLE);
                lacto_ovo1.setVisibility(View.VISIBLE);
                vegan2.setVisibility(View.GONE);
                ovo2.setVisibility(View.GONE);
                lacto_ovo2.setVisibility(View.GONE);

                vegan_type = "락토";
                vegetarian_state = 1;
            }
        });

        ovo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ovo1.setVisibility(View.GONE);
                ovo2.setVisibility(View.VISIBLE);
                vegan1.setVisibility(View.VISIBLE);
                lacto1.setVisibility(View.VISIBLE);
                lacto_ovo1.setVisibility(View.VISIBLE);
                vegan2.setVisibility(View.GONE);
                lacto2.setVisibility(View.GONE);
                lacto_ovo2.setVisibility(View.GONE);

                vegan_type = "오보";
                vegetarian_state = 2;
            }
        });

        lacto_ovo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lacto_ovo1.setVisibility(View.GONE);
                lacto_ovo2.setVisibility(View.VISIBLE);
                vegan1.setVisibility(View.VISIBLE);
                ovo1.setVisibility(View.VISIBLE);
                lacto1.setVisibility(View.VISIBLE);
                vegan2.setVisibility(View.GONE);
                ovo2.setVisibility(View.GONE);
                lacto2.setVisibility(View.GONE);

                vegan_type = "락토오보";
                vegetarian_state = 3;
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String response="";
                switch(v.getId()){
                    case R.id.register:

                        if(!validate())
                            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                        else {
                            // call AsynTask to perform network operation on separate thread
                            PostRequest httpTask = new PostRequest(getApplicationContext());
                            /*
                            httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/join", id_textview.getText().toString(),
                            password_textview.getText().toString(), email_textView.getText().toString(), "여자", "20대", "락토오보");
                            */
                            try {
                                response = httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/join", "id", id_textview.getText().toString(),
                                        "pwd", password_textview.getText().toString(), "name",phone_textView.getText().toString(), "sex", rd_s, "birth", mSpinner.getSelectedItem().toString(), "vegantype", vegan_type).get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            Toast.makeText(getBaseContext(), response, Toast.LENGTH_LONG).show();
                        }

                        break;
                }
            }
        });
        /*
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();
                Intent intent = new Intent(RegisterPage.this, MainActivity.class); //파라메터는 현재 액티비티, 전환될 액티비티
                startActivity(intent); //엑티비티 요청
            }
        });
         */
    }

    private boolean validate(){
        if(id_textview.getText().toString().trim().equals(""))
            return false;
        else if(password_textview.getText().toString().trim().equals(""))
            return false;
        else if(phone_textView.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

}
