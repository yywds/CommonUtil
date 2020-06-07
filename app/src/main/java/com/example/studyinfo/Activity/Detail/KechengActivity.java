package com.example.studyinfo.Activity.Detail;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.jzvd.JZVideoPlayerStandard;


public class KechengActivity extends AppCompatActivity {

    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.show)
    JZVideoPlayerStandard show;
    @BindView(R.id.ll_show)
    ListView llShow;
    private String rname;
    private String rimage;
    private String rid;
    private String rurl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kechengdetail);
        //沉浸式
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        ButterKnife.bind(this);
        initViews();//初始化界面


    }

    //初始化界面
    public void initViews() {

        //获取传递过来的参数
        Intent intent = this.getIntent();
        rid = intent.getStringExtra("id");
        rurl = intent.getStringExtra("vurl");
        rimage = intent.getStringExtra("image");
        rname = intent.getStringExtra("title");

        //显示数据
        String imgurl = AppNetConfig.GetImageUrl+ rimage;
        tvName.setText(rname);
        show.setUp(rurl, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,rname);
        Glide.with(this).load(imgurl).into(show.thumbImageView);
//        show.thumbImageView.setImageURI(Uri.parse(imgurl));
//        show.startVideo();

    }

    //重新计算Listview的高度
    private void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }

    @OnClick(R.id.sixin)
    public void onClick() {
    }
}
