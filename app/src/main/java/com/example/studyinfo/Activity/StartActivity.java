package com.example.studyinfo.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyinfo.Activity.Login.LoginActivity;
import com.example.studyinfo.R;

import static com.example.studyinfo.Utils.CommonUtils.IntentToPage;
import static com.example.studyinfo.Utils.CommonUtils.QuanPing;
import static com.example.studyinfo.Utils.CommonUtils.SharedInfo;


/**
 * APP引导页
 */
public class StartActivity extends AppCompatActivity {
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_start);
        String NameInfo = SharedInfo("UserName", "name");//用户名
        String XuexiInfo = SharedInfo("XuexiLiang", "xuexi");//学习量
        QuanPing(StartActivity.this);
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void run() {
                if (NameInfo != null && XuexiInfo == null) {
                    IntentToPage(context, MainActivity.class);
                } else if (NameInfo != null && XuexiInfo != null) {
                    IntentToPage(context, MainActivity2.class);
                } else {
                    IntentToPage(context, LoginActivity.class);
                }
                finish();
            }
        }, 3000);
    }

}