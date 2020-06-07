package com.example.studyinfo.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyinfo.Adapter.BeidanciAdapter;
import com.example.studyinfo.Entity.Info;
import com.example.studyinfo.Entity.Words;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.example.studyinfo.Utils.DBHelper;
import com.kongzue.dialog.v2.WaitDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.studyinfo.Utils.CommonUtils.CurrentTime;
import static com.example.studyinfo.Utils.CommonUtils.DialogShow;
import static com.example.studyinfo.Utils.CommonUtils.Fayin;
import static com.example.studyinfo.Utils.CommonUtils.IntentToPage;
import static com.example.studyinfo.Utils.CommonUtils.PostHttpValue2;
import static com.example.studyinfo.Utils.CommonUtils.QuanPing;
import static com.example.studyinfo.Utils.CommonUtils.SaveInfo;
import static com.example.studyinfo.Utils.CommonUtils.SharedInfo;
import static com.example.studyinfo.Utils.CommonUtils.YanChi;

public class DanciFuxiActivity extends AppCompatActivity {


    @BindView(R.id.ll_show)
    ListView llShow;
    @BindView(R.id.renshi)
    TextView renshi;
    @BindView(R.id.soucang)
    TextView shoucang;
    @BindView(R.id.burenshi)
    TextView burenshi;
    @BindView(R.id.Show)
    RelativeLayout Show;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.yinbiao)
    TextView yinbiao;
    @BindView(R.id.bofang)
    ImageView bofang;

    private List<Words> datas; //集合对象
    private List<Info> datainfo; //集合对象
    private BeidanciAdapter adapter; //自定义的Adapter对象
    String NameInfo = SharedInfo("UserName","name");//用户名

    private String ids;
    private String names;
    private String yinbiaos;
    private String means;
    private String statuss;
    private String types;
    private String instances;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bei);
        ButterKnife.bind(this);
        name.setText("点我查看单词");
        QuanPing(this);//全屏
        bofang.setVisibility(View.GONE);
        PostHttpValue2("username", NameInfo, "time", CurrentTime(), AppNetConfig.SetdancifuxiUrl);

        //延迟获取数据才能正常刷新Listview
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                datas = new ArrayList<Words>();
                getDatas(AppNetConfig.GetdancifuxiUrl); //读取生成的json数据
                adapter = new BeidanciAdapter(DanciFuxiActivity.this, datas);// 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)
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
                            datas.clear();
                            JSONObject jsonObject2 = jsonObject.getJSONObject("result");
                            JSONArray jsonArray = jsonObject2.getJSONArray("dancifuxi");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Words data = new Words();
                                data.setId(item.getString("id"));
                                data.setName(item.getString("name"));
                                data.setYinbiao(item.getString("yinbiao"));
                                data.setMean(item.getString("mean"));
                                data.setStatus(item.getString("status"));
                                data.setType(item.getString("type"));
                                data.setInstance(item.getString("instance"));

                                    datas.add(data);
                                    ids = item.getString("id");
                                    names = item.getString("name");
                                    yinbiaos = item.getString("yinbiao");
                                    means = item.getString("mean");
                                    statuss = item.getString("status");
                                    types = item.getString("type");
                                    instances = item.getString("instance");
                                    bofang.setVisibility(View.VISIBLE);
                                    Fayin(DanciFuxiActivity.this, data.getName());//单词发音
                                    name.setText("点我查看单词");
                                    yinbiao.setText(data.getYinbiao());
                                    //请求成功后为ListView设置Adapter
                                    llShow.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }



                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                    name.setText("");
                    yinbiao.setText("");
                    bofang.setVisibility(View.GONE);
                    Show.setVisibility(View.GONE);
                    renshi.setVisibility(View.GONE);
                    burenshi.setVisibility(View.GONE);
                    shoucang.setVisibility(View.GONE);
                    DialogShow(DanciFuxiActivity.this,"今日单词复习任务\n\n已完成");
                    YanChi(DanciFuxiActivity.this,MainActivity2.class);
            }
        }
        );
        mQueue.add(stringRequest);

    }

    @OnClick({R.id.renshi, R.id.soucang, R.id.burenshi,R.id.Show,R.id.bofang,R.id.name})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.renshi:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PostHttpValue2("username", NameInfo, "time", CurrentTime(), AppNetConfig.SetdancifuxiUrl);
                        IntentToPage(DanciFuxiActivity.this,IntentActivity2.class);
                        finish();

                    }
                },3000);

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.UpdateMyWordsFuxi(names,NameInfo);//更新单词
                        Looper.prepare();
                        Toast.makeText(DanciFuxiActivity.this, "认识", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.soucang:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.Shoucang(names,yinbiaos,means,statuss,types,instances,NameInfo);
                        Looper.prepare();
                        Toast.makeText(DanciFuxiActivity.this, "收藏成功", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.burenshi:
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PostHttpValue2("username", NameInfo, "time", CurrentTime(), AppNetConfig.SetdancifuxiUrl);
                        IntentToPage(DanciFuxiActivity.this,IntentActivity2.class);
                        finish();
                    }
                },3000);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        DBHelper.UpdateMyWordsFuxi(names,NameInfo);//更新单词
                        Looper.prepare();
                        Toast.makeText(DanciFuxiActivity.this, "不认识", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
                break;
            case R.id.Show:
                Show.setVisibility(View.GONE);
                break;
            case R.id.name:
                name.setText(names);
                break;
            case R.id.bofang:
                Fayin(DanciFuxiActivity.this, names);//单词发音
                break;
        }
    }

    //返回键操作
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        IntentToPage(DanciFuxiActivity.this,MainActivity2.class);
    }
}
