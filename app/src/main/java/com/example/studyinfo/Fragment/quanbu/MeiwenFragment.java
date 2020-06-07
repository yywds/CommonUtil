package com.example.studyinfo.Fragment.quanbu;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.studyinfo.Activity.Detail.MeiwenActivity;
import com.example.studyinfo.Adapter.MeiwenAdapter;
import com.example.studyinfo.Entity.Meiwen;
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
import butterknife.Unbinder;

public class MeiwenFragment extends Fragment {
    @BindView(R.id.ll_show)
    ListView llshow;
    Unbinder unbinder;

    private List<Meiwen> datas; //集合对象
    private MeiwenAdapter adapter; //自定义的Adapter对象

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.meiwen, container, false);
        //沉浸式
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ListView listView = view.findViewById(R.id.ll_show);
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

        //数据列表点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //创建一个意图
                Intent intent = new Intent(getActivity(), MeiwenActivity.class);
                //在datas中通过点击的位置position通过get()方法获得具体某个图书
                //的数据然后通过Intent的putExtra()传递到DetailActivity中
                intent.putExtra("id", datas.get(position).getId());
                intent.putExtra("content", datas.get(position).getContent());
                intent.putExtra("title", datas.get(position).getTitle());
                intent.putExtra("image", datas.get(position).getImage());
                getActivity().startActivity(intent);//启动Activity
            }
        });

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
                        //请求成功后为ListView设置Adapter
                        llshow.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
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
}
