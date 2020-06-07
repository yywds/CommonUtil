package com.example.studyinfo.Activity.Quanbu;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyinfo.Adapter.MeiwenAdapter;
import com.example.studyinfo.Entity.Meiwen;
import com.example.studyinfo.Fragment.Words;
import com.example.studyinfo.Fragment.quanbu.DianyingFragment;
import com.example.studyinfo.Fragment.quanbu.KeChengFragment;
import com.example.studyinfo.Fragment.quanbu.MeiwenFragment;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.kongzue.dialog.v2.WaitDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuanBuActivity extends AppCompatActivity {
    @BindView(R.id.meiwen)
    TextView meiwen;
    @BindView(R.id.kecheng)
    TextView kecheng;
    @BindView(R.id.dianying)
    TextView dianying;
    @BindView(R.id.ll_layout)
    LinearLayout lllayout;
    private List<Meiwen> datas; //集合对象
    private MeiwenAdapter adapter; //自定义的Adapter对象


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanbu);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ButterKnife.bind(this);

        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        beginTransaction.replace(R.id.ll_layout, new MeiwenFragment());//初始化页面
        beginTransaction.commit();
    }


    @OnClick({R.id.meiwen, R.id.kecheng, R.id.dianying})
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.meiwen:
                beginTransaction.replace(R.id.ll_layout, new MeiwenFragment());
                beginTransaction.commit(); //记得comment
                setCurPoint(0);
                break;
            case R.id.kecheng:
                beginTransaction.replace(R.id.ll_layout, new KeChengFragment());
                beginTransaction.commit(); //记得comment
                setCurPoint(1);
                break;
            case R.id.dianying:
                beginTransaction.replace(R.id.ll_layout, new DianyingFragment());
                beginTransaction.commit(); //记得comment
                setCurPoint(2);
                break;
        }
    }
    //点击变化
    private void setCurPoint(int index) {
        if (index == 0) {
            meiwen.setTextSize(20);
            kecheng.setTextSize(15);
            dianying.setTextSize(15);
        }
        if (index == 1) {
            meiwen.setTextSize(15);
            kecheng.setTextSize(20);
            dianying.setTextSize(15);
        }
        if (index == 2) {
            meiwen.setTextSize(15);
            kecheng.setTextSize(15);
            dianying.setTextSize(20);
        }
    }

}
