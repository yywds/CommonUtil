package com.example.studyinfo.Activity;

import android.content.Context;
import android.content.SharedPreferences;
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

public class XiugaiMima extends AppCompatActivity {

    @BindView(R.id.user_password)
    EditText userPassword;
    @BindView(R.id.user_password2)
    EditText userPassword2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xiugaimima);
        ButterKnife.bind(this);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }

    private void modify() {
        String pwd1 = userPassword.getText().toString().trim();
        String pwd2 = userPassword2.getText().toString().trim();
        if (pwd1.isEmpty()) {
            TipDialog.show(this, "请输入密码！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (pwd2.isEmpty()) {
            TipDialog.show(this, "请输入再次输入密码！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else if (!pwd1.equals(pwd2)) {
            TipDialog.show(this, "密码不一致！", TipDialog.SHOW_TIME_SHORT, TipDialog.TYPE_WARNING);
        } else {
            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void run() {
                    //获取存储的用户名
                    SharedPreferences sp = getSharedPreferences("UserName", Context.MODE_PRIVATE);
                    String username = sp.getString("name", null);
                    DBHelper.UpdatePassword(pwd1, username);//密码更新操作
                    Timer timer = new Timer();
                    TimerTask timerTask = new TimerTask() {
                        @Override
                        public void run() {
                           // startActivity(new Intent(XiugaiMima.this, MainActivity.class));
                        }
                    };
                    timer.schedule(timerTask, 1000 * 3); //3秒后跳转到首页
                    Looper.prepare();
                    Toast.makeText(XiugaiMima.this, "修改成功！", Toast.LENGTH_SHORT).show();
                    Looper.loop();
                }
            }).start();

        }
    }

    @OnClick(R.id.btn_ok)
    public void onClick() {
        modify();//修改判断
    }
}
