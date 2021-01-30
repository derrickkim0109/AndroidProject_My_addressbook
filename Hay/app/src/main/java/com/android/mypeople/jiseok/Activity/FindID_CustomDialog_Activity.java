package com.android.mypeople.jiseok.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.android.mypeople.youngjae.Activity.FindIDActivity;
import com.android.mypeople.R;
import com.android.mypeople.youngjae.Activity.LoginActivity;

public class FindID_CustomDialog_Activity extends Dialog {

    private Context context;
    private String result;

    public FindID_CustomDialog_Activity(Context context, String result) {
        super(context);
        this.context = context;
        this.result = result;
    }
    public void callDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.costomdialog_findid);
        dialog.show();
        Log.v("DAI","d"+result);

        final TextView mfi_text = dialog.findViewById(R.id.mfi_text);
        // 당신의 계정은 -- 입니다.
        mfi_text.setText("당신의 계정은\n"+result+"입니다.");

        final Button mfi_okbtn= dialog.findViewById(R.id.mfi_okbtn);
        // 확인버튼 여기서 인텐트로 로그인에 계정을 set해줘야함
        final Button mfi_cancelbtn = dialog.findViewById(R.id.mfi_cancelbtn);
        //취소버튼 다시 계정 찾기 진행할 시 필요

        mfi_okbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context, LoginActivity.class);
                intent.putExtra("id_result",result);
                context.startActivity(intent);
                ((FindIDActivity)context).finish();

            }
        });

        mfi_cancelbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }



}
