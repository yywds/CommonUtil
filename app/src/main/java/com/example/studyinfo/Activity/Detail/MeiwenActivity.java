package com.example.studyinfo.Activity.Detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.example.studyinfo.Utils.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MeiwenActivity extends AppCompatActivity {


    @BindView(R.id.iv_tupian)
    ImageView ivTupian;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_content)
    TextView tvContent;
    private String rname;
    private String rimage;
    private String rid;
    private String rcontent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meiwendetail);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ButterKnife.bind(this);
        initViews();//初始化界面
    }

    //初始化界面
    public void initViews() {

        //获取传递过来的参数
        Intent intent = this.getIntent();
        rid = intent.getStringExtra("id");
        rcontent = intent.getStringExtra("content");
        rimage = intent.getStringExtra("image");
        rname = intent.getStringExtra("title");

        //显示数据
        Glide.with(this).load(AppNetConfig.GetImageUrl + rimage).into(ivTupian);
        tvName.setText(rname);
        tvContent.setText(CommonUtils.ToDBC(rcontent));
    }
}
