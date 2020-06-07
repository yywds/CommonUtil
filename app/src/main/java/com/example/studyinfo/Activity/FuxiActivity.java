package com.example.studyinfo.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyinfo.Adapter.BeidanciAdapter;
import com.example.studyinfo.Adapter.FuxiAdapter;
import com.example.studyinfo.Entity.Mywords;
import com.example.studyinfo.Entity.Words;
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


public class FuxiActivity extends AppCompatActivity {


    @BindView(R.id.ll_show)
    ListView llShow;

    private List<Mywords> datas; //集合对象
    private FuxiAdapter adapter; //自定义的Adapter对象

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fuxi);
        ListView listView = (ListView) findViewById(R.id.ll_show);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ButterKnife.bind(this);

        Post();
        //延迟获取数据才能正常刷新Listview
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //解决getCacheDir()报空问题
                    datas = new ArrayList<Mywords>();
                    getDatas(AppNetConfig.GetShengciUrl); //读取生成的json数据
                    adapter = new FuxiAdapter(FuxiActivity.this, datas);// 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)

            }
        }, 1000);

        //数据列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建一个意图
                Intent intent = new Intent(FuxiActivity.this, ShowWords.class);
                //在datas中通过点击的位置position通过get()方法获得具体某个图书
                //的数据然后通过Intent的putExtra()传递到DetailActivity中
                intent.putExtra("id", datas.get(position).getId());
                intent.putExtra("instance", datas.get(position).getInstance());
                intent.putExtra("mean", datas.get(position).getMean());
                intent.putExtra("name", datas.get(position).getName());
                intent.putExtra("type", datas.get(position).getType());
                intent.putExtra("yinbiao", datas.get(position).getYinbiao());
                FuxiActivity.this.startActivity(intent);//启动Activity
            }
        });


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
                params.put("username",suser);
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

    //通过接口获取列表的方法
    public void getDatas(String url) {
        datas.clear();
        final RequestQueue mQueue = Volley.newRequestQueue(this);
        JsonObjectRequest stringRequest = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            /**
                             * 对返回的json数据进行解析,然后装入datas集合中
                             */
                            datas.clear();
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("shengci");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Mywords data = new Mywords();
                                data.setId(item.getString("id"));
                                data.setName(item.getString("name"));
                                data.setYinbiao(item.getString("yinbiao"));
                                data.setMean(item.getString("mean"));
                                data.setStatus(item.getString("status"));
                                data.setType(item.getString("type"));
                                data.setInstance(item.getString("instance"));
                                datas.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        //请求成功后为ListView设置Adapter
                        llShow.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                WaitDialog.show(FuxiActivity.this, "数据加载中...");
            }

        }
        );
        mQueue.add(stringRequest);
    }


}
