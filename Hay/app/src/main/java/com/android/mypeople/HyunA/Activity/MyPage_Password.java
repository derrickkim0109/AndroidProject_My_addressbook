package com.android.mypeople.HyunA.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.mypeople.HyunA.NetworkTask.CUDNetworkTask_Hyeona;
import com.android.mypeople.Minwoo.Activity.MainActivity;
import com.android.mypeople.R;
import com.google.android.material.bottomappbar.BottomAppBar;

public class MyPage_Password extends AppCompatActivity {

    int seq;
    String update_pw;
    EditText mypage_nowPw,mypage_afterPw,mypage_afterPw_check;
    String nowPw,urlAddr,ipurl,urlAddr1;
    TextView mypage_pwmessage1,mypage_pwmessage2,mypage_pwmessage2_1,mypage_pwmessage3;
    Button mypage_updatebtn,mypage_canclebtn;
    LinearLayout ll_hide;
    InputMethodManager inputMethodManager ;
    String pwVaildation = "^.*(?=^.{8,20}$)(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$";

    //앱바
    BottomAppBar bab;
    boolean isCenter=true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page__password);
        Intent intent = getIntent();
        seq = intent.getIntExtra("uSeqno",0);
        Log.v("오키오키","11-------------------------"+seq);
        update_pw = intent.getStringExtra("pw");
        ipurl = intent.getStringExtra("ipurl");
        Log.v("여기","Mypage_Password MacIP : " +ipurl);

        urlAddr = "http://"+ipurl+":8080/mypeople/mypage_pwUpdate.jsp?";



        Log.v("비번","비번"+update_pw);
        mypage_nowPw = findViewById(R.id.mypage_nowPw);
        mypage_canclebtn = findViewById(R.id.mypage_canclebtn);
        mypage_afterPw = findViewById(R.id.mypage_afterPw);
        mypage_afterPw_check = findViewById(R.id.mypage_afterPw_check);
        mypage_pwmessage1 = findViewById(R.id.mypage_pwmessage1);
        mypage_pwmessage2 = findViewById(R.id.mypage_pwmessage2);
        mypage_pwmessage2_1 = findViewById(R.id.mypage_pwmessage2_1);
        mypage_pwmessage3 = findViewById(R.id.mypage_pwmessage3);

        mypage_updatebtn = findViewById(R.id.mypage_updatebtn);

        // 홈버튼 액션을 위한 선언
        bab=findViewById(R.id.bab);

        mypage_nowPw.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        mypage_afterPw.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});
        mypage_afterPw_check.setFilters(new InputFilter[] {new InputFilter.LengthFilter(20)});


        mypage_canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyPage_Password.this, MyPageActivity.class);

                intent.putExtra("macIP", ipurl);
                intent.putExtra("action", "Show_List");
                intent.putExtra("uSeqno", seq);


                startActivity(intent);
            }
        });


        mypage_updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mypage_afterPw.getText().toString().length()==0 ||mypage_afterPw_check.getText().toString().length()==0 || mypage_nowPw.getText().toString().length()==0){
                    String strColor = "#D34646";
                    mypage_pwmessage3.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage3.setText("비밀번호를 전부 입력해주셔야 합니다.");

                }else if (!mypage_afterPw.getText().toString().equals(mypage_afterPw_check.getText().toString())){
                    String strColor = "#D34646";
                    mypage_pwmessage3.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage3.setText("비밀번호 일치확인을 해주세요.");
                }else if(mypage_pwmessage2.getText().equals("비밀번호는 8글자이상, 특수문자와 숫자가 반드시 포함되어야 합니다.")){
                    String strColor = "#D34646";
                    mypage_pwmessage3.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage3.setText("비밀번호를 다시 작성해주세요.");
                }else if(mypage_afterPw.getText().toString().equals(nowPw)){
                    String strColor = "#D34646";
                    mypage_pwmessage3.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage3.setText("이전 비밀번호와 일치합니다.");
                }
                else if(nowPw.equals(update_pw)&&mypage_afterPw.getText().toString().equals(mypage_afterPw_check.getText().toString())){
                    String afterpw = mypage_afterPw.getText().toString();
                    urlAddr1 = urlAddr+"nowpw="+update_pw+"&afterpw="+afterpw+"&seq="+seq;
                    String result = connectUpdateData();
                    if (result.equals("1")) {
                        Log.v("tat","dd-----------------------------------------------------------------"+result);
                        String strColor = "#077C0C";
                        mypage_pwmessage3.setTextColor(Color.parseColor(strColor));
                        mypage_pwmessage3.setText("비밀번호 수정을 완료했습니다.");
                        mypage_pwmessage1.setText("");
                        mypage_pwmessage2.setText("");


                        Intent intent = new Intent(MyPage_Password.this,MyPageActivity.class);
                        intent.putExtra("uSeqno", seq);
                        intent.putExtra("macIP",ipurl);
                        startActivity(intent);



                    }else{
                        mypage_pwmessage3.setText("시스템에 문제가 발생했습니다. 관리자에게 문의부탁드립니다.");
                    };
                }else{
                    String strColor = "#D34646";
                    mypage_pwmessage3.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage3.setText("정보를 다시 한번 확인해 주세요.");
                }
            }
        });

        mypage_nowPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                nowPw = String.valueOf(mypage_nowPw.getText());
                if(nowPw.equals(update_pw)){
                    mypage_pwmessage1.setText("");
                }else{
                    mypage_pwmessage1.setText("비밀번호를 확인해주세요.");
                }
            }
        });

        mypage_afterPw.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mypage_afterPw.getText().toString().matches(pwVaildation)) {
                    String strColor = "#077C0C";
                    mypage_pwmessage2.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage2.setText("사용 가능한 비밀번호입니다.");
                    if (mypage_afterPw.getText().toString().equals(mypage_afterPw_check.getText().toString())) {
                        String strColor1 = "#077C0C";
                        mypage_pwmessage2.setTextColor(Color.parseColor(strColor1));
                        mypage_pwmessage2.setText("비밀번호가 일치합니다.");
                    }if(mypage_afterPw.getText().toString().length()==0 || mypage_afterPw_check.getText().toString().length()==0){
                        mypage_pwmessage2_1.setText("");
                        mypage_pwmessage2.setText("");
                    }if(!mypage_afterPw.getText().toString().equals(mypage_afterPw_check.getText().toString())) {
                        String strColor1 = "#D34646";
                        mypage_pwmessage2_1.setTextColor(Color.parseColor(strColor1));
                        mypage_pwmessage2_1.setText("비밀번호를 확인해주세요.");
                    }
                }
                else{
                    String strColor = "#D34646";
                    mypage_pwmessage2.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage2.setText("비밀번호는 8글자이상, 특수문자와 숫자가 반드시 포함되어야 합니다.");
                };
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mypage_afterPw_check.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mypage_afterPw.getText().toString().equals(mypage_afterPw_check.getText().toString())) {
                    String strColor = "#077C0C";
                    mypage_pwmessage2_1.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage2_1.setText("비밀번호가 일치합니다.");
                }if(mypage_afterPw.getText().toString().length()==0 || mypage_afterPw_check.getText().toString().length()==0){
                    mypage_pwmessage2_1.setText("");
                    mypage_pwmessage2.setText("");
                }
                if(!mypage_afterPw.getText().toString().equals(mypage_afterPw_check.getText().toString())) {
                    String strColor = "#D34646";
                    mypage_pwmessage2_1.setTextColor(Color.parseColor(strColor));
                    mypage_pwmessage2_1.setText("비밀번호를 확인해주세요.");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });


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

    private String connectUpdateData(){
        String result = null;
        try {
            CUDNetworkTask_Hyeona CUDNetworkTask_Hyeona = new CUDNetworkTask_Hyeona(MyPage_Password.this, urlAddr1,"update");
            Object obj = CUDNetworkTask_Hyeona.execute().get();
            result = (String) obj;

        }catch (Exception e){
            e.printStackTrace();
        }return result;
    }

    /////////////////앱바//////////////////////
    public void clickFab(View view) { // 하단 앱바 홈버튼 클릭시
        isCenter= !isCenter;

        if(isCenter) bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        else bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        Intent intent = new Intent(MyPage_Password.this, MainActivity.class);
        intent.putExtra("macIP", ipurl);
        intent.putExtra("uSeqno", seq);
        intent.putExtra("action", "Show_List");
        startActivity(intent);
    }
    /////////////////앱바//////////////////////
}