package com.example.studyinfo.Activity.Login;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.studyinfo.R;
import com.example.studyinfo.Utils.DBHelper;
import com.kongzue.dialog.v2.TipDialog;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.studyinfo.Utils.CommonUtils.ClearInfo;
import static com.example.studyinfo.Utils.CommonUtils.SaveInfo;

public class RegisterUserActivity extends AppCompatActivity {


    @BindView(R.id.tv_name)
    EditText tvName;
    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.user_password2)
    EditText userPassword2;
    @BindView(R.id.tv_phone)
    EditText tvPhone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registeruser_activity);
        ButterKnife.bind(this);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);


    }


    //注册判断
    private void Register() {

        //获取输入框中的内容
        final String uName = tvName.getText().toString().trim();
        final String uPhone = tvPhone.getText().toString().trim();
        final String uPassword = userPassword.getText().toString().trim();
        final String uPassword2 = userPassword2.getText().toString().trim();

        if (uName.isEmpty()) {
            TipDialog.show(this, "请输入用户名！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (uPhone.isEmpty()) {
            TipDialog.show(this, "请输入手机号！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (uPassword.isEmpty()) {
            TipDialog.show(this, "请输入密码！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (uPassword2.isEmpty()) {
            TipDialog.show(this, "请输入确认密码！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (!uPassword.equals(uPassword2)) {
            TipDialog.show(this, "密码不一致！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (uPhone.length() < 11){
            TipDialog.show(this, "请输入正确的手机号", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else {

            //数据库操作要在子线程中进行，不然会报错
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    //调用DBHelper
                    DBHelper dbHelper = new DBHelper();
                    Boolean result = dbHelper.SelectUserName(uName);
                    if (result) {
                        Looper.prepare();
                        Toast.makeText(RegisterUserActivity.this, "该用户已存在！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    } else {

                        Timer timer = new Timer();
                        TimerTask timerTask1 = new TimerTask() {
                            @Override
                            public void run() {
                                startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class));
                            }
                        };
                        timer.schedule(timerTask1, 1000 * 3); //3秒后跳转到登录页
                        DBHelper.InsertUser(uName, uPassword,uPhone);//进行注册
                        //清除保存的信息
                        ClearInfo("UserName");
                        ClearInfo("XuexiLiang");
                        ClearInfo("Xuanze");
                        ClearInfo("Count");

//                        SaveInfo("UserName","name",uname);//存储用户名
                        Looper.prepare();
                        Toast.makeText(RegisterUserActivity.this, "注册成功！", Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }
            }).start();
        }
    }

    //返回操作
    private void fanhui() {
        startActivity(new Intent(RegisterUserActivity.this, LoginActivity.class)); //跳转到登录页
        finish();
    }

    @OnClick({R.id.iv_fanhui, R.id.btn_ook})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_fanhui:
                fanhui();//跳转到登录页
                break;
            case R.id.btn_ook:
                Register();//注册判断
                break;
        }
    }
}
