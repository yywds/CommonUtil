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
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyinfo.Activity.DanciFuxiActivity;
import com.example.studyinfo.Activity.FuxiActivity;
import com.example.studyinfo.Activity.Quanbu.QuanBuActivity;
import com.example.studyinfo.Adapter.MeiwenAdapter;
import com.example.studyinfo.Entity.Count;
import com.example.studyinfo.Entity.Meiwen;
import com.example.studyinfo.Entity.Mywords;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.example.studyinfo.Utils.CommonUtils;
import com.kongzue.dialog.v2.WaitDialog;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mysql.jdbc.Statement;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.example.studyinfo.Utils.CommonUtils.CurrentTime;
import static com.example.studyinfo.Utils.CommonUtils.SharedInfo;

public class Train extends Fragment {
    @BindView(R.id.fuxi)
    TextView fuxi;
    @BindView(R.id.ll_show)
    ListView llshow;
    Unbinder unbinder;

    private List<Meiwen> datas; //集合对象
    private MeiwenAdapter adapter; //自定义的Adapter对象

    //在主线程中更新UI
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x1:
                    String total = (String) msg.obj;
                    fuxi.setText("今天还有" + total + "个单词需要复习");
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.xunlian, container, false);
        //沉浸式
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        unbinder = ButterKnife.bind(this, view);
        Post();
        //延迟获取数据才能正常刷新Listview
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                //解决getCacheDir()报空问题
                if (getActivity() != null) {
                    datas = new ArrayList<Meiwen>();
                    getDatas(AppNetConfig.GetMeiwenUrl); //读取生成的json数据
                    adapter = new MeiwenAdapter(getActivity(), datas);// 实例化Adapter对象(注意:必须要写在在getDatas() 方法后面,不然datas中没有数据)
                }
            }
        }, 1000);

         String username = SharedInfo("UserName","name");

        //查询已学单词
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message = handler.obtainMessage();
                try {
                    Class.forName(AppNetConfig.driver);
                    java.sql.Connection cn = DriverManager.getConnection(AppNetConfig.MysqlUrl, AppNetConfig.Mysqluser, AppNetConfig.Mysqlpwd);
                    String sql = "select count(*) from mywords where username = '"+ username +"' and time = '"+ CurrentTime() +"' and fuxistatus = '0'";
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

        return view;
    }

    //向后台Post请求
    public void Post() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //进行Http向后端 post请求
                AsyncHttpClient client = new AsyncHttpClient();
                RequestParams params = new RequestParams();
                client.post(AppNetConfig.SetMeiwenUrl, params, new AsyncHttpResponseHandler() {
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
        final RequestQueue mQueue = Volley.newRequestQueue(getActivity());
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
                            JSONArray jsonArray = jsonObject2.getJSONArray("meiwen");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject item = jsonArray.getJSONObject(i);
                                Meiwen data = new Meiwen();
                                data.setId(item.getString("id"));
                                data.setTitle(item.getString("title"));
                                data.setImage(item.getString("image"));
                                data.setContent(item.getString("content"));
                                datas.add(data);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (llshow != null) {
                            //请求成功后为ListView设置Adapter
                            llshow.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                WaitDialog.show(getActivity(), "数据加载中...");
            }

        }
        );
        mQueue.add(stringRequest);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ll1, R.id.btn_fuxi})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll1:
                startActivity(new Intent(getActivity(), QuanBuActivity.class));//跳转到详细页美文页面
                break;
            case R.id.btn_fuxi:
                startActivity(new Intent(getActivity(), DanciFuxiActivity.class));
                break;
        }
    }
}
