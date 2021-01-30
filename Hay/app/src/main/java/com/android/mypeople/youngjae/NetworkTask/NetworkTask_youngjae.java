package com.android.mypeople.youngjae.NetworkTask;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.mypeople.Share.Bean.Bean_user;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.Timestamp;

public class NetworkTask_youngjae extends AsyncTask<Integer, String, Object> {

    final static String TAG = "here";
    Context context = null;
    String mAddr = null;
    String where = null;
    int loginCheck = 20;
    int useridCheck = 0;
    int userIdFind = 0;
    Bean_user bean_user = null;
    String result = null;

    int jointelCheck = 0;
    // Constructor


    public NetworkTask_youngjae(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader =null;


        try{
            URL url = new URL(mAddr);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);


                while (true){
                    String strline = bufferedReader.readLine();
                    if(strline == null) break;
                    stringBuffer.append(strline + "\n");
                }



                // 로그인일때 parser1 실행
                if(where.equals("login-layout")){
                    parser1(stringBuffer.toString());
                }
                // 중복체크일때 parser2 실행
                if(where.equals("useridCheck")) {
                    parser2(stringBuffer.toString());
                }
                // 로그인체크
                if(where.equals("loginCount")){
                    parserLoginCheck(stringBuffer.toString());
                }
                // ID 찾기
                if(where.equals("useridFind")) {
                   result = parserUserIdFInd(stringBuffer.toString());
                }
                if(where.equals("telcheck")){
                    parser3(stringBuffer.toString());
                }

            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if (bufferedReader != null) bufferedReader.close();
                if (inputStreamReader != null) inputStreamReader.close();
                if (inputStream != null) inputStream.close();

            }catch (Exception e){
                e.printStackTrace();
            }

        }

        // login action 이면 logincheck 리턴
        if(where.equals("login-layout")){
            return bean_user;
        }
        if(where.equals("useridCheck")){
            return useridCheck;
        }
        if(where.equals("loginCount")){
            return loginCheck;
        }
        if(where.equals("useridFind")){
            return result;
        }
        if(where.equals("telcheck")){
            return jointelCheck;
        }
        return null;
    }

    private void parser1(String s){
        Log.v(TAG, "Parser1()");


        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

            for(int i = 0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int userseq = jsonObject1.getInt("uSeqno");
                String userid = jsonObject1.getString("uId");
                String userpw = jsonObject1.getString("uPw");
                String username = jsonObject1.getString("uName");
                String usertel = jsonObject1.getString("uTel");
                Timestamp uinsertdate = Timestamp.valueOf(jsonObject1.getString("uInsertDate"));
                Timestamp udeletedate = Timestamp.valueOf(jsonObject1.getString("uDeleteDate"));

                bean_user = new Bean_user(userseq,userid,userpw,username,usertel,uinsertdate,udeletedate);

                Log.v(TAG,"--------------------------------------------");


            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void parserLoginCheck(String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);

                int count = jsonObject1.getInt("count");

                loginCheck = count;
                Log.v("여기", "parserLoginCheck : " + count);

                Log.v(TAG, "----------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        private void parser2(String s){
            Log.v(TAG,"Parser2()");



            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));


                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                    int useridCheckCount = jsonObject1.getInt("count");

                    switch (useridCheckCount){
                        case 0:
                            Log.v(TAG,"중복 x");
                            break;
                        default:
                            Log.v(TAG,"중복 o");
                            break;
                    }


                    // 필드변수 loginCheck 에다가 제이슨에서 불러온 loginCount 값을 집어넣음
                    // 필드변수는 보라색
                    useridCheck = useridCheckCount;

                    Log.v(TAG, "----------------------------------");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
    }

    private String parserUserIdFInd(String s){
        Log.v(TAG,"parserUserIdFInd()");
        String finduserid = null;

        try {

            JSONObject jsonObject = new JSONObject(s);
            finduserid = jsonObject.getString("uTel");
//            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));

//            for (int i = 0; i < jsonArray.length(); i++){
//                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
//                finduserid = jsonObject1.getString("uId");
//                Log.v(TAG, "finduserid : " + finduserid);
//            }
//



        }catch (Exception e){
            e.printStackTrace();
        }
        return finduserid;
    }


    private void parser3(String s){
        Log.v(TAG,"Parser3()");



        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));


            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int joinTelCheck = jsonObject1.getInt("count");

                switch (joinTelCheck){
                    case 0:
                        Log.v(TAG,"중복 x");
                        break;
                    default:
                        Log.v(TAG,"중복 o");
                        break;
                }


                // 필드변수 loginCheck 에다가 제이슨에서 불러온 loginCount 값을 집어넣음
                // 필드변수는 보라색
                jointelCheck = joinTelCheck;

                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

} // ----------------------------------
