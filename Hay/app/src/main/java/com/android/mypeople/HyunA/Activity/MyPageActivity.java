package com.android.mypeople.HyunA.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
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
import com.android.mypeople.Share.Bean.Bean_user;
import com.android.mypeople.HyunA.NetworkTask.myPageNetworkTask;
import com.google.android.material.bottomappbar.BottomAppBar;


public class MyPageActivity extends AppCompatActivity {
    //탈퇴합니다 메세지 받을 부분 없애도 될거같긴한데 일단 둔다
    final String userDeleteMessage = "";
    String TAG = "MyPageActivity";

    //걍주소
    String urlAddr = null;
    //파라미터 값 넘길 주소값
    String urlAddr1 = null;
    Bean_user bean_user = null;
    String ipurl = null;

    String update_tel1, update_name1,update_pw;

    Button mypage_pwbtn, mypage_updatebtn, mypage_canclebtn;
    EditText mypage_tel, mypage_name;
    TextView mypage_id, mypage_userdelete,mypage_telmessage,mypage_complete,mypage_namemessage;

    LinearLayout ll_hide;
    InputMethodManager inputMethodManager ;

    String telVaildation = "^\\d{3}-\\d{3,4}-\\d{4}$";
    String nameVaildation = "^[a-zA-Zㄱ-ㅎ가-힣]+$";

    //앱바
    BottomAppBar bab;
    boolean isCenter=true;


    int seq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //리스트에서 인텐드 받아야함 (user sno 받아야함) 일단 2로 가정
        Intent intent = getIntent();
        seq = intent.getIntExtra("uSeqno", 0);
        Log.v(TAG, "seq : " + seq);
        //ipurl = "http://192.168.0.55";
//        ipurl = "http://192.168.2.3";

        ipurl = intent.getStringExtra("macIP");
        Log.v("여기","MypageActivity MacIP : "+ipurl);

        urlAddr = "http://" + ipurl+ ":8080/mypeople/mypage.jsp?";

        //마이페이지 버튼
        mypage_pwbtn = findViewById(R.id.mypage_pwbtn);
        mypage_updatebtn = findViewById(R.id.mypage_updatebtn);
        mypage_canclebtn = findViewById(R.id.mypage_canclebtn);

        //마이페이지 에디트텍스트
        mypage_tel = findViewById(R.id.mypage_tel);
        mypage_name = findViewById(R.id.mypage_name);
        mypage_tel.setFilters(new InputFilter[] {new InputFilter.LengthFilter(13)});
        mypage_name.setFilters(new InputFilter[] {new InputFilter.LengthFilter(8)});

        //마이페이지 텍스트뷰
        mypage_id = findViewById(R.id.mypage_id);
        mypage_userdelete = findViewById(R.id.mypage_userdelete);

        mypage_telmessage = findViewById(R.id.mypage_telmessage);
        mypage_complete = findViewById(R.id.mypage_complete);

        mypage_namemessage=findViewById(R.id.mypage_namemessage);

        // 홈버튼 액션을 위한 선언
        bab=findViewById(R.id.bab);

        mypage_userdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPage_CustomDialog myPage_customDialog = new MyPage_CustomDialog(MyPageActivity.this,seq,ipurl);
                myPage_customDialog.callFunction(userDeleteMessage);
            }
        });
        //수정버튼
        mypage_updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String update_tel = String.valueOf(mypage_tel.getText());
                Log.v("확인","1111"+update_tel);
                String update_name = String.valueOf(mypage_name.getText());
                Log.v("확인","1111"+update_name);

                if(update_tel1.equals(update_tel)&&update_name.equals(update_name1)){
                    mypage_complete.setText("수정하실 사항이 없습니다");
                    mypage_namemessage.setText("");
                    mypage_telmessage.setText("");
                }if(!update_tel.matches(telVaildation)&&!update_name.matches(nameVaildation)){
                    mypage_namemessage.setText("이름을 확인해주세요");
                    mypage_complete.setText("");
                    mypage_telmessage.setText("전화번호를 확인해주세요");

                }else if(!update_tel.matches(telVaildation) || update_tel.equals("")) {
                    new AlertDialog.Builder(MyPageActivity.this);
                    mypage_telmessage.setText("전화번호를 확인해주세요");
                    mypage_namemessage.setText("");
                    mypage_complete.setText("");

                }else if(!update_name.matches(nameVaildation) ||update_name.equals("") ){
                    new AlertDialog.Builder(MyPageActivity.this);
                    mypage_namemessage.setText("이름을 확인해주세요");
                    mypage_telmessage.setText("");
                    mypage_complete.setText("");
                }
                else {
                    urlAddr1 = ipurl + ":8080/mypeople/mypage_update.jsp?uTel=" + update_tel + "&uName=" + update_name + "&seq=" + seq;
                    String result = connectUpdateData();

                    if (result.equals("1")) {
                        mypage_complete.setText("정보수정이 완료되었습니다.");
                        mypage_telmessage.setText("");
                        mypage_namemessage.setText("");

                    } else {
                        mypage_complete.setText("");
                        mypage_telmessage.setText("이미 등록된 전화번호입니다.");
                    }
                };
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

        //비밀번호 수정 버튼
        mypage_pwbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MyPage_Password.class);
                intent.putExtra("uSeqno",seq);
                intent.putExtra("pw",update_pw);
                intent.putExtra("ipurl",ipurl);
                startActivity(intent);
            }
        });

        //취소버튼
        mypage_canclebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("macIP", ipurl);
                intent.putExtra("uSeqno", seq);
                intent.putExtra("action", "Show_List");
                startActivity(intent);
            }
        });

    }
    /////////////////앱바//////////////////////
    public void clickFab(View view) { // 하단 앱바 홈버튼 클릭시
        isCenter= !isCenter;

        if(isCenter) bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        else bab.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);

        Intent intent = new Intent(MyPageActivity.this, MainActivity.class);
        intent.putExtra("macIP", ipurl);
        intent.putExtra("uSeqno", seq);
        intent.putExtra("action", "Show_List");
        startActivity(intent);
    }
    /////////////////앱바//////////////////////

    @Override
    protected void onResume() {
        super.onResume();
        connectGetData();
    }

    private void connectGetData(){
        Log.v("여기","getdata"+seq);
        try {
            urlAddr = urlAddr+"seq="+seq;
            Log.v("여기","getdata"+urlAddr);
            myPageNetworkTask myPageNetworkTask = new myPageNetworkTask(MyPageActivity.this, urlAddr);
            Object obj = myPageNetworkTask.execute().get();
            bean_user = (Bean_user) obj;

            mypage_tel.setText(bean_user.getuTel());
            mypage_name.setText(bean_user.getuName());
            mypage_id.setText(bean_user.getuId());

            update_tel1 = bean_user.getuTel();
            update_name1 = bean_user.getuName();
            update_pw = bean_user.getuPw();

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private String connectUpdateData(){
        String result = null;
        try {
            CUDNetworkTask_Hyeona CUDNetworkTask_Hyeona = new CUDNetworkTask_Hyeona(MyPageActivity.this, urlAddr1,"update");
            Object obj = CUDNetworkTask_Hyeona.execute().get();
            result = (String) obj;

        }catch (Exception e){
            e.printStackTrace();
        }return result;
    }
}
