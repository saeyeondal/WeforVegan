package com.example.weforvegan;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.CookieStore;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class LoginPage extends Activity {
    TextView id_textView;
    TextView password_textView;
    Button login_button;
    TextView find_id;
    TextView register;
    static String logState = "login";
    CookieManager cookieManager;

    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.login_page);

        id_textView = (TextView)findViewById(R.id.login_id);
        password_textView = (TextView)findViewById(R.id.login_password);
        login_button = (Button)findViewById(R.id.login_button);
        find_id = (TextView) findViewById(R.id.login_find_id);
        register = (TextView) findViewById(R.id.login_register);
        //final String str = "admin";

        CookieSyncManager.createInstance(this);
        cookieManager = CookieManager.getInstance();
        CookieSyncManager.getInstance().startSync();


        if(logState.equals("logout"))
            Toast.makeText(getApplicationContext(), "로그아웃 되었습니다.", Toast.LENGTH_LONG).show();

        login_button.setOnClickListener(new View.OnClickListener() {  //로그인 버튼 -> 메인 화면으로 이동
            @Override
            public void onClick(View v) {
                String uid = id_textView.getText().toString();
                String passwd = password_textView.getText().toString();
                String result_msg = "";
                LoginRequest httpTask = new LoginRequest();
                String response = "";
                try {
                    response = httpTask.execute("http://ec2-18-222-92-67.us-east-2.compute.amazonaws.com:3000/login", "id", uid, "pwd", passwd).get();
                    JsonParser json_result= new JsonParser();
                    result_msg = json_result.login_parse(response);
                    Toast myToast = Toast.makeText(getApplicationContext(),result_msg, Toast.LENGTH_SHORT);
                    myToast.show();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(result_msg.equals("로그인 성공")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"로그인 성공", Toast.LENGTH_SHORT);
                    myToast.show();


                }
                else if(result_msg.equals("중복 로그인")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"중복 로그인", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else if(result_msg.equals("존재하지 않는 계정입니다.")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"존재하지 않는 계정", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else{
                    Toast myToast = Toast.makeText(getApplicationContext(),"비밀번호 틀림", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                /*if((id_textView.getText().toString().equals(str)) && (password_textView.getText().toString().equals(str))){ //문자열 비교 equals(), getText() 가 반환하는 것은 문자열이 아님
                    Intent intent = new Intent(LoginPage.this, MainActivity.class); //파라메터는 현재 액티비티, 전환될 액티비티
                    MyApplication.user_id = id_textView.getText().toString();
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent); //엑티비티 요청
                }
                else if((id_textView.getText().toString().equals("")) || password_textView.getText().toString().equals("")){
                    Toast myToast = Toast.makeText(getApplicationContext(),"아이디나 패스워드를 입력하지 않았습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }
                else {
                    Toast myToast = Toast.makeText(getApplicationContext(),"아이디와 비밀번호가 올바르지 않습니다.", Toast.LENGTH_SHORT);
                    myToast.show();
                }*/
            }
        });

        find_id.setOnClickListener(new View.OnClickListener() { //아이디/비밀번호 찾기 버튼
            @Override
            public void onClick(View v) {
                Toast myToast = Toast.makeText(getApplicationContext(),"아이디/비밀번호 페이지로 이동합니다.", Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() { //회원가입 버튼 -> register 페이지로 이동
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPage.this, RegisterPage.class); //파라메터는 현재 액티비티, 전환될 액티비티
                startActivity(intent); //엑티비티 요청
            }
        });
    }

    /*public void syncCookeFromCookieManagerToHttpClient(String url){
        String cookieString = cookieManager.getCookie(url);
        CookieStore cs =
    }*/

    public class LoginRequest extends AsyncTask<String, Void, String> {
        private   MainActivity mainAct;

        public String POST(String... urls) throws UnsupportedEncodingException {
            String result = "";
            String response = "";
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
                    response = builder.toString();
                    System.out.println(response);
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
            return response;
        }
        @Override
        protected String doInBackground(String... urls) {
            String result = null;
            try {
                result = POST(urls);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return result;
        }
        // onPostExecute displays the results of the AsyncTask.
    /*
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        strJson = result;
        mainAct.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(mainAct, "Received!", Toast.LENGTH_LONG).show();
                try {
                    JSONArray json = new JSONArray(strJson);
                    mainAct.tvResponse.setText(json.toString(1));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
     */
    }

}


