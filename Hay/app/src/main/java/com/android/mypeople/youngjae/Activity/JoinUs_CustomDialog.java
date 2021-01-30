package com.android.mypeople.youngjae.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.android.mypeople.R;

public class JoinUs_CustomDialog extends Dialog {

    private Context context;


    public JoinUs_CustomDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    public void closeDialog(){
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 타이틀 없애줌
        dialog.setContentView(R.layout.customdialog_joinus);

        dialog.show();
        final Button okbtn = dialog.findViewById(R.id.jue_okbtn);

        okbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}