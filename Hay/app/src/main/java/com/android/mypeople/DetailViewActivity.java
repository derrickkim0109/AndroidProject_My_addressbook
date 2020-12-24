package com.android.mypeople;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class DetailViewActivity extends AppCompatActivity {

    //field
    final static String TAG = "DetailViewActivity";
    String urlAddress = null;  // intent타고 넘어온다
    String sname, stel, semail, sphone, macIP;

    InputMethodManager inputMethodManager ;
    Intent intent;

    EditText username, usertel, useremail, relation, address, comment;
    ImageView profile, tag1, tag2, tag3, tag4, tag5;
    Button btnPlus, btnenroll, btncancel;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailview);

        intent = getIntent();

        macIP = intent.getStringExtra("macIP");
        urlAddress = "http://" + macIP + ":8080/test/studentUpdate.jsp?"; //사용자가 입력하는 것. get방식

        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  //OS에서 지원해주는 메소드이다.

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

//        btnPlus.setOnClickListener(onClikcListener);
//        btnenroll.setOnClickListener(onClikcListener);
//        btncancel.setOnClickListener(onClikcListener);


    }



}

