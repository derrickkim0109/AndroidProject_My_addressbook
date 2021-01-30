package com.android.mypeople.Minwoo.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.mypeople.Share.Bean.Bean_friendslist;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class ListNetworkTask extends AsyncTask<Integer, String, Object> {

    /*공용이므로 항상 수정사항 알려주시기 바랍니다.*/

    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    ArrayList<Bean_friendslist> friends;

    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - NetworkTask를 검색, 입력, 수정, 삭제 구분없이 하나로 사용키 위해 생성자 변수 추가.
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    String where = null;

    // 검색 내용
    int tagNum = -1;
    String searchText = null;
    String whereAdd = null;

    public ListNetworkTask(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;
        this.friends = new ArrayList<Bean_friendslist>();
        Log.v(TAG, "Start : " + mAddr);
    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG, "onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("Get ....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG, "doInBackground()");

        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;
        ///////////////////////////////////////////////////////////////////////////////////////
        // Date : 2020.12.25
        //
        // Description:
        //  - NetworkTask에서 입력,수정,검색 결과값 관리.
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        String result = null;
        ///////////////////////////////////////////////////////////////////////////////////////
        try {
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
                ///////////////////////////////////////////////////////////////////////////////////////
                // Date : 2020.12.25
                //
                // Description:
                //  - 검색으로 들어온 Task는 parserSelect()로
                //  - 입력, 수정, 삭제로 들어온 Task는 parserAction()으로 구분
                //
                ///////////////////////////////////////////////////////////////////////////////////////
                if(where.equals("select")){
                    parserSelect(stringBuffer.toString()); // 전체 친구 리스트 가져오기
                    Log.v(TAG, "where : " + "select");
                }else if(where.equals("search_with_tag")) {
                    parserSearch_With_Tag(stringBuffer.toString());
                    Log.v(TAG, "where : " + "search_with_tag");
                }else if(where.equals("search")) {
                    parserSearch(stringBuffer.toString());
                    Log.v(TAG, "where : " + "search");
                }else{
                    result = parserAction(stringBuffer.toString());
                    Log.v(TAG, "where : else");
                }
                ///////////////////////////////////////////////////////////////////////////////////////

            }
        }catch (Exception e){
            e.printStackTrace();
            Log.v(TAG, "연결 실패");
        }finally {
            try {
                if(bufferedReader != null) bufferedReader.close();
                if(inputStreamReader != null) inputStreamReader.close();
                if(inputStream != null) inputStream.close();

            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        ///////////////////////////////////////////////////////////////////////////////////////
        // Date : 2020.12.25
        //
        // Description:
        //  - 검색으로 들어온 Task는 members를 return
        //  - 입력, 수정, 삭제로 들어온 Task는 result를 return
        //
        ///////////////////////////////////////////////////////////////////////////////////////
        if(where.equals("select")){
            return friends;
        }else if(where.equals("search")) {
            return friends;
        }else if(where.equals("search_with_tag")) {
            return friends;
        }else{
            return result;
        }
        ///////////////////////////////////////////////////////////////////////////////////////

    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG, "onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();
    }

    @Override
    protected void onCancelled() {
        Log.v(TAG,"onCancelled()");
        super.onCancelled();
    }
    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - 검색후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private void parserSelect(String s){
        Log.v(TAG,"parserSelect()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("friendslist"));
            friends.clear();

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int fSeqno = jsonObject1.getInt("fSeqno");
                String fName = jsonObject1.getString("fName");
                String fTel = jsonObject1.getString("fTel");
                String fRelation = jsonObject1.getString("fRelation");
                String fImage = jsonObject1.getString("fImage");
                String fImageReal = jsonObject1.getString("fImageReal");
                int fTag1 = jsonObject1.getInt("fTag1");
                int fTag2 = jsonObject1.getInt("fTag2");
                int fTag3 = jsonObject1.getInt("fTag3");
                int fTag4 = jsonObject1.getInt("fTag4");
                int fTag5 = jsonObject1.getInt("fTag5");
                String fComment = jsonObject1.getString("fComment");
                String fAddress = jsonObject1.getString("fAddress");
                String fEmail = jsonObject1.getString("fEmail");


                Log.v(TAG, "fSeqno : " + fSeqno);
                Log.v(TAG, "fName : " + fName);
                Log.v(TAG, "fTel : " + fTel);
                Log.v(TAG, "fRelation : " + fRelation);

                Bean_friendslist friend = new Bean_friendslist(fSeqno, fName, fTel, fAddress, fRelation, fComment, fImage, fImageReal, fTag1, fTag2, fTag3, fTag4, fTag5, fEmail);
                friends.add(friend);
                // Log.v(TAG, member.toString());
                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserSearch(String s){
        Log.v(TAG,"parserSearch()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("friendslist"));
            friends.clear();

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int fSeqno = jsonObject1.getInt("fSeqno");
                String fName = jsonObject1.getString("fName");
                String fTel = jsonObject1.getString("fTel");
                String fRelation = jsonObject1.getString("fRelation");
                String fImage = jsonObject1.getString("fImage");
                String fImageReal = jsonObject1.getString("fImageReal");
                int fTag1 = jsonObject1.getInt("fTag1");
                int fTag2 = jsonObject1.getInt("fTag2");
                int fTag3 = jsonObject1.getInt("fTag3");
                int fTag4 = jsonObject1.getInt("fTag4");
                int fTag5 = jsonObject1.getInt("fTag5");
                String fComment = jsonObject1.getString("fComment");
                String fAddress = jsonObject1.getString("fAddress");
                String fEmail = jsonObject1.getString("fEmail");

                Log.v(TAG, "fSeqno : " + fSeqno);
                Log.v(TAG, "fName : " + fName);
                Log.v(TAG, "fTel : " + fTel);
                Log.v(TAG, "fRelation : " + fRelation);

                Bean_friendslist friend = new Bean_friendslist(fSeqno, fName, fTel, fAddress, fRelation, fComment, fImage, fImageReal, fTag1, fTag2, fTag3, fTag4, fTag5, fEmail);
                friends.add(friend);
                // Log.v(TAG, member.toString());
                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parserSearch_With_Tag(String s){
        Log.v(TAG,"parserSearch_With_Tag()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("friendslist"));
            friends.clear();

            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int fSeqno = jsonObject1.getInt("fSeqno");
                String fName = jsonObject1.getString("fName");
                String fTel = jsonObject1.getString("fTel");
                String fRelation = jsonObject1.getString("fRelation");
                String fImage = jsonObject1.getString("fImage");
                String fImageReal = jsonObject1.getString("fImageReal");
                int fTag1 = jsonObject1.getInt("fTag1");
                int fTag2 = jsonObject1.getInt("fTag2");
                int fTag3 = jsonObject1.getInt("fTag3");
                int fTag4 = jsonObject1.getInt("fTag4");
                int fTag5 = jsonObject1.getInt("fTag5");
                String fComment = jsonObject1.getString("fComment");
                String fAddress = jsonObject1.getString("fAddress");
                String fEmail = jsonObject1.getString("fEmail");


                Log.v(TAG, "fSeqno : " + fSeqno);
                Log.v(TAG, "fName : " + fName);
                Log.v(TAG, "fTel : " + fTel);
                Log.v(TAG, "fRelation : " + fRelation);

                Bean_friendslist friend = new Bean_friendslist(fSeqno, fName, fTel, fAddress, fRelation, fComment, fImage, fImageReal, fTag1, fTag2, fTag3, fTag4, fTag5, fEmail);
                friends.add(friend);
                // Log.v(TAG, member.toString());
                Log.v(TAG, "----------------------------------");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////////////////////////////////////////////////////
    // Date : 2020.12.25
    //
    // Description:
    //  - 입력, 수정, 삭제후 json parsing
    //
    ///////////////////////////////////////////////////////////////////////////////////////
    private String parserAction(String s){
        Log.v(TAG,"parserAction()");
        String returnValue = null;

        try {
            Log.v(TAG, s);

            JSONObject jsonObject = new JSONObject(s);
            returnValue = jsonObject.getString("result");
            Log.v(TAG, returnValue);

        }catch (Exception e){
            e.printStackTrace();
        }
        return returnValue;
    }
    ///////////////////////////////////////////////////////////////////////////////////////

}
