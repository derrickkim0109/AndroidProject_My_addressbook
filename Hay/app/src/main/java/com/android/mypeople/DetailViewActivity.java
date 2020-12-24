package com.android.mypeople;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DetailViewActivity extends Activity {

    //field
    final static String TAG = "DetailViewActivity";
    String urlAddress = null;  // intent타고 넘어온다
    String sname, stel, semail, sphone, macIP;

    InputMethodManager inputMethodManager ;
    Intent intent;

    LinearLayout ll_hide;
    EditText username, usertel, useremail, relation, address, comment;
    ImageView profile, tag1, tag2, tag3, tag4, tag5;
    Button btnenroll, btncancel;
    ImageButton btnPlus;

    int limit = 0, admit =0;
    int t1 = 0, t2 = 0 ,t3 = 0, t4 = 0, t5 = 0 ;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        intent = getIntent();

        macIP = intent.getStringExtra("macIP");
        urlAddress = "http://" + macIP + ":8080/test/studentUpdate.jsp?"; //사용자가 입력하는 것. get방식



        username = findViewById(R.id.detail_Edit_username);
        usertel = findViewById(R.id.detail_Edit_usertel);
        useremail = findViewById(R.id.detail_Edit_useremail);
        relation = findViewById(R.id.detail_Edit_relation);
        address = findViewById(R.id.detail_Edit_address);
        comment = findViewById(R.id.detail_Edit_comment);

        //입력시 자릿수 제한
        username.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(10)});
        usertel.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(12)});
        useremail.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(30)});
        relation.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(20)});
        address.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(60)});

        btnPlus = findViewById(R.id.detail_Btn_plus);
        btnenroll = findViewById(R.id.detail_enrollBtn);
        btncancel = findViewById(R.id.detail_cancelBtn);

        btnPlus.setOnClickListener(onClickListener);
        btnenroll.setOnClickListener(onClickListener);
        btncancel.setOnClickListener(onClickListener);


        //////사진 추가시 여기로
        profile = findViewById(R.id.detail_icon_profile);


        ///사진 추가


        //Tag
        tag1 = findViewById(R.id.detail_tag1);
        tag2 = findViewById(R.id.detail_tag2);
        tag3 = findViewById(R.id.detail_tag3);
        tag4 = findViewById(R.id.detail_tag4);
        tag5 = findViewById(R.id.detail_tag5);

        tag1.setOnClickListener(tClickListener);
        tag2.setOnClickListener(tClickListener);
        tag3.setOnClickListener(tClickListener);
        tag4.setOnClickListener(tClickListener);
        tag5.setOnClickListener(tClickListener);





        //키보드 화면 터치시 숨기기위해 선언.
        ll_hide = findViewById(R.id.detail_ll_hide);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  //OS에서 지원해주는 메소드이다.

        //키보드 화면 터치시 숨김.
        ll_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(ll_hide.getWindowToken(),0);
            }
        });





    }

    //사진 추가 버튼 // 등록 하기 // 메인 리스트 //
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                    //사진 추가 버튼
                case R.id.detail_Btn_plus:

                    break;
                    //등록하기
                case R.id.detail_enrollBtn:

                    break;

                    //메인 리스트로 돌아가기
                case R.id.detail_cancelBtn:
                    finish();
                    break;
            }
        }
    };

    View.OnClickListener tClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            // admit 버튼 딸깍 허용 // limit 최대 3번까지 선택.
            if(limit < 4 ){

            switch (v.getId()){
                case R.id.detail_tag1:
                    if(admit < 1) {
                        admit = 1;
                        limit ++;
                        tag1.setImageResource(R.drawable.firstblack);

                    }else if(admit == 1) {
                        admit = 0;
                        limit = limit - 1;
                        tag1.setImageResource(R.drawable.firstlight);
                    }
                    break;

                case R.id.detail_tag2:
                    if(admit < 1) {
                        admit = 1;
                        limit ++;
                        tag2.setImageResource(R.drawable.secondblack);

                    }else if(admit == 1){
                        admit = 0;
                        limit = limit - 1;
                        tag2.setImageResource(R.drawable.secondlight);
                    }

                    break;
                case R.id.detail_tag3:
                    if(admit < 1) {
                        admit = 1;
                        limit++;
                        tag3.setImageResource(R.drawable.thirdblack);

                    }else if(admit == 1){
                        admit = 0;
                        limit = limit - 1;
                        tag3.setImageResource(R.drawable.thirdlight);
                    }
                    break;
                case R.id.detail_tag4:
                    if(admit < 1) {
                        admit = 1;
                        limit++;
                        tag4.setImageResource(R.drawable.fourthblack);

                    }else if(admit == 1){
                        admit = 0;
                        limit = limit - 1;
                        tag4.setImageResource(R.drawable.fourthlight);
                    }
                    break;
                case R.id.detail_tag5:
                    if(admit < 1) {
                        admit = 1;
                        limit++;
                        tag5.setImageResource(R.drawable.fifthblack);
                    }else if(admit == 1){
                        admit = 0;
                        limit = limit - 1;
                        tag5.setImageResource(R.drawable.fifthlight);
                    }

                    break;
            }///End
                Log.v(TAG, String.valueOf(admit));
                Log.v(TAG, String.valueOf(limit));
            }
            else {

            }
        }
    };


}

