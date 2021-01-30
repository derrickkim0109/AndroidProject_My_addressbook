package com.android.mypeople.HyunA.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.mypeople.HyunA.NetworkTask.CUDNetworkTask_Hyeona;
import com.android.mypeople.R;

public class MyPage_CustomDialog extends Dialog {

    private Context context;
    private int seq;
    private String ipurl;
    private String urlAddr;


    public MyPage_CustomDialog(Context context, int seq, String ipurl) {
        super(context);
        this.context = context;
        this.seq = seq;
        this.ipurl = ipurl;
//테스트1
    }

    public void callFunction(final String userDeleteMessage) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.costomdialog_mypage);

        dialog.show();

        final EditText userDeleteMessage_dialog = dialog.findViewById(R.id.userDeleteMessage);
        final Button mpd_okbtn = dialog.findViewById(R.id.mpd_okbtn);
        final Button mpd_cancelbtn = dialog.findViewById(R.id.mpd_cancelbtn);
        final TextView userDeleteText = dialog.findViewById(R.id.userDeleteText);

        mpd_okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //seq 번호 넘겨받음

                String m = userDeleteMessage_dialog.getText().toString();

                Log.v("Tagg","d"+m);
                if(m.length()==0){
                    userDeleteText.setText("입력하지 않았습니다");
                }else if (m.equals("탈퇴합니다")){
                    String userDeleteMessage = m;
                    connectGetData();

                    String result = connectGetData();
                    Log.v("안됨?","ㅇ"+result);
                    if(result.equals("1")){
                        dialog.dismiss();
                        Intent intent = new Intent(context, UserDeleteActivity.class);
                        context.startActivity(intent);
                    }else{
                        userDeleteText.setText("시스템에 문제가 발생했습니다. 관리자에게 문의해주세요.");
                    }


                } else {
                    userDeleteText.setText("틀린문장을 입력했습니다.");
                }
            }
        });

        mpd_cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }

    private String connectGetData(){
        String result = null;

        try {
            urlAddr = ipurl+":8080/mypeople/mypage_userDelete.jsp?seq="+seq;

            CUDNetworkTask_Hyeona CUDNetworkTask_Hyeona = new CUDNetworkTask_Hyeona(context, urlAddr,"update");
            Object obj = CUDNetworkTask_Hyeona.execute().get();
            result = (String) obj;

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


}
