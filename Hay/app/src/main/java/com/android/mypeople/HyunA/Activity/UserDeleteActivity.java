package com.android.mypeople.HyunA.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.mypeople.R;
import com.android.mypeople.youngjae.Activity.LoginActivity;

public class UserDeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_delete);

        Button userDelete = findViewById(R.id.userDelete);

        userDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDeleteActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}