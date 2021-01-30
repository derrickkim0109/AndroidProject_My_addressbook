package com.android.mypeople.Taehyun.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.mypeople.Share.Bean.Bean_friendslist;
import com.android.mypeople.Share.Bean.Bean_tag;
import com.android.mypeople.Minwoo.Activity.MainActivity;
import com.android.mypeople.R;
import com.android.mypeople.Taehyun.NetworkTask.ImageNetworkTask;
import com.android.mypeople.jiseok.NetworkTask.TAGNetworkTask;
import com.android.mypeople.Taehyun.NetworkTask.CUDNetworkTask_TaeHyun;
import com.blogspot.atifsoftwares.circularimageview.CircularImageView;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddFriendsActivity extends AppCompatActivity {

    //field
    final static String TAG = "AddFriendsActivity";
    String urlAddress = null;  // intent타고 넘어온다
    String urlAddr1 = null;  // intent타고 넘어온다
    String sname, stel, semail, srelation, saddress, scomment, macIP;
    int userseq = 0;


    InputMethodManager inputMethodManager ;
    Intent intent;

    BottomAppBar bab;
    boolean isCenter=true;

    LinearLayout ll_hide;
    EditText userName, userTel, userEmail, relation, address, comment;
    ImageView  tag1, tag2, tag3, tag4, tag5;
    Button btnEnroll, btnCancel;
    TextView textView_match;

    //이미지 추가 되는곳
    int limit = 0;
    int t1 = 0, t2 = 0 ,t3 = 0, t4 = 0, t5 = 0 ;
    int limitT1 = 0, limitT2 = 0, limitT3 = 0, limitT4 = 0, limitT5 = 0;

    //TAG이름값 -2020.12.31. -영재
    String Etag1, Etag2, Etag3, Etag4, Etag5;
    Bean_tag bean_tag=null;
    String urlAddr = null;
    ////////////////////////////////////////////////////
    // Date 2020.12.27 - 태현
    ////////////////////////////////
    CircularImageView profileIv;
    ImageButton btnPlus;
    private static final int CAMERA_REQUEST_CODE = 100;   // Camera Return Code
    private static final int STORAGE_REQUEST_CODE = 101;   // Gallery Return Code

    private static final int IMAGE_PICK_CAMERA_CODE = 100;
    private static final int IMAGE_PICK_GALLERY_CODE = 101;

    private String[] cameraPermissions;
    private String[] storagePermissions;

    private Uri imageUri;
    ActionBar actionBar ;
    private String imageName;
    ///////////
    //Data 2020.12.29 -태현.
    private String img_path = null;// 최종 file name
    //2020.12.30.-태현
    private String f_ext = null;// 최종 file extension
    File tempSelectFile;

    //2021.01.02-태현
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //          Tomcat Server의 IP Address와 Package이름은 수정 하여야 함
    //           2021.01.02 -
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    String devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.android.mypeople/Taehyun/Activity";
    String imageurl = null;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addfriends);
        //2020.12.27-태현
        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        userseq = intent.getIntExtra("uSeqno",0);

        urlAddr1 = "http://" + macIP + ":8080/mypeople/mypeople_add_insert.jsp?";

        //init
        actionBar = getSupportActionBar();
        /////////
        userName = findViewById(R.id.add_Edit_username);
        userTel = findViewById(R.id.add_Edit_usertel);
        userEmail = findViewById(R.id.add_Edit_useremail);
        relation = findViewById(R.id.add_Edit_relation);
        address = findViewById(R.id.add_Edit_address);
        comment = findViewById(R.id.add_Edit_comment);
        //Tag
        tag1 = findViewById(R.id.add_tag1);
        tag2 = findViewById(R.id.add_tag2);
        tag3 = findViewById(R.id.add_tag3);
        tag4 = findViewById(R.id.add_tag4);
        tag5 = findViewById(R.id.add_tag5);

        profileIv = findViewById(R.id.add_profileIv);

        cameraPermissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermissions = new String[]{ Manifest.permission.WRITE_EXTERNAL_STORAGE};


        //하이픈
        userTel.addTextChangedListener(new PhoneNumberFormattingTextWatcher());




        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /입력시 자릿수 제한//   2020.12.24-태현     //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////


        userName.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(10)});
        userTel.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(15)});
        userEmail.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(30)});
        relation.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(20)});
        address.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(60)});

        btnPlus = findViewById(R.id.add_Btn_plus);
        btnEnroll = findViewById(R.id.add_enrollBtn);
        btnCancel = findViewById(R.id.add_cancelBtn);
        textView_match = findViewById(R.id.add_textview_match);


        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /키보드 화면 터치시 숨기기위해 선언.2020.12.24-태현         //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////

        //
        ll_hide = findViewById(R.id.add_ll_hide);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  //OS에서 지원해주는 메소드이다.

        //키보드 화면 터치시 숨김.
        ll_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(ll_hide.getWindowToken(),0);
            }
        });

        profileIv.setOnClickListener(onClickListener);
        btnPlus.setOnClickListener(onClickListener);
        btnEnroll.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);

        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /Tag 선언  2020.12.24-태현                 //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////



        tag1.setOnClickListener(tClickListener);
        tag2.setOnClickListener(tClickListener);
        tag3.setOnClickListener(tClickListener);
        tag4.setOnClickListener(tClickListener);
        tag5.setOnClickListener(tClickListener);

        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /이름/ 전화번호 확인  2020.12.24-태현      //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////


        userTel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textView_match.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 홈버튼 액션을 위한 선언
        bab=findViewById(R.id.bab);
    }

    ////////////////////////////////////////////////////////////
    //                                                        //
    //                                                        //
    //        //사진 추가 버튼 // 등록 하기 // 메인 리스트 //2020.12.24-태현         //
    //                                                        //
    //                                                        //
    ////////////////////////////////////////////////////////////



    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch (v.getId()){
                //두개다 profileIv로 간다.
                case R.id.add_profileIv:
                    imagePickDialog();// 2020.12.27 - 태현
                    break;
                case R.id.add_Btn_plus:
                    imagePickDialog(); // 2020.12.27 - 태현
                    break;

                //등록하기 - 2020/12/27 - 태현 - Network 연결.
                case R.id.add_enrollBtn:

                    ///이미지 서버로 업로드 보내는 메소드
                        connectImage();
                    ///// 2021. 01. 02. 태현

                        sname = userName.getText().toString();
                        stel = userTel.getText().toString();
                        semail = userEmail.getText().toString();
                        srelation = relation.getText().toString();
                        saddress = address.getText().toString();
                        scomment = comment.getText().toString();

                    if (sname.equals("")){
                        Toast.makeText(AddFriendsActivity.this,"이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                    }
                    else if (stel.equals("")){
                        Toast.makeText(AddFriendsActivity.this,"전화번호를 입력하세요.",Toast.LENGTH_SHORT).show();
                    }
                    else if (semail.equals("") ){
                        Toast.makeText(AddFriendsActivity.this,"Email을 입력하세요.",Toast.LENGTH_SHORT).show();
                    }
                    else if (srelation.equals("")){
                        srelation =  "null";
                    }
                    else if (saddress.equals("")){
                        saddress =  "null";
                    }
                    else if (scomment.equals("")){
                        scomment =  "null";
                    }

                    else {
                        String result = connectinsert();
                        if(result.equals("1")){
                            textView_match.setText("");
                        }else{
                            textView_match.setText("이름과 연락처를 꼭 입력해주셔야 합니다.");

                        }
                        finish();
                    }
                    break;

                //메인 리스트로 돌아가기
                case R.id.add_cancelBtn:
                    finish();
                    break;
            }
        }
    };



    ////////////////////////////////////////////////////////////
    //                                                        //
    //                                                        //
    //        //사진 //2020.12.27-태현         //
    //                                                        //
    //                                                        //
    ////////////////////////////////////////////////////////////

    private void imagePickDialog() {
        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Pick Image From");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == 0){
                    if (!checkCameraPermissions()){
                        requestCameraPermission();
                    }else {
                        pickFromCamera();
                    }

                }
                else if (which == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else {
                        pickFromGallery();
                    }
                }
            }
        });

        builder.create().show();
    }

    private void pickFromGallery() {

        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_CODE);
    }

    private void pickFromCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image description");

        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(cameraIntent, IMAGE_PICK_CAMERA_CODE);


    }

    private void requestCameraPermission(){
        ActivityCompat.requestPermissions(this, cameraPermissions, CAMERA_REQUEST_CODE);
    }

    private void requestStoragePermission(){
        ActivityCompat.requestPermissions(this, storagePermissions, STORAGE_REQUEST_CODE);
    }
    private boolean checkCameraPermissions(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }

    private boolean checkStoragePermission(){
        boolean result = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }



    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // go back by clicking back button of actionbar
        return super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case CAMERA_REQUEST_CODE:{
                if (grantResults.length > 0){
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted){
                        pickFromCamera();
                    }
                    else {
                        Toast.makeText(this, "Camera & Storage permission are required", Toast.LENGTH_SHORT).show();
                    }
                }

            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0){
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Storage permission is required", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

                try {
                    if (requestCode == IMAGE_PICK_GALLERY_CODE && resultCode == Activity.RESULT_OK){
                        CropImage.activity(data.getData())
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(this);
                    }
                    if (requestCode == IMAGE_PICK_CAMERA_CODE && resultCode == Activity.RESULT_OK){
                        CropImage.activity(imageUri)
                                .setGuidelines(CropImageView.Guidelines.ON)
                                .setAspectRatio(1,1)
                                .start(this);
                    }
                   if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK){
                        CropImage.ActivityResult result = CropImage.getActivityResult(data);
                        if(resultCode == RESULT_OK){
                            Uri resultUri = result.getUri();
                            imageUri = resultUri;
                            profileIv.setImageURI(resultUri);
                        }
                        if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                            Exception error = result.getError();
                            Toast.makeText(this, "" + error, Toast.LENGTH_SHORT).show();
                        }
                    }
                    bitMap(data);
                }catch (Exception e){
                    e.printStackTrace();
                }

        super.onActivityResult(requestCode, resultCode, data);
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //                   Photo App.에서 Image 선택후 작업내용
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void bitMap(Intent data) throws IOException {

        //이미지의 URI를 얻어 경로값으로 반환.
        img_path = getImagePathToUri(data.getData());
        Log.v(TAG, "image path :" + img_path);
        Log.v(TAG, "Data :" +String.valueOf(data.getData()));

        //이미지를 비트맵형식으로 반환
        Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

        //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
        Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
        profileIv.setImageBitmap(image_bitmap_copy);

        // 파일 이름 및 경로 바꾸기(임시 저장, 경로는 임의로 지정 가능)
        String date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
        imageName = date + "." + f_ext;
        tempSelectFile = new File(devicePath , imageName);
        OutputStream out = new FileOutputStream(tempSelectFile);
        image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

        // 임시 파일 경로로 위의 img_path 재정의
        img_path = devicePath + imageName;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //              사용자가 선택한 이미지의 정보를 받아옴
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());

        return imgPath;
    }//end of getImagePathToUri()



    ////////////////////////////////////////////////////////////
    //                                                        //
    //                                                        //
    //       // limit 최대 3번까지 선택. 딸깍이  2020.12.24-태현     //
    //                                                        //
    //                                                        //
    ////////////////////////////////////////////////////////////


    View.OnClickListener tClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // limit 최대 3번까지 선택.
            switch (v.getId()){
                case R.id.add_tag1:

                    if(limitT1 == 0 && limit < 3) {
                        limit++;
                        limitT1++;
                        tag1.setImageResource(R.drawable.firstblack);
                        //DB에 보낼값.
                        t1 = 1;
                        Toast.makeText(AddFriendsActivity.this,Etag1,Toast.LENGTH_SHORT).show();

                    }else if(limitT1 == 1) {
                        limit--;
                        limitT1--;
                        tag1.setImageResource(R.drawable.firstlight);

                        t1 = 0;


                    }
                    break;
                case R.id.add_tag2:
                    if(limitT2 == 0 && limit < 3) {
                        limit++;
                        limitT2++;
                        tag2.setImageResource(R.drawable.secondblack);
                        Toast.makeText(AddFriendsActivity.this,Etag2,Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t2 = 1;


                    }else if(limitT2 == 1){
                        limit--;
                        limitT2--;
                        tag2.setImageResource(R.drawable.secondlight);

                        t2 = 0;


                    }
                    break;
                case R.id.add_tag3:
                    if(limitT3 == 0 && limit < 3) {
                        limit++;
                        limitT3++;
                        tag3.setImageResource(R.drawable.thirdblack);
                        Toast.makeText(AddFriendsActivity.this,Etag3,Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t3 = 1 ;


                    }else if(limitT3 == 1){
                        limit--;
                        limitT3--;
                        tag3.setImageResource(R.drawable.thirdlight);

                        t3 = 0;


                    }
                    break;
                case R.id.add_tag4:
                    if(limitT4 == 0 && limit < 3) {
                        limit++;
                        limitT4++;
                        tag4.setImageResource(R.drawable.fourthblack);
                        Toast.makeText(AddFriendsActivity.this,Etag4,Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t4 = 1;


                    }else if(limitT4 == 1){
                        limit--;
                        limitT4--;
                        tag4.setImageResource(R.drawable.fourthlight);
                        t4 = 0;


                    }
                    break;
                case R.id.add_tag5:
                    if(limitT5 == 0 && limit < 3) {
                        limit++;
                        limitT5++;
                        tag5.setImageResource(R.drawable.fifthblack);
                        Toast.makeText(AddFriendsActivity.this,Etag5,Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t5 = 1;


                    }else if(limitT5 == 1){
                        limit--;
                        limitT5--;
                        tag5.setImageResource(R.drawable.fifthlight);
                        t5 = 0;
                    }
                    break;

            }///End
            Log.v(TAG, String.valueOf(limit));
        }
    };
    private String connectinsert() {
        Log.v(TAG, "connectGetData()");

        urlAddress = urlAddr1 + "fName=" + sname + "&fTel=" + stel + "&fRelation=" + srelation
                + "&fComment=" + scomment + "&fImage=" + imageName + "&fEmail=" + semail + "&fAddress=" + saddress
                + "&fTag1=" + t1 + "&fTag2=" + t2 +"&fTag3=" + t3 + "&fTag4=" + t4 + "&fTag5=" + t5
                + "&uSeqno=" + userseq;
        String result = null;

        try {
            CUDNetworkTask_TaeHyun insertworkTask = new CUDNetworkTask_TaeHyun(AddFriendsActivity.this, urlAddress,"enroll");
            Object obj = insertworkTask.execute().get();
            result = (String)obj;

        }catch (Exception e){
            e.printStackTrace();

        }

        return result;
    }
    private void connectImage(){
        imageurl = "http://" + macIP + ":8080/mypeople/multipartRequest.jsp";
        ImageNetworkTask imageNetworkTask = new ImageNetworkTask(AddFriendsActivity.this,profileIv,img_path,imageurl);
        //////////////////////////////////////////////////////////////////////////////////////////////
        //
        //              NetworkTask Class의 doInBackground Method의 결과값을 가져온다.
        //
        //////////////////////////////////////////////////////////////////////////////////////////////
        try {
            Integer result = imageNetworkTask.execute(100).get();
            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //              doInBackground의 결과값으로 Toast생성
            //
            //////////////////////////////////////////////////////////////////////////////////////////////
            switch (result){
                case 1:
                    Toast.makeText(AddFriendsActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                    //////////////////////////////////////////////////////////////////////////////////////////////
                    //
                    //              Device에 생성한 임시 파일 삭제
                    //
                    //////////////////////////////////////////////////////////////////////////////////////////////
                    File file = new File(img_path);
                    file.delete();
                    break;
                case 0:
                    Toast.makeText(AddFriendsActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    break;
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    ////////////////////////////////////
    // 태그 눌렀을때 태그값 토스트          //
    ///////////////////////////////////
    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }
    private void connectGetData(){

        try {
            urlAddr = "http://"+macIP+":8080/mypeople/tagActivitySelect.jsp?";
            urlAddr = urlAddr+"seq="+userseq;
            TAGNetworkTask tagNetworkTask = new TAGNetworkTask(AddFriendsActivity.this, urlAddr);
            Object obj = tagNetworkTask.execute().get();
            bean_tag = (Bean_tag) obj;

            Etag1 = bean_tag.getTag1();
            Etag2 = bean_tag.getTag2();
            Etag3 = bean_tag.getTag3();
            Etag4 = bean_tag.getTag4();
            Etag5 = bean_tag.getTag5();


            if(bean_tag.getTag1().equals("no")) Etag1 ="";
            if(bean_tag.getTag2().equals("no")) Etag2 ="";
            if(bean_tag.getTag3().equals("no")) Etag3 ="";
            if(bean_tag.getTag4().equals("no")) Etag4 ="";
            if(bean_tag.getTag5().equals("no")) Etag5 ="";



        }catch (Exception e){
            e.printStackTrace();
        }

    }
    public void clickFab(View view) { // 하단 앱바 홈버튼 클릭시
        isCenter= !isCenter;

        if(isCenter) bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        else bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        Intent intent = new Intent(AddFriendsActivity.this, MainActivity.class);
        intent.putExtra("macIP", macIP);
        intent.putExtra("uSeqno", userseq);
        intent.putExtra("action", "Show_List");
        startActivity(intent);
    }


}///----END


