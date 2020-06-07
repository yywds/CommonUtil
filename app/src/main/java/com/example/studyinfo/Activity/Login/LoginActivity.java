package com.example.studyinfo.Activity.Login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyinfo.Activity.MainActivity;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.CustomVideoView;
import com.example.studyinfo.Utils.DBHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.studyinfo.Utils.CommonUtils.SaveInfo;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.user_name)
    EditText userName;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.videoView)
    CustomVideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ButterKnife.bind(this);
        videoView.setVideoURI(Uri.parse("android.resource://com.example.studyinfo/" + R.raw.vedio));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {

            }
        });
        //循环播放
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoView.start();
            }
        });
        videoView.start();//开始播放
    }

    @OnClick({R.id.btn_login, R.id.tv_user})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                Login();//登录判断
                break;
            case R.id.tv_user:
                startActivity(new Intent(LoginActivity.this, RegisterUserActivity.class));
                break;
        }
    }

    //登录判断
    private void Login() {
            //Mysql数据库操作需要在子线程中进行
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    String uName = userName.getText().toString().trim();
                    String uPwd = userPassword.getText().toString().trim();
                    SaveInfo("UserName","name",uName);
                    //调用DBHelper操作类
                    DBHelper dbHelper = new DBHelper();
                    Boolean result = dbHelper.SelectUserLogin(uName, uPwd);

                    if (uName.isEmpty() && uPwd.isEmpty()) {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "请填写完整信息！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else if (!result) {
                        Looper.prepare();
                        Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                    else {
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finishAffinity();//销毁登录页
                    }
                }
            }).start();
    }
}




