package com.example.studyinfo.Fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.studyinfo.Activity.BeidanciActivity;
import com.example.studyinfo.Activity.SelectWords;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.mysql.jdbc.Statement;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class Words extends Fragment {
    @BindView(R.id.jrshuliang)
    TextView jrshuliang;
    @BindView(R.id.syshuliang)
    TextView syshuliang;
    @BindView(R.id.jindu)
    TextView jindu;
    Unbinder unbinder;

    //在主线程中更新UI
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1:
                    String total = (String) msg.obj;
                    System.out.println(total);
                    if (total != null) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("TotalWords", Context.MODE_PRIVATE);
                        preferences.edit().putString("total", total).apply();
                    }
                    break;
                case 0x2:
                    String yixue = (String) msg.obj;
                    System.out.println(yixue);
                    if (yixue != null) {
                        SharedPreferences preferences = getActivity().getSharedPreferences("YixueWords", Context.MODE_PRIVATE);
                        preferences.edit().putString("yixue", yixue).apply();
                    }
                    break;
            }
        }
    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.danci, container, false);
        //沉浸式
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        unbinder = ButterKnife.bind(this, view);
        initView();//初始化
        return view;
    }

    private void initView() {

        SharedPreferences preferences = getActivity().getSharedPreferences("UserName", Context.MODE_PRIVATE);
        String username = preferences.getString("name", null);
        //查询单词总数
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                try {
                    Class.forName(AppNetConfig.driver);
                    java.sql.Connection cn = DriverManager.getConnection(AppNetConfig.MysqlUrl, AppNetConfig.Mysqluser, AppNetConfig.Mysqlpwd);
                    String sql = "select count(*) from words";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String mybook = rs.getString("count(*)");
                        message.what = 0x1;
                        message.obj = mybook;
                    }
                    cn.close();
                    st.close();
                    rs.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(message); // 发送到主线程更新UI
            }
        }).start();

        //查询已学单词
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                try {
                    Class.forName(AppNetConfig.driver);
                    java.sql.Connection cn = DriverManager.getConnection(AppNetConfig.MysqlUrl, AppNetConfig.Mysqluser, AppNetConfig.Mysqlpwd);
                    String sql = "select count(*) from mywords where username = '"+ username +"' and status = 1";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);
                    while (rs.next()) {
                        String mybook = rs.getString("count(*)");
                        message.what = 0x2;
                        message.obj = mybook;
                    }
                    cn.close();
                    st.close();
                    rs.close();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                handler.sendMessage(message); // 发送到主线程更新UI
            }
        }).start();

        SharedPreferences zong = getActivity().getSharedPreferences("TotalWords", Context.MODE_PRIVATE);
        String zongshu = zong.getString("total", null);
        System.out.println(zongshu);

        SharedPreferences yixue = getActivity().getSharedPreferences("YixueWords", Context.MODE_PRIVATE);
        String yi = yixue.getString("yixue", null);
        System.out.println(yi);

        jindu.setText("学习进度    "+ yi + "/" + zongshu);
        if(zongshu != null && yi != null) {
            Integer a = Integer.valueOf(zongshu);
            Integer b = Integer.valueOf(yi);
            Integer c = a - b;
            String d = String.valueOf(c);
            syshuliang.setText(c + "个");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.top2, R.id.btn_kaishi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.top2:
                startActivity(new Intent(getActivity(), SelectWords.class));//跳转到查询单词页
                break;
            case R.id.btn_kaishi:
                startActivity(new Intent(getActivity(), BeidanciActivity.class));//跳转到背单词页
                break;
        }
    }
}
