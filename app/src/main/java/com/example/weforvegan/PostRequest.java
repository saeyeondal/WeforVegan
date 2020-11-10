package com.example.weforvegan;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

public class PostRequest extends AsyncTask<String, Void, String>{
    private   MainActivity mainAct;
    static Context post_context;
    static HttpURLConnection httpCon;

    public PostRequest(Context post_context){
        this.post_context = post_context;
    }

    public static String POST(String... urls) throws UnsupportedEncodingException {
        String result = "";
        String response = "";
        try {
            URL urlCon = new URL(urls[0]);
            System.out.println(urls[0]);
            httpCon = (HttpURLConnection)urlCon.openConnection(); //Create Connection

            String json = "";

            // build jsonObject
            JSONObject jsonObject = new JSONObject();
            for(int i=1; i<urls.length; i=i+2) {
                System.out.println(urls[i]);
                jsonObject.accumulate(urls[i], urls[i+1]);
            }

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

            SharedPreferences pref = post_context.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE);
            String sessionid = pref.getString("sessionid", null);
            if(sessionid != null)
                httpCon.setRequestProperty("Cookie", sessionid);

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
            System.out.println("예외1");
            e.printStackTrace();
        }
        catch (Exception e) {
            System.out.println("예외2");
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

