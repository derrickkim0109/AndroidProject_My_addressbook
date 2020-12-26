package com.android.mypeople;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener{


    //field
    final static String TAG = "GalleryActivity";

    Button contactBtn,galleryBtn;
    Button gallery_enroll_cancelBtn ,gallery_enroll_enrollBtn;
    int reqWidth, reqHeight;
    LinearLayout mainContent, btnGallery,enrollGallery;

    Intent intent;
    DisplayMetrics metrics;
    int inSampleSize = 0, widthRatio = 0;
    BitmapFactory.Options imgOptions,options;
    Bitmap bitmap;
    File file;
    InputStream inputStream;
    ImageView imageView; LinearLayout.LayoutParams params;
    String docId ="", type = "", selection = ""; String[] spilt,selectionArg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);



        galleryBtn = findViewById(R.id.gallery_galleryBtn);
        contactBtn = findViewById(R.id.gallery_contactbtn);
        mainContent = findViewById(R.id.enroll_main);
        btnGallery = findViewById(R.id.gallery_ll_btn);
        enrollGallery = findViewById(R.id.gallery_ll_enroll);

        gallery_enroll_enrollBtn = findViewById(R.id.gallery_enroll_enrollBtn);
        gallery_enroll_cancelBtn = findViewById(R.id.gallery_enroll_cancelBtn);

        gallery_enroll_enrollBtn.setOnClickListener(eClickListener);
        gallery_enroll_cancelBtn.setOnClickListener(eClickListener);


        galleryBtn.setOnClickListener(this);

        ////////////////////////////////////////////////////////////
        //
        //     Date: 2020.12.25-태현
        //       //   //사진 사이즈 정하는곳.
        //
        //
        ////////////////////////////////////////////////////////////

        metrics = this.getResources().getDisplayMetrics();
        reqHeight = metrics.heightPixels;
        reqWidth = metrics.widthPixels;

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_CONTACTS},  100);
        }
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},  100);
        }

    }
    ////////////////////////////////////////////////////////////
    //
    //      Date: 2020.12.25-태현
    //
    //       //   //사진 Layout에 띄우기(사이즈).
    //
    //
    ////////////////////////////////////////////////////////////

    private void insertImageView(String filePath){

        //갤러리 버튼 제거, 등록튼 생성
        btnGallery.setVisibility(View.INVISIBLE);
        enrollGallery.setVisibility(View.VISIBLE);

        if(!filePath.equals("")){
            file = new File(filePath);
            options = new BitmapFactory.Options();
            //초기 이미지 읽기
            options.inJustDecodeBounds = true;
            try {
                inputStream = new FileInputStream(filePath);
                BitmapFactory.decodeStream(inputStream, null,options);
                inputStream.close();
                inputStream = null;

                final int width = options.outWidth;
                inSampleSize = 1 ;

                if(width > reqWidth){
                    widthRatio = Math.round((float)width  / (float)reqWidth);
                    inSampleSize = widthRatio;
                }

                imgOptions = new BitmapFactory.Options();
                imgOptions.inSampleSize = inSampleSize;
                bitmap = BitmapFactory.decodeFile(filePath, imgOptions);

                imageView = new ImageView(this);
                imageView.setImageBitmap(bitmap);
                params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
                mainContent.addView(imageView, params);


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }

    ////////////////////////////////////////////////////////////
    //       Date: 2020.12.25-태현
    //
    //       //   // 커서로 선택한 사진 ID값.
    //
    //
    ////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String getFilePathFromDocumentUri(Context context, Uri uri){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            docId = DocumentsContract.getDocumentId(uri);
            spilt = docId.split(":");

            type = spilt[0];
            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            }
            selection = MediaStore.Images.Media._ID + "=?";
            selectionArg = new String[]{spilt[1]};

            String column = "_data";
            String[] projection = {column};

            Cursor cursor = context.getContentResolver().query(contentUri, projection, selection, selectionArg, null);
            String filePath = null;
            if (cursor != null && cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(column);
                filePath = cursor.getString(column_index);

            }
            cursor.close();
            return filePath;
        }else {
            return null;
        }

    }

    ////////////////////////////////////////////////////////////
    //      Date: 2020.12.25-태현
    //
    //           //갤러리 앱의 컨텐터 이용하기 위한것.
    //    //sement방식으로 결과 값 넘어왔을때 그걸 가지고 파일 경로를 뽑아내는 방식.
    //
    //
    ////////////////////////////////////////////////////////////

    private String getFilePathFromUriSegment(Uri uri) {
        String selection = MediaStore.Images.Media._ID + "=?";

        //?에 해당하는 데이터 주기
        String[] selectionArgs = new String[]{uri.getLastPathSegment()};
        //id값을 그대로 지칭한 것

        String column = "_data";
        String[] projection = {column};
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,selection,selectionArgs,null);

        String filePath = null;
        //무언가 선택 된것이 있다면 if
        if(cursor != null && cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(column);
            filePath = cursor.getString(column_index);

        }
        cursor.close();
        return filePath;
    }

    ////////////////////////////////////////////////////////////
    //
    //      Date: 2020.12.25-태현
    //        //사진 선택시, 연락처 선택시 인덱스 값으로 결과값 되돌려 받기..//
    //                                                        //
    //                                                        //
    ////////////////////////////////////////////////////////////
    @Override
    public void onClick(View v) {

        if(v == contactBtn){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setData(Uri.parse("content://com.android.contacts/data/phones"));
            //Activity로 실행은 시키는 것인데 결과값을 되돌려 받기 위해.
            //이번 요청을 10번으로 식별 하겠다.
            startActivityForResult(intent,10);
        }else if(v == galleryBtn){

            //version이 상위라면.
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
                Intent intent = new Intent();
                intent.setType("image/*");

                //한꺼번에 여러번 선택하는것 허용하는것.
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                //결과값 되돌려 받는곳.
                startActivityForResult(intent,30);

            }
            //version이 하위라면.-> 하나밖에 선택 안됨.
            else{
                Intent intent = new Intent(Intent.ACTION_PICK); //Segment방식
                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent,20);
            }
        }
    }


    ////////////////////////////////////////////////////////////
    //
    //       Date: 2020.12.25-태현
    //      ///startActivityForResult함수로
    //         되돌아 올 경우 사후 처리를 위해 자동호출되는 함수 작성 //
    //
    ////////////////////////////////////////////////////////////

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10 && resultCode == RESULT_OK) {
            String id = Uri.parse(data.getDataString()).getLastPathSegment();
            Cursor cursor = getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER},
                    ContactsContract.Data._ID + "=" + id , null,null);
            //마지막 뽑고자하는 데이터 쓰는것. // 사람이름과 전화번호를 뽑아 내겠다는 얘기.
            cursor.moveToFirst();
            String name = cursor.getString(0);
            String phone = cursor.getString(1);

            TextView textView = new TextView(this);
            textView.setText(name + ":" + phone);
            textView.setTextSize(25);
            textView.setTypeface(null, Typeface.BOLD);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        //Segment 방식 하나밖에 안들어온경우
        else if (requestCode == 20 && resultCode == RESULT_OK ){
            String[] projection = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(data.getData(),projection,null,null,null);
            cursor.moveToFirst();
            String filePath = cursor.getString(0);
            insertImageView(filePath);


        }
        else if (requestCode == 30 && resultCode == RESULT_OK && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            if (data.getClipData() != null){
                ClipData clipData = data.getClipData();
                for (int i = 0; i < clipData.getItemCount();i++){
                    ClipData.Item item = clipData.getItemAt(i);
                    Uri uri = item.getUri();
                    if ("com.android.providers.media.documents".equals(uri.getAuthority()) && Build.VERSION.SDK_INT >= 19){
                        String filePath = getFilePathFromDocumentUri(this,uri);
                        if (filePath != null){
                            insertImageView(filePath);
                        }
                    }else if ("external".equals(uri.getPathSegments().get(0))){
                        String filePath = getFilePathFromUriSegment(uri);
                        if (filePath != null){
                            insertImageView(filePath);
                        }
                    }
                }
            }
            else {
                Uri uri = data.getData();
                String filePath = getFilePathFromDocumentUri(this,uri);
                if (filePath != null){
                    insertImageView(filePath);
                }
            }
        }
    }

    View.OnClickListener eClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.gallery_enroll_enrollBtn:
                    intent = new Intent(GalleryActivity.this,DetailViewActivity.class);
//                    intent.putExtra("imageUri", )

                    break;
                case R.id.gallery_enroll_cancelBtn:
                    intent = new Intent(GalleryActivity.this,DetailViewActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    };








}//---END