package com.example.weforvegan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class RegisterPage extends Activity {
    TextView name_textview, id_textview, password_textview, password_check, email_textView;
    Button id_check;
    Button registerBtn;
    Button vegan1, vegan2, lacto1, lacto2, ovo1, ovo2, lacto_ovo1, lacto_ovo2;
    RadioButton female;
    RadioButton male;

    static Integer vegetarian_state = 0;

    public void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.register_page);

        id_textview = (TextView)findViewById(R.id.register_page_id);
        password_textview= (TextView)findViewById(R.id.register_page_password);
        password_check = (TextView)findViewById(R.id.register_page_password_check);
        email_textView = (TextView)findViewById(R.id.register_email);
        id_check = (Button)findViewById(R.id.id_check);
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

                vegetarian_state = 3;
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getApplicationContext(),"회원가입이 완료되었습니다.", Toast.LENGTH_SHORT);
                myToast.show();
                Intent intent = new Intent(RegisterPage.this, MainActivity.class); //파라메터는 현재 액티비티, 전환될 액티비티
                startActivity(intent); //엑티비티 요청
            }
        });
    }
}
