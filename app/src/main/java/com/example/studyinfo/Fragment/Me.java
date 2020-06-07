package com.example.studyinfo.Fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.example.studyinfo.Activity.BeidanciActivity;
import com.example.studyinfo.Activity.Falu;
import com.example.studyinfo.Activity.Login.LoginActivity;
import com.example.studyinfo.Activity.Shengciben;
import com.example.studyinfo.Activity.ShoucangActivity;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.CommonUtils;
import com.kongzue.dialog.v2.TipDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.studyinfo.Utils.CommonUtils.IntentToPage;

public class Me extends Fragment {
    @BindView(R.id.head)
    CircleImageView head;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me, container, false);
        //沉浸式
        getActivity().getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.head, R.id.xiangqing, R.id.shengciben, R.id.pingjia, R.id.gengxin, R.id.falu, R.id.tv_tuichu})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.head:
                break;
            case R.id.xiangqing:
                break;
            case R.id.shengciben:
                startActivity(new Intent(getActivity(), Shengciben.class));//跳转到生词本页
                break;
            case R.id.pingjia:
               IntentToPage(getActivity(), ShoucangActivity.class);//跳转到收藏页
                break;
            case R.id.gengxin:
                TipDialog.show(getActivity(), "当前已是最新版本！", TipDialog.SHOW_TIME_LONG, TipDialog.TYPE_WARNING);
                break;
            case R.id.falu:
                startActivity(new Intent(getActivity(), Falu.class));//跳转到法律页
                break;
            case R.id.tv_tuichu:
                startActivity(new Intent(getActivity(), LoginActivity.class));//跳转登录页页
                break;
        }
    }
}
