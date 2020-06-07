package com.example.studyinfo.Activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studyinfo.Fragment.BeidanciFragment;
import com.example.studyinfo.Fragment.Me;
import com.example.studyinfo.Fragment.Train;
import com.example.studyinfo.Fragment.Words;
import com.example.studyinfo.R;
import com.kongzue.dialog.v2.WaitDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity2 extends AppCompatActivity {

    @BindView(R.id.ll_layout)
    LinearLayout llLayout;
    @BindView(R.id.iv_home_s)
    ImageView ivHomeS;
    @BindView(R.id.tv_shouye_s)
    TextView tvShouyeS;
    @BindView(R.id.iv_tuijie_s)
    ImageView ivTuijieS;
    @BindView(R.id.tv_tuijie_s)
    TextView tvTuijieS;
    @BindView(R.id.iv_luntan_s)
    ImageView ivLuntanS;
    @BindView(R.id.tv_luntan_s)
    TextView tvLuntanS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();//初始化
    }


    //初始化首页界面
    private void init() {
                WaitDialog.dismiss();//2秒后取消对话框加载数据
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.replace(R.id.ll_layout, new BeidanciFragment());//初始化显示单词页面
                beginTransaction.commit();
    }

    @OnClick({R.id.tab_rel_home_s, R.id.tab_rel_recommend_s, R.id.tab_rel_discuss_s})
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
                case R.id.tab_rel_home_s:
                    beginTransaction.replace(R.id.ll_layout, new BeidanciFragment());
                    beginTransaction.commit(); //记得comment
                    setCurPoint(0);
                    break;
                case R.id.tab_rel_recommend_s:
                    beginTransaction.replace(R.id.ll_layout, new Train());
                    beginTransaction.commit(); //记得comment
                    setCurPoint(1);
                    break;
                case R.id.tab_rel_discuss_s:
                    beginTransaction.replace(R.id.ll_layout, new Me());
                    beginTransaction.commit(); //记得comment
                    setCurPoint(2);
                    break;
            }
        }

    //点击底部栏颜色变化
    private void setCurPoint(int index) {
        if (index == 0) {
            tvShouyeS.setTextColor(Color.parseColor("#0099FF"));
            tvTuijieS.setTextColor(Color.parseColor("#666666"));
            tvLuntanS.setTextColor(Color.parseColor("#666666"));
            ivHomeS.setImageDrawable(getResources().getDrawable(R.drawable.danci2));
            ivTuijieS.setImageDrawable(getResources().getDrawable(R.drawable.xunlian1));
            ivLuntanS.setImageDrawable(getResources().getDrawable(R.drawable.wode1));

        }
        if (index == 1) {
            tvShouyeS.setTextColor(Color.parseColor("#666666"));
            tvTuijieS.setTextColor(Color.parseColor("#0099FF"));
            tvLuntanS.setTextColor(Color.parseColor("#666666"));
            ivHomeS.setImageDrawable(getResources().getDrawable(R.drawable.danci1));
            ivTuijieS.setImageDrawable(getResources().getDrawable(R.drawable.xunlian2));
            ivLuntanS.setImageDrawable(getResources().getDrawable(R.drawable.wode1));
        }
        if (index == 2) {
            tvShouyeS.setTextColor(Color.parseColor("#666666"));
            tvTuijieS.setTextColor(Color.parseColor("#666666"));
            tvLuntanS.setTextColor(Color.parseColor("#0099FF"));
            ivHomeS.setImageDrawable(getResources().getDrawable(R.drawable.danci1));
            ivTuijieS.setImageDrawable(getResources().getDrawable(R.drawable.xunlian1));
            ivLuntanS.setImageDrawable(getResources().getDrawable(R.drawable.wode2));
        }
    }
}
