package com.android.mypeople;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageNetworkTask extends AsyncTask<Integer, String, Integer> {
                //BackgroundInteger   //
    final static String TAG = "NetworkTask";
    Context context = null;
    String mAddr = null; // 연결할 아이피
    ProgressDialog progressDialog = null;
    String devicePath; // 스마트폰 경로
    ImageView imageView;

    public ImageNetworkTask(Context context, String mAddr, ImageView imageView) { //
        this.context = context;
        this.mAddr = mAddr;
        this.imageView = imageView;
    }


    @Override
    protected void onPreExecute() { // 프로그레스 띄우는 것
        Log.v(TAG, "onPreExecute");
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setTitle("Dialogue");
        progressDialog.setMessage("Down...");
        progressDialog.show();
        
    }

    @Override
    protected void onProgressUpdate(String... values) { // 중간 중간 얼만큼 진행되었는지 체크
        Log.v(TAG, "onProgressUpdate");
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Integer integer) { // 다운로드 끝났을 때 다운로드한 사진을 띄움
        Log.v(TAG, "onPostExecute");
        Bitmap bitmap = BitmapFactory.decodeFile(devicePath);
        imageView.setImageBitmap(bitmap);
        progressDialog.dismiss(); // 다운로드 끝났으니 progressDialog 종료
    }

    @Override
    protected void onCancelled() { // 시간 내에 다운받지 못 했을 경우나 종료했을 경우
        Log.v(TAG,"onCancelled");
        super.onCancelled();
    }

    @Override
    protected Integer doInBackground(Integer... integers) { // 실질적으로 작업하는 거 (파일 가져오는 중)
        Log.v(TAG, "doInBackground()");

        int index = mAddr.lastIndexOf("/"); // /뒤의 이름을 뽑아내기 위함
        String imgName = mAddr.substring(index + 1); // index를 이용해 이름을 추출
        Log.v(TAG, "urlAddress : " + mAddr);
        Log.v(TAG, "index : " + index);
        Log.v(TAG, "Image name : " + imgName);

        devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.android.networkimage/files/" + imgName; // 받아온 이미지를 해당 경로에 넣는 것
        Log.v(TAG, "devicePath : " + devicePath);

        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;

        try{
            URL url = new URL(mAddr); // 해당 주소로 접속 (연결)
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection(); // http일 경우
            httpURLConnection.setConnectTimeout(10000); // 최대 이미지 다운로드 대기 시간 10초

            int len = httpURLConnection.getContentLength(); // 파일 크기 불러오기 (파일 크기 만큼 array를 잡아야 하기 때문)
            byte[] bs = new byte[len]; // 파일 크기 만큼 array를 잡는다.

            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){ // 해당 커넥션으로 연결이 잘 되었을 때
                inputStream = httpURLConnection.getInputStream(); // 이미지를 가져오고
                fileOutputStream = context.openFileOutput(imgName, 0); // 가져온 이미지를 해당 context에 송출

                while (true){
                    int i = inputStream.read(bs); // length만큼 불러오기 (data)
                    if(i < 0) break; // 읽어올 데이터가 없을 경우 break;
                    fileOutputStream.write(bs, 0,i); //이미지는 0부터 (offset이 없다?)
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                if(inputStream != null) inputStream.close();
                if(fileOutputStream != null) fileOutputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
