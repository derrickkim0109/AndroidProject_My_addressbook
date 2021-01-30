package com.android.mypeople.jiseok.NetworkTask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.android.mypeople.Share.Bean.Bean_tag;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TAGNetworkTask extends AsyncTask<Integer,String,Object> {

    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null;
    ProgressDialog progressDialog = null;
    Bean_tag bean_tag =null;

    //construct
    public TAGNetworkTask(Context context, String mAddr) {
        this.context = context;
        this.mAddr = mAddr;
        this.bean_tag = new Bean_tag();
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

                parser(stringBuffer.toString());

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
        return bean_tag;
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
            JSONArray jsonArray = new JSONArray(jsonObject.getString("tage_info"));


            for (int i=0; i<jsonArray.length(); i++){
                JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
                String tag1 = jsonObject1.getString("tag1");
                String tag2 = jsonObject1.getString("tag2");
                String tag3 = jsonObject1.getString("tag3");
                String tag4 = jsonObject1.getString("tag4");
                String tag5 = jsonObject1.getString("tag5");
                int userSeqno = jsonObject1.getInt("user_uSeqno");

                Log.v("여기",tag1);
                Log.v("여기",tag2);
                Log.v("여기",tag3);
                Log.v("여기",tag4);
                Log.v("여기",tag5);

                bean_tag = new Bean_tag(tag1,tag2,tag3,tag4,tag5,userSeqno);

            }

        }catch (Exception e){
            e.printStackTrace();
        }


    }


}
