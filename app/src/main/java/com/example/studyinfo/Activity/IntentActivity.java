package com.example.studyinfo.Activity;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.studyinfo.Utils.CommonUtils.IntentToPage;

public class IntentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        IntentToPage(IntentActivity.this, BeiActivity.class);//跳转到开始背单词页面
         finish();
    }
}
