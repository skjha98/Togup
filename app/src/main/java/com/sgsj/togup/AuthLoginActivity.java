package com.sgsj.togup;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class AuthLoginActivity extends AppCompatActivity {
    private TextView loginTab, signUpTab;

    private ViewPager authPager;

    private PagerViewAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(R.layout.activity_login_main);

        loginTab = findViewById(R.id.auth_login_tab1);
        signUpTab = findViewById(R.id.auth_login_tab2);

        authPager = findViewById(R.id.auth_login_pager);

        pagerAdapter = new PagerViewAdapter(getSupportFragmentManager());
        authPager.setAdapter(pagerAdapter);

        loginTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPager.setCurrentItem(0);
            }
        });

        signUpTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPager.setCurrentItem(1);
            }
        });

        authPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void changeTab(int position) {
        if(position == 0){
            loginTab.setTextColor(getResources().getColor(R.color.tabSelectedColor));
            loginTab.setBackgroundResource(R.drawable.auth_login_tab_selected_bg);
            signUpTab.setTextColor(getResources().getColor(R.color.tabNotSelectedColor));
            signUpTab.setBackgroundResource(R.drawable.auth_login_tab_not_selected_bg);
        }
        else if(position == 1){
            loginTab.setTextColor(getResources().getColor(R.color.tabNotSelectedColor));
            loginTab.setBackgroundResource(R.drawable.auth_login_tab_not_selected_bg);
            signUpTab.setTextColor(getResources().getColor(R.color.tabSelectedColor));
            signUpTab.setBackgroundResource(R.drawable.auth_login_tab_selected_bg);
        }

    }
}


