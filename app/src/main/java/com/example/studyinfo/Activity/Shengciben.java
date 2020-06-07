package com.example.studyinfo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyinfo.Activity.Login.LoginActivity;
import com.example.studyinfo.Adapter.ShengcibenAdapter;
import com.example.studyinfo.Entity.Mywords;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.kongzue.dialog.v2.TipDialog;
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

public class Shengciben extends AppCompatActivity {
    @BindView(R.id.lv_show)
    ListView lvShow;

    private List<Mywords> datas; //集合对象
    private ShengcibenAdapter adapter; //自定义的Adapter对象;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shengci);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
//         listView = findViewById(R.id.lv_show);
        ButterKnife.bind(this);
        Post();
        //延迟获取数据才能正常刷新Listview
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas = new ArrayList<Mywords>();
                getDatas(AppNetConfig.GetShengciUrl); //读取生成的json数据
                adapter = new ShengcibenAdapter(Shengciben.this, datas);// 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)

            }
        }, 1000);
    }


    //通过接口获取列表的方法
    public void getDatas(String url) {
        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("shengci");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Mywords data = new Mywords();
                                data.setId(item.getString("id"));
                                data.setName(item.getString("name"));
                                data.setType(item.getString("type"));
                                data.setYinbiao(item.getString("yinbiao"));
                                data.setStatus(item.getString("status"));
                                data.setMean(item.getString("mean"));
                                data.setInstance(item.getString("instance"));
                                datas.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //请求成功后为ListView设置Adapter
                        lvShow.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                TipDialog.show(Shengciben.this, "空空如也", TipDialog.SHOW_TIME_LONG, TipDialog.TYPE_WARNING);
            }
        }
        );
        mQueue.add(stringRequest);
    }

    //向后台Post请求
    public void Post() {
        SharedPreferences preferences = getSharedPreferences("UserName", Context.MODE_PRIVATE);
        String suser = preferences.getString("name", null);
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行Http向后端 post请求
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                params.put("username", suser);
                client.post(AppNetConfig.SetShengciUrl, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(String content) {
                        super.onSuccess(content);
                    }

                    @Override
                    public void onFailure(Throwable error, String content) {
                        super.onFailure(error, content);
                    }
                });
            }
        }).start();
    }

    @OnClick(R.id.btn_fuxi)
    public void onClick() {
        startActivity(new Intent(this, ShengciFuxiActivity.class));
    }
}
