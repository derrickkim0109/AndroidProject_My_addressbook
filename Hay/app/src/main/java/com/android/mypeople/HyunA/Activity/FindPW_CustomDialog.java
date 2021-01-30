package com.android.mypeople.HyunA.Activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.mypeople.jiseok.Activity.FindPWActivity;
import com.android.mypeople.R;
import com.android.mypeople.youngjae.Activity.LoginActivity;


public class FindPW_CustomDialog extends Dialog {

    private Context context;

    public FindPW_CustomDialog(Context context) {
        super(context);
        this.context = context;
    }
    public void callDialog() {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.costomdialog_findpw);
        dialog.show();

        final Button mfp_okbtn= dialog.findViewById(R.id.mfp_okbtn);
        // 확인버튼 로그인창으로 넘어감
        final Button mfp_cancelbtn = dialog.findViewById(R.id.mfp_cancelbtn);
        //취소버튼 다시 비번 찾기 진행할 시 필요

        mfp_okbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent intent = new Intent(context, LoginActivity.class);
                context.startActivity(intent);
                ((FindPWActivity)context).finish();
            }
        });

        mfp_cancelbtn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }



}
