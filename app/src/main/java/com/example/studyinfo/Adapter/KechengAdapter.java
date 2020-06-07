package com.example.studyinfo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studyinfo.Entity.Kecheng;
import com.example.studyinfo.Entity.Meiwen;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.shehuan.niv.NiceImageView;

import java.util.ArrayList;
import java.util.List;

public class KechengAdapter extends BaseAdapter {

    private List<Kecheng> datas = new ArrayList<Kecheng>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private LayoutInflater layoutInflater2;
    private TextView textView;

    public KechengAdapter(Context context, List<Kecheng> datas) {
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
//        this.layoutInflater2 = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size(); //返回列表的长度
    }

    @Override
    public Kecheng getItem(int position) {
        return datas.get(position); //通过列表的位置 获得集合中的对象
    }

    @Override
    public long getItemId(int position) { // 获得集合的Item的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.kechengshow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;

    }

    @SuppressLint("SetTextI18n")
    private void initViews(Kecheng data, ViewHolder holder, int position) {//初始化数据
        System.out.println(AppNetConfig.GetImageUrl + data.getImage());
        //Glide加载图片解决图片过多内存溢出问题
        Glide.with(context).load(AppNetConfig.GetImageUrl + data.getImage()).into(holder.ivImg);
        holder.tvName.setText(data.getTitle()); //Name
        holder.tvMiaoshu.setText("类型：" + data.getType()); //
    }
    protected class ViewHolder {
        private NiceImageView ivImg;
        private TextView tvName, tvMiaoshu;

        public ViewHolder(View view) {
            ivImg = (NiceImageView) view.findViewById(R.id.tt);
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvMiaoshu = (TextView) view.findViewById(R.id.tv_type);
        }
    }
}