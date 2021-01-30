package com.android.mypeople.jiseok.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkTask_jiseok extends AsyncTask<Integer,String,Object> {
    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    String where = null;
    int seq = 0;
    int findPwCount=0;

    //construct


    public NetworkTask_jiseok(Context context, String mAddr, String where) {
        this.context = context;
        this.mAddr = mAddr;
        this.where = where;

    }

    @Override
    protected void onPreExecute() {
        Log.v(TAG,"onPreExecute()");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Data Fetch");
        progressDialog.setMessage("Get....");
        progressDialog.show();
    }

    @Override
    protected Object doInBackground(Integer... integers) {
        Log.v(TAG,"doInBackground()");
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream =  null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            URL url = new URL(mAddr);

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(10000);

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
                inputStreamReader = new InputStreamReader(inputStream);
                bufferedReader = new BufferedReader(inputStreamReader);

                while (true){
                    String  strline = bufferedReader.readLine();
                    if (strline == null) break;
                    stringBuffer.append(strline + "\n");

                }

                if(where.equals("findPwCount")) parser(stringBuffer.toString());
                if(where.equals("selectSeq"))  parser2(stringBuffer.toString());


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

        if(where.equals("findPwCount"))return findPwCount;
        if(where.equals("selectSeq"))return seq;


        return null;
    }

    @Override
    protected void onProgressUpdate(String... values) {
        Log.v(TAG,"onProgressUpdate()");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Object o) {
        Log.v(TAG,"onPostExecute()");
        super.onPostExecute(o);
        progressDialog.dismiss();
    }
    @Override
    protected void onCancelled() {
        Log.v(TAG,"onCancelled()");
        super.onCancelled();
    }

    private void parser(String s){
        Log.v(TAG,"parser()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));


            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int count = jsonObject1.getInt("count");
                Log.v("여기","NetworkTask_jiseok_parserCount : "+count);
                findPwCount = count;

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }
    private void parser2(String s){
        Log.v(TAG,"parser2()");
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonArray = new JSONArray(jsonObject.getString("user_info"));


            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                int userinfoSeq = jsonObject1.getInt("seq");
                Log.v("여기","NetworkTask_jiseok_parserCount : "+userinfoSeq);
                seq = userinfoSeq;

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
