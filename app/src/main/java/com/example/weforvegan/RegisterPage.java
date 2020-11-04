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

public class RegisterPage extends Activity {
    TextView id_textview, password_textview, password_check, email_textView;
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
                switch(v.getId()){
                    case R.id.register:
                        if(!validate())
                            Toast.makeText(getBaseContext(), "Enter some data!", Toast.LENGTH_LONG).show();
                        else {
                            // call AsynTask to perform network operation on separate thread
                            HttpAsyncTask httpTask = new HttpAsyncTask(RegisterPage.this);
                            /*
                            httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/join", id_textview.getText().toString(),
                            password_textview.getText().toString(), email_textView.getText().toString(), "여자", "20대", "락토오보");
                            */
                            httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/join", "id", "hungry", "pwd", "pig", "name", "01000101", "sex", "여자", "birth",
                                    "20대", "vegantype", "락토오보");
                            Toast.makeText(getBaseContext(), "전송 완료", Toast.LENGTH_LONG).show();
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

    public static String POST(String... urls) throws UnsupportedEncodingException {
        //InputStream is = null;
        //InputStreamReader responseBodyReader = new InputStreamReader(is, "UTF-8");
        String result = "";
        try {
            URL urlCon = new URL(urls[0]);
            System.out.println(urls[0]);
            HttpURLConnection httpCon = (HttpURLConnection)urlCon.openConnection(); //Create Connection

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            for(int i=1; i<urls.length; i=i+2)
                jsonObject.accumulate(urls[i], urls[i+1]);

            // convert JSONObject to JSON to String
            json = jsonObject.toString();
            System.out.println(json);

            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // Set some headers to inform server about the type of the content
            httpCon.setRequestProperty("Accept", "application/json");
            httpCon.setRequestProperty("Content-type", "application/json");

            // OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            httpCon.setDoOutput(true);
            // InputStream으로 서버로 부터 응답을 받겠다는 옵션.
            httpCon.setDoInput(true);

            OutputStream os = httpCon.getOutputStream();
            os.write(json.getBytes("UTF-8"));
            os.flush();
            // receive response as inputStream
            try {
                InputStreamReader tmp = new InputStreamReader(httpCon.getInputStream(), "UTF-8");
                BufferedReader reader = new BufferedReader(tmp);
                StringBuilder builder = new StringBuilder();
                String str;
                while ((str = reader.readLine()) != null) {       // 서버에서 라인단위로 보내줄 것이므로 라인단위로 읽는다
                    builder.append(str + "\n");                     // View에 표시하기 위해 라인 구분자 추가
                }
                String myResult = builder.toString();
                System.out.println(myResult);
                JSONObject jsonObject_result = new JSONObject(myResult);
                String json_result = jsonObject_result.getString("message");
                System.out.println(json_result);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                httpCon.disconnect();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }

    private class HttpAsyncTask extends AsyncTask<String, Void, String> {

        private   RegisterPage registerPage;

        HttpAsyncTask(RegisterPage registerPage) {
            this.registerPage = registerPage;
        }
        @Override
        protected String doInBackground(String... urls) {

           /* person = new Person();
            person.setId(urls[1]);
            person.setPwd(urls[2]);
            person.setName(urls[3]);
            person.setSex(urls[4]);
            person.setBirth(urls[5]);
            person.setVegantype(urls[6]);*/

            String result = null;
            try {
                //result = POST(urls[0],person);
                result = POST(urls);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result;
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    private boolean validate(){
        if(id_textview.getText().toString().trim().equals(""))
            return false;
        else if(password_textview.getText().toString().trim().equals(""))
            return false;
        else if(email_textView.getText().toString().trim().equals(""))
            return false;
        else
            return true;
    }

}
