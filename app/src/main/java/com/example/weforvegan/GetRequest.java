package com.example.weforvegan;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetRequest extends AsyncTask<String, Void, String> {
    HttpURLConnection con;
    Context get_context;

    public GetRequest(Context get_context){
        this.get_context = get_context;
    }
    @Override
    protected String doInBackground(String... urlstr) {
        String result = null;
        try{
            URL url = new URL(urlstr[0]);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Accept-Charset", "UTF-8");
            con.setDoInput(true);

            System.out.println("들어왔어");

            setCookieHeader(get_context);

            System.out.println("헤더 들어감");
            InputStreamReader inputStrteam = new InputStreamReader(con.getInputStream(), "UTF-8");
            BufferedReader reader = new BufferedReader(inputStrteam);
            StringBuilder builder = new StringBuilder();
            String resultStr;

            while((resultStr = reader.readLine()) != null){
                builder.append(resultStr + "\n");
            }
            result = builder.toString();
            reader.close();
        }
        catch (MalformedURLException e) {
            System.out.println("에러1");
            e.printStackTrace();
        }
        catch (Exception e){
            System.out.println("에러2");
            e.printStackTrace();
        }
        return result;
    }

    private void setCookieHeader(Context context){
        SharedPreferences pref = context.getSharedPreferences("sessionCookie", Context.MODE_PRIVATE);
        String sessionid = pref.getString("sessionid", null);
        if(sessionid != null){
            System.out.println("세션 아이디 " + sessionid + "가 요청 헤더에 포함 되었습니다.");
            con.setRequestProperty("Cookie", sessionid);
        }
    }

    /*public static String[] jsonParser_users(String jsonString){
        String[] userArray = null;

        try{
            JSONArray jsonArray = new JSONObject(jsonString).getJSONArray("user_select");
            userArray = new String[12];
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject location = jsonArray.getJSONObject(i);
                userArray[0] = location.optString("user_name");
                userArray[1] = location.optString("user_id");
                userArray[2] = location.optString("user_passwd");
                userArray[3]= location.optString("user_email");
                userArray[4]= location.optString("user_phone");
                userArray[5]= location.optString("user_stickNum");
                userArray[6]= location.optString("user_address");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userArray;
    }*/
}