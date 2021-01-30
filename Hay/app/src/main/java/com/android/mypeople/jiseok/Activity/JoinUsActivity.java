package com.android.mypeople.jiseok.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mypeople.youngjae.Activity.JoinUsAddActivity;
import com.android.mypeople.youngjae.Activity.JoinUs_CustomDialog;
import com.android.mypeople.youngjae.NetworkTask.NetworkTask_youngjae;
import com.android.mypeople.R;

public class JoinUsActivity extends AppCompatActivity {
    //
    final static String TAG = "JoinUsActivty";
    EditText editEmail;
    EditText et_id;
    Button btn_continue;
    Button btn_email;
    TextView tv_idcheck,tv_emailcheck;
    Intent intent;
    String macIP;
    InputMethodManager inputMethodManager ;
    LinearLayout ll_hide;
    String urlAddrloginduplicationCheck = null;
    int Idduplication = 0;

    SendMail sendMail = new SendMail();

    int count = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joinus);

        intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        Log.v(TAG, "macIP : " + macIP);

        et_id = findViewById(R.id.join_et_id);

        editEmail = findViewById(R.id.joinus_edit_eamil);
        btn_continue = findViewById(R.id.join_btn_continue);
        btn_email = findViewById(R.id.join_btn_email);
        tv_idcheck = findViewById(R.id.join_tv_idcheck);
        tv_emailcheck = findViewById(R.id.join_tv_emailcheck);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                        .permitDiskReads()
                        .permitDiskWrites()
                        .permitNetwork().build()
        );




        // 이메일 입력 및 계속하기 버튼. DB저장 X
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                // 입력받은 이메일(ID)를 추가정보 입력페이지로 넘김.
                String et_idsend = et_id.getText().toString();
                Log.v(TAG, "et_idsend : " + et_idsend);




//                    Log.v(TAG,sendMail.emailCode);
                    if(et_idsend.equals("")){
                        Log.v(TAG, "et_idsend = " + et_idsend);
                        tv_idcheck.setText("이메일인증을 해주세요.");
                    }else if(sendMail.emailCode == null){
                        tv_idcheck.setText("이메일인증을 해주세요.");
                        Log.v(TAG, "sendEmail : " + sendMail.emailCode);
                    }else if(sendMail.emailCode.equals(editEmail.getText().toString())){

                        new AlertDialog.Builder(JoinUsActivity.this)
                                .setTitle("이메일인증이 완료 되었습니다.")
                                .setNegativeButton("확인", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        intent = new Intent(JoinUsActivity.this, JoinUsAddActivity.class);
                                        // 입력받은 이메일 넘김.
                                        intent.putExtra("et_idsend", et_idsend);
                                        intent.putExtra("macIP", macIP);  // IP주소를 보내줌.
                                        Log.v(TAG, "macIP123 : " + macIP);
                                        startActivity(intent);
                                    }
                                })
                                .show();
                    }else {
                        tv_emailcheck.setText("인증에 실패하였습니다.");
                    }


            }
        });

        btn_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edyo = et_id.getText().toString();



                if (edyo.equals("") || !android.util.Patterns.EMAIL_ADDRESS.matcher(edyo).matches()) {
                    tv_idcheck.setText("입력정보를 확인해주세요");
                }else {

                    Log.v(TAG, "urlAddrloginduplicationCheck : " + urlAddrloginduplicationCheck);
                    urlAddrloginduplicationCheck = "http://" + macIP + ":8080/mypeople/loginduplicationCheck.jsp?userid=" + edyo;
                    count = loginduplicationCheck();
                    if (count == 0) {

                        sendMail.sendSecurityCode2(getApplicationContext(), edyo);
                        JoinUs_CustomDialog joinUs_customDialog = new JoinUs_CustomDialog(JoinUsActivity.this);
                        joinUs_customDialog.closeDialog();
                        editEmail.setVisibility(View.VISIBLE);
                        tv_idcheck.setText("사용가능한 아이디 입니다.");
                        String strColor = "#077C0C";
                        tv_idcheck.setTextColor(Color.parseColor(strColor));


                    } else {
                        tv_idcheck.setText("중복된 아이디 입니다.");
                        String strColor = "#D34646";
                        tv_idcheck.setTextColor(Color.parseColor(strColor));
                    }
                }

            }
        });




        //키보드 화면 터치시 숨기기위해 선언.
        ll_hide = findViewById(R.id.join_ll_hide);
        inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);  //OS에서 지원해주는 메소드이다.

        //키보드 화면 터치시 숨김.
        ll_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputMethodManager.hideSoftInputFromWindow(ll_hide.getWindowToken(),0);
            }
        });
    }

    private int loginduplicationCheck(){
        try {
            NetworkTask_youngjae networkTask2 = new NetworkTask_youngjae(JoinUsActivity.this, urlAddrloginduplicationCheck, "useridCheck");
            Object obj = networkTask2.execute().get();

            count = (int) obj;
            Log.v("여기","loginCount : " + count);


        }catch (Exception e){
            e.printStackTrace();
        }

        return count;
    }
}