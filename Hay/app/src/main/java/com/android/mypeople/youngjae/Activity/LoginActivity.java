package com.android.mypeople.youngjae.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.mypeople.jiseok.Activity.FindPWActivity;
import com.android.mypeople.Minwoo.Activity.MainActivity;
import com.android.mypeople.jiseok.Activity.JoinUsActivity;
import com.android.mypeople.youngjae.NetworkTask.NetworkTask_youngjae;
import com.android.mypeople.R;
import com.android.mypeople.Share.Bean.Bean_user;

import java.sql.Timestamp;

public class LoginActivity extends AppCompatActivity {

    final static String TAG = "LoginActivity";
    EditText edtIP, et_id,et_pw;
    TextView tv_join, tv_findid,tv_findpw;
    Button btn_login;
    CheckBox cb_autologin;
    Intent intent;
    InputMethodManager inputMethodManager;
    LinearLayout ll_hide;
    String macIP;
    String urlAddrLogin = null;
    String urlAddrLoginCheck = null;


    String uId, uPw;
    int count = 0;
    int count2 = 0;
    Bean_user bean = new Bean_user();

    // 리스트로 보내는 값.
    int setSeqno;
    String setId;
    String setPw;
    String setName;
    String setTel;
    Timestamp setDeleteDate;
    Timestamp setInsertDate;
    String loginid,loginpw;
    String findID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edtIP = findViewById(R.id.edt_ip);
        macIP = edtIP.getText().toString();
        Log.v(TAG, "macIP : " + macIP);

        btn_login = findViewById(R.id.login_btn_login);

        et_id = findViewById(R.id.login_et_id);
        et_pw = findViewById(R.id.login_et_pw);

        tv_join = findViewById(R.id.login_tv_join);
        tv_findid = findViewById(R.id.login_tv_findid);
        tv_findpw = findViewById(R.id.login_tv_findpw);

        cb_autologin = findViewById(R.id.login_cb_AutoLogin);
        cb_autologin.setOnCheckedChangeListener(mSetCheckChangeListener);
        btn_login.setOnClickListener(onClickListener);
        tv_join.setOnClickListener(onClickListener);
        tv_findid.setOnClickListener(onClickListener);
        tv_findpw.setOnClickListener(onClickListener);

        Intent intent1 = getIntent();
        findID = intent1.getStringExtra("id_result");
        if (findID!=null){
            et_id.setText(findID);
        }

        ////////////////////////////////////////////////////////////////////////
        //자동로그인 / 자동로그인이 되어있을때                                  //
        //                                                                    //
        //                                                                    //
        ////////////////////////////////////////////////////////////////////////
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);

        SharedPreferences.Editor autoLogin = auto.edit();

        autoLogin.clear();
        autoLogin.commit();


        loginid = auto.getString("inputId",null);
        loginpw = auto.getString("inputPw",null);
        Log.v(TAG,"login : " + loginid);
        Log.v(TAG,"loginpw : " + loginpw);
        if(loginid !=null && loginpw != null) {
            urlAddrLogin = "http://" + macIP + ":8080/mypeople/loginCheck.jsp?userid="+loginid+"&userpw="+loginpw;
            urlAddrLoginCheck = "http://"+ macIP +":8080/mypeople/loginCheck_count.jsp?userid="+loginid+"&userpw="+loginpw;
            connectGetData();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("uSeqno", setSeqno);
            Log.v(TAG, "uSeqno : " + setSeqno);
            intent.putExtra("uId", setId);
            intent.putExtra("uPw", setPw);
            intent.putExtra("uName", setName);
            intent.putExtra("uTel", setTel);
            intent.putExtra("uDeleteDate", setDeleteDate);
            intent.putExtra("uInsertDate", setInsertDate);
            intent.putExtra("action", "Show_List");
            String tempIP = edtIP.getText().toString();
            intent.putExtra("macIP", tempIP);  // IP주소를 보내줌.
            Log.v(TAG, "여기기랑 :  " + setSeqno + setId + setPw + setName + setTel + setDeleteDate + setInsertDate);
            startActivity(intent);
            finish();
        }
        /////////////////////////////////////////////////////////////////////////////////


        //키보드 화면 터치시 숨기기위해 선언.
        ll_hide = findViewById(R.id.login_ll_hide);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  //OS에서 지원해주는 메소드이다.

        //키보드 화면 터치시 숨김.
        ll_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(ll_hide.getWindowToken(),0);
            }
        });

    }









    // 로그인 화면 onClickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String tempIP = edtIP.getText().toString();
            uId = et_id.getText().toString();
            uPw= et_pw.getText().toString();
            urlAddrLogin = "http://" + macIP + ":8080/mypeople/loginCheck.jsp?userid="+uId+"&userpw="+uPw;
            urlAddrLoginCheck = "http://"+ macIP +":8080/mypeople/loginCheck_count.jsp?userid="+uId+"&userpw="+uPw;
            switch (v.getId()){

                // 로그인 버튼 //
                case R.id.login_btn_login :

                    Log.v(TAG, "uId : " + uId);
                    Log.v(TAG, "uPw : " + uPw);
                    count = loginCount();
                    if (count == 1) {
                        connectGetData(); // 유저정보 받아옴
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        // 값을 Intent로 보내줌
                        intent.putExtra("uSeqno", setSeqno);
                        Log.v(TAG, "uSeqno : " + setSeqno);
                        intent.putExtra("uId",setId);
                        intent.putExtra("uPw",setPw);
                        intent.putExtra("uName",setName);
                        intent.putExtra("uTel",setTel);
                        intent.putExtra("uDeleteDate",setDeleteDate);
                        intent.putExtra("uInsertDate",setInsertDate);
                        intent.putExtra("action", "Show_List");
                        intent.putExtra("macIP", tempIP);  // IP주소를 보내줌.
                        Log.v(TAG, "여기기랑 :  " + setSeqno + setId +setPw+setName+setTel+setDeleteDate+setInsertDate);
                        startActivity(intent);

                    }else{
                        // 로그인 실패
                        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.clear();
                        autoLogin.commit();

                        new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("입력 정보 오류")
                                .setMessage("입력정보를 확인하세요")
                                .setPositiveButton("확인",null)
                                .show();
                    }
                    break;



                // 아이디 찾기로 이동 //
                case R.id.login_tv_findid :
                    intent = new Intent(LoginActivity.this, FindIDActivity.class);
                    intent.putExtra("macIP", tempIP);
                    startActivity(intent);
                    break;




                // 비번 찾기로 이동 //
                case R.id.login_tv_findpw :
                    intent = new Intent(LoginActivity.this, FindPWActivity.class);
                    intent.putExtra("macIP", tempIP);
                    startActivity(intent);
                    break;



                // 회원가입으로 이동 //
                case R.id.login_tv_join :
                    intent = new Intent(LoginActivity.this, JoinUsActivity.class);
                    intent.putExtra("macIP", tempIP);  // IP주소를 보내줌.
                    Log.v(TAG, "macIP : " + tempIP);
                    startActivity(intent);
                    break;

            }

        }
    };










    // 해당 로그인 아이디 비번이 있으면 로그인 가능.
    private void connectGetData(){

        try {
            NetworkTask_youngjae networkTask_youngjae = new NetworkTask_youngjae(LoginActivity.this, urlAddrLogin, "login-layout");
            Object obj = networkTask_youngjae.execute().get();
            bean = (Bean_user) obj;

            setSeqno = bean.getuSeqno();
            setId = bean.getuId();
            setPw = bean.getuPw();
            setName = bean.getuName();
            setTel = bean.getuTel();
            setDeleteDate = bean.getuDeleteDate();
            setInsertDate = bean.getuInsertDate();


        }catch (Exception e){
            e.printStackTrace();
        }
    }











    // 로그인 할 정보가 있나 count
    private int loginCount(){
        try {
            NetworkTask_youngjae networkTask_youngjae = new NetworkTask_youngjae(LoginActivity.this, urlAddrLoginCheck, "loginCount");
            Object obj = networkTask_youngjae.execute().get();

            count = (int) obj;
            Log.v("여기","loginCount : " + count);


        }catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }


    ////////////////////////////////////////////////////////////////////////
    // 자동 로그인                                                          //
    //                                                                    //
    //                                                                    //
    ////////////////////////////////////////////////////////////////////////
    CheckBox.OnCheckedChangeListener mSetCheckChangeListener =  new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = auto.edit();
                autoLogin.putString("inputId", et_id.getText().toString());
                autoLogin.putString("inputPw", et_pw.getText().toString());
                autoLogin.commit();
                Log.v(TAG, "login2 : " + loginid);
                Log.v(TAG, "loginpw2 : " + loginpw);
            } else {
                SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
                SharedPreferences.Editor autoLogin = auto.edit();
                autoLogin.clear();
                autoLogin.commit();
                Log.v(TAG, "login3 : " + loginid);
                Log.v(TAG, "loginpw3 : " + loginpw);
            }
        }
    };
    ///////////////////////////////////////////////////////////////////////






} //---------------------