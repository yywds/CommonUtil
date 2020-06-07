package com.example.studyinfo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studyinfo.Entity.Mywords;
import com.example.studyinfo.Entity.Words;
import com.example.studyinfo.R;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ShengcibenAdapter extends BaseAdapter {

    private List<Mywords> datas = new ArrayList<Mywords>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private LayoutInflater layoutInflater2;
    private TextView textView;
    private MediaPlayer mp;

    public ShengcibenAdapter(Context context, List<Mywords> datas) {
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
    public Mywords getItem(int position) {
        return datas.get(position); //通过列表的位置 获得集合中的对象
    }

    @Override
    public long getItemId(int position) { // 获得集合的Item的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.shengcishow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;

    }

    @SuppressLint("SetTextI18n")
    private void initViews(Mywords data, ViewHolder holder, int position) {

        holder.tv_name.setText(data.getName()); //Name
        holder.tv_yinbiao.setText("美  "+data.getYinbiao());
        holder.tv_mean.setText(data.getMean()); //

    }
    protected class ViewHolder {
        private ImageView bofang;
        private TextView tv_name, tv_type,tv_instance,tv_mean,tv_yinbiao;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_yinbiao = (TextView) view.findViewById(R.id.tv_yinbiao);
            tv_mean = (TextView) view.findViewById(R.id.tv_mean);
        }
    }


    /**
     * 文字颜色变化
     * @param text
     * @param keyword
     * @return
     */
    public SpannableString matcherSearchText(String text, String keyword) {
        SpannableString ss = new SpannableString(text);
        Pattern pattern = Pattern.compile(keyword);
        Matcher matcher = pattern.matcher(ss);
        while (matcher.find()) {
            int start = matcher.start();
            int end = matcher.end();
            ss.setSpan(new TextAppearanceSpan(context,R.style.style_color_FA9A3A), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);//new ForegroundColorSpan(color)
        }
        return ss;
    }


}