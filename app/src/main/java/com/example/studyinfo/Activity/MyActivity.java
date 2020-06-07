package com.example.studyinfo.Activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyinfo.Entity.MeiwenEntity;
import com.example.studyinfo.R;
import com.google.gson.Gson;
import com.kongzue.dialog.v2.WaitDialog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;


import static com.example.studyinfo.Utils.CommonUtils.PostUrl;

public class MyActivity extends AppCompatActivity {
    @BindView(R.id.ll_show)
    ListView llShow;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my);
        ButterKnife.bind(this);
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = "http://139.159.154.117:8080/zpsys/cart/json.action";

       okHttpClient.newCall(PostUrl(url)).enqueue(new Callback() {
           @Override
           public void onFailure(Call call, IOException e) {
               Looper.prepare();
               WaitDialog.show(MyActivity.this,"加载中...");
               Looper.loop();
           }

           @RequiresApi(api = Build.VERSION_CODES.KITKAT)
           @Override
           public void onResponse(Call call, Response response) throws IOException {
               String json = response.body().string();
               String json2 = new String(json.getBytes(), StandardCharsets.UTF_8);
               System.out.println(json);
               Gson gson = new Gson();
               MeiwenEntity meiwenEntity = gson.fromJson(json,MeiwenEntity.class);
               String title = meiwenEntity.getResult().getCart().get(1).getTitle();
               System.out.println(title);
           }
       });
    }
}
