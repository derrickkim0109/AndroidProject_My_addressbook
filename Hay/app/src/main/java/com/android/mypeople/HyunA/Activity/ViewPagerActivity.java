package com.android.mypeople.HyunA.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.android.mypeople.R;
import com.android.mypeople.youngjae.Adapter.PagerAdapter;
import com.android.mypeople.youngjae.Activity.LoginActivity;

import me.relex.circleindicator.CircleIndicator;

public class ViewPagerActivity extends AppCompatActivity {

    private FragmentPagerAdapter fragmentPagerAdapter;
    private ViewPager vp;
    private CircleIndicator indicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
        Button start_btn = findViewById(R.id.start_btn);
        LinearLayout ll_button = findViewById(R.id.ll_button);

        SharedPreferences introskip = getSharedPreferences("introskip", Activity.MODE_PRIVATE);

        String skip2 = introskip.getString("skip",null);

        if(skip2 !=null) {
            Intent intent = new Intent(ViewPagerActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        ll_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String skip = "skip";

                SharedPreferences introskip = getSharedPreferences("introskip", Activity.MODE_PRIVATE);
                SharedPreferences.Editor skipok = introskip.edit();
                skipok.putString("skip", skip);
                skipok.commit();

                Intent intent = new Intent(ViewPagerActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });



        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ViewPagerActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

    }

    private void initView(){
        vp = findViewById(R.id.vp);
        indicator = findViewById(R.id.indicator);

        fragmentPagerAdapter = new PagerAdapter(getSupportFragmentManager());
        vp.setAdapter(fragmentPagerAdapter);
        indicator.setViewPager(vp);
    }
}