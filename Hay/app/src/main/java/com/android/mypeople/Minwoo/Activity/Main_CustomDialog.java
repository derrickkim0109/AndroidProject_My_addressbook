package com.android.mypeople.Minwoo.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.mypeople.Minwoo.NetworkTask.ListNetworkTask;
import com.android.mypeople.R;
import com.android.mypeople.Share.Bean.Bean_friendslist;
import com.android.mypeople.Taehyun.Activity.DetailViewActivity;

import java.util.ArrayList;

public class Main_CustomDialog extends AppCompatActivity{

    String TAG = "Main_CustomDialog";
    private int seq;
    private String ipurl;
    private String urlAddr;
    private Context context;
    String macIP = null;
    String where = null;
    private ArrayList<Bean_friendslist> data = null;

    TextView tv_Call = null;
    TextView tv_Delete = null;
    TextView tv_Detail = null;
    TextView tv_Name = null;
    TextView tv_Msg = null;
    Button btn_Cancel = null;

    int fSeqno = -1;
    int uSeqno = -1;
    String fName = null;
    String fTel = null;
    String fRelation = null;
    String fImage = null;
    String fImageReal = null;
    int fTag1 = -1;
    int fTag2 = -1;
    int fTag3 = -1;
    int fTag4 = -1;
    int fTag5 = -1;
    String fComment = null;
    String fAddress = null;
    String fEmail = null;
    String Tag1 = null;
    String Tag2 = null;
    String Tag3 = null;
    String Tag4 = null;
    String Tag5 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__custom_dialog);

        Intent intent = getIntent();
        macIP = intent.getStringExtra("macIP");
        uSeqno = intent.getIntExtra("uSeqno", 0);
        fSeqno = intent.getIntExtra("fSeqno", 0);
        fName = intent.getStringExtra("fName");
        fTel = intent.getStringExtra("fTel");
        fRelation = intent.getStringExtra("fRelation");
        fImage = intent.getStringExtra("fImage");
        fImageReal = intent.getStringExtra("fImageReal");
        fTag1 = intent.getIntExtra("fTag1", 0);
        fTag2 = intent.getIntExtra("fTag2", 0);
        fTag3 = intent.getIntExtra("fTag3", 0);
        fTag4 = intent.getIntExtra("fTag4", 0);
        fTag5 = intent.getIntExtra("fTag5", 0);
        fComment = intent.getStringExtra("fComment");
        fAddress = intent.getStringExtra("fAddress");
        fEmail = intent.getStringExtra("fEmail");
        Tag1 = intent.getStringExtra("Tag1");
        Tag2 = intent.getStringExtra("Tag2");
        Tag3 = intent.getStringExtra("Tag3");
        Tag4 = intent.getStringExtra("Tag4");
        Tag5 = intent.getStringExtra("Tag5");

        Log.v(TAG, "uSeqno : " + uSeqno);
        Log.v(TAG, "fSeqno : " + fSeqno);
        Log.v(TAG, "fName : " + fName);
        Log.v(TAG, "fTel : " + fTel);
        Log.v(TAG, "fRelation : " + fRelation);
        Log.v(TAG, "fImage : " + fImage);
        Log.v(TAG, "fImageReal : " + fImageReal);
        Log.v(TAG, "fTag1 : " + fTag1);
        Log.v(TAG, "fTag2 : " + fTag2);
        Log.v(TAG, "fTag3 : " + fTag3);
        Log.v(TAG, "fTag4 : " + fTag4);
        Log.v(TAG, "fTag5 : " + fTag5);
        Log.v(TAG, "fComment : " + fComment);
        Log.v(TAG, "fAddress : " + fAddress);
        Log.v(TAG, "fEmail : " + fEmail);
        Log.v(TAG, "Tag1 : " + Tag1);
        Log.v(TAG, "Tag2 : " + Tag2);
        Log.v(TAG, "Tag3 : " + Tag3);
        Log.v(TAG, "Tag4 : " + Tag4);
        Log.v(TAG, "Tag5 : " + Tag5);

        tv_Name = findViewById(R.id.main_Dialog_Tv_Name);
        tv_Call = findViewById(R.id.main_Dialog_Tv_Call);
        tv_Delete = findViewById(R.id.main_Dialog_Tv_Delete);
        tv_Detail = findViewById(R.id.main_Dialog_Tv_Detail);
        tv_Msg = findViewById(R.id.main_Dialog_Tv_Msg);
        btn_Cancel = findViewById(R.id.main_Dialog_Btn_Cancel);

        tv_Name.setText(fName + "님의 연락처");
        tv_Call.setText(fTel + " 전화걸기");
        tv_Msg.setText("문자 보내기");
        tv_Detail.setText(fName + "님의 연락처 보기");
        tv_Delete.setText(fName + "님의 연락처 삭제");

        tv_Detail.setOnClickListener(onClickListener);
        tv_Call.setOnClickListener(onClickListener);
        tv_Delete.setOnClickListener(onClickListener);
        tv_Msg.setOnClickListener(onClickListener);
        btn_Cancel.setOnClickListener(onClickListener);
    }
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.main_Dialog_Tv_Call: // 전화걸기
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:" + fTel));
                    startActivity(intent);
                    break;
                case R.id.main_Dialog_Tv_Msg: // 문자 보내기
                    intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + fTel));
                    startActivity(intent);
                    break;
                case R.id.main_Dialog_Tv_Detail: // 상세보기
                    intent = new Intent(Main_CustomDialog.this, DetailViewActivity.class);

                    // 뭐가 필요하실지 몰라서 다 보냅니다..
                    intent.putExtra("macIP", macIP);
                    intent.putExtra("uSeqno", uSeqno);
                    intent.putExtra("fSeqno", fSeqno);
                    intent.putExtra("fName", fName);
                    intent.putExtra("fRelation", fRelation);
                    intent.putExtra("fTel", fTel);
                    intent.putExtra("fImage", fImage);
                    intent.putExtra("fImageReal", fImageReal);
                    intent.putExtra("fTag1", fTag1);
                    intent.putExtra("fTag2", fTag2);
                    intent.putExtra("fTag3", fTag3);
                    intent.putExtra("fTag4", fTag4);
                    intent.putExtra("fTag5", fTag5);
                    intent.putExtra("fComment", fComment);
                    intent.putExtra("fAddress", fAddress);
                    intent.putExtra("fEmail", fEmail);

                    startActivity(intent);
                    break;
                case R.id.main_Dialog_Tv_Delete: // 연락처 삭제
                    AlertDialog.Builder builder = new AlertDialog.Builder(Main_CustomDialog.this);
                    builder.setTitle("<연락처 삭제>");
                    builder.setMessage(fName + "님의 연락처를 삭제하시겠습니까?");
                    builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            where = "Delete_friend";
                            urlAddr = "http://" + macIP + ":8080/mypeople/friendslist_Delete.jsp?fSeqno=" + fSeqno;
                            Log.v(TAG, "urlAddr : " + urlAddr);
                            connectGetData();
                            Toast.makeText(Main_CustomDialog.this, fName + "님의 연락처가 삭제되었습니다.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                    builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.create().show();
                    break;
                case R.id.main_Dialog_Btn_Cancel: // 취소 버튼
                    finish();
                    break;

            }
        }
    };

    private void connectGetData(){ // 해당 연락처 삭제
        try {
            ListNetworkTask listNetworkTask= new ListNetworkTask(Main_CustomDialog.this, urlAddr, where);
            Object obj = listNetworkTask.execute().get();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}