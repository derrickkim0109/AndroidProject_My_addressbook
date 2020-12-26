package com.android.mypeople;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
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
import android.widget.Toast;

public class DetailViewActivity extends Activity {

    //field
    final static String TAG = "DetailViewActivity";
    String urlAddress = null;  // intent타고 넘어온다
    String sname, stel, semail, sphone, macIP;

    InputMethodManager inputMethodManager ;
    Intent intent;

    LinearLayout ll_hide;
    EditText userName, userTel, userEmail, relation, address, comment;
    ImageView  tag1, tag2, tag3, tag4, tag5;
    Button btnEnroll, btnCancel;
    TextView textView_match;
    ImageButton btnPlus;

    //이미지 추가 되는곳
    ImageView userProfile;
    int limit = 0;
    int t1 = 0, t2 = 0 ,t3 = 0, t4 = 0, t5 = 0 ;
    int limitT1 = 0, limitT2 = 0, limitT3 = 0, limitT4 = 0, limitT5 = 0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        intent = getIntent();

        macIP = intent.getStringExtra("macIP");
        urlAddress = "http://" + macIP + ":8080/test/studentUpdate.jsp?"; //사용자가 입력하는 것. get방식



        userName = findViewById(R.id.detail_Edit_username);
        userTel = findViewById(R.id.detail_Edit_usertel);
        userEmail = findViewById(R.id.detail_Edit_useremail);
        relation = findViewById(R.id.detail_Edit_relation);
        address = findViewById(R.id.detail_Edit_address);
        comment = findViewById(R.id.detail_Edit_comment);


        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /입력시 자릿수 제한//   2020.12.24-태현     //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////


        userName.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(10)});
        userTel.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(12)});
        userEmail.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(30)});
        relation.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(20)});
        address.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(60)});

        btnPlus = findViewById(R.id.detail_Btn_plus);
        btnEnroll = findViewById(R.id.detail_enrollBtn);
        btnCancel = findViewById(R.id.detail_cancelBtn);


        btnPlus.setOnClickListener(onClickListener);
        btnEnroll.setOnClickListener(onClickListener);
        btnCancel.setOnClickListener(onClickListener);


        textView_match = findViewById(R.id.detail_textview_match);

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


        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /Tag 선언  2020.12.24-태현                 //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////


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




        ////////////////////////////////////////////////////////////
        //                                                        //
        //                                                        //
        //                    /키보드 화면 터치시 숨기기위해 선언.2020.12.24-태현         //
        //                                                        //
        //                                                        //
        ////////////////////////////////////////////////////////////

        //
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
                case R.id.detail_Btn_plus:
                    intent = new Intent(DetailViewActivity.this,GalleryActivity.class);
                    startActivity(intent);
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
                case R.id.detail_tag1:

                    if(limitT1 == 0 && limit < 3) {
                        limit++;
                        limitT1++;
                        tag1.setImageResource(R.drawable.firstblack);
                        //DB에 보낼값.
                        t1 = 1;
                        Toast.makeText(DetailViewActivity.this,"친구",Toast.LENGTH_SHORT).show();

                    }else if(limitT1 == 1) {
                        limit--;
                        limitT1--;
                        tag1.setImageResource(R.drawable.firstlight);

                        t1 = 0;


                    }
                        break;
                case R.id.detail_tag2:
                    if(limitT2 == 0 && limit < 3) {
                        limit++;
                        limitT2++;
                        tag2.setImageResource(R.drawable.secondblack);
                        Toast.makeText(DetailViewActivity.this,"가족",Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t2 = 1;


                    }else if(limitT2 == 1){
                        limit--;
                        limitT2--;
                        tag2.setImageResource(R.drawable.secondlight);

                        t2 = 0;


                    }
                    break;
                case R.id.detail_tag3:
                    if(limitT3 == 0 && limit < 3) {
                        limit++;
                        limitT3++;
                        tag3.setImageResource(R.drawable.thirdblack);
                        Toast.makeText(DetailViewActivity.this,"계모임",Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t3 = 1 ;


                    }else if(limitT3 == 1){
                        limit--;
                        limitT3--;
                        tag3.setImageResource(R.drawable.thirdlight);

                        t3 = 0;


                    }
                    break;
                case R.id.detail_tag4:
                    if(limitT4 == 0 && limit < 3) {
                        limit++;
                        limitT4++;
                        tag4.setImageResource(R.drawable.fourthblack);
                        Toast.makeText(DetailViewActivity.this,"조기축구",Toast.LENGTH_SHORT).show();
                        //DB에 보낼값.
                        t4 = 1;


                    }else if(limitT4 == 1){
                        limit--;
                        limitT4--;
                        tag4.setImageResource(R.drawable.fourthlight);
                        t4 = 0;


                    }
                    break;
                case R.id.detail_tag5:
                    if(limitT5 == 0 && limit < 3) {
                        limit++;
                        limitT5++;
                        tag5.setImageResource(R.drawable.fifthblack);
                        Toast.makeText(DetailViewActivity.this,"거래",Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){

        }

    }
}

