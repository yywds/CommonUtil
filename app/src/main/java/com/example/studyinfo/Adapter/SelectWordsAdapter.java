package com.example.studyinfo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.studyinfo.Entity.Words;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;
import com.loopj.android.http.AsyncHttpClient;
import com.shehuan.niv.NiceImageView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SelectWordsAdapter extends BaseAdapter {

    private List<Words> datas = new ArrayList<Words>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private LayoutInflater layoutInflater2;
    private TextView textView;
    private MediaPlayer mp;

    public SelectWordsAdapter(Context context, List<Words> datas) {
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
    public Words getItem(int position) {
        return datas.get(position); //通过列表的位置 获得集合中的对象
    }

    @Override
    public long getItemId(int position) { // 获得集合的Item的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.selectwordsshow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;

    }

    @SuppressLint("SetTextI18n")
    private void initViews(Words data, ViewHolder holder, int position) {
//        //初始化数据
//        String str = data.getInstance();
//        boolean status = str.contains(data.getName());
//        if (status){
//
//
//            String before = str.substring(0,str.indexOf(data.getName()));
//            String after = str.substring(before.length()+1,str.length());
//            holder.tv_instance.setText(before+wenzi+after);
//
//        }
        holder.tv_instance.setText(matcherSearchText(data.instance,data.getName()));
        holder.tv_name.setText(data.getName()); //Name
        holder.tv_type.setText(data.getType()); //
        holder.tv_yinbiao.setText("美    "+data.getYinbiao());
        holder.tv_mean.setText(data.getMean()); //

        holder.bofang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String voiceurl =  "http://media.shanbay.com/audio/us/";//发音地址
                MediaPlayer mediaPlayer = MediaPlayer.create(context, Uri.parse( voiceurl+data.getName()+".mp3"));
                mediaPlayer.start();//开始播放

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mediaPlayer.release();//释放
                    }
                });

            }
        });
    }
    protected class ViewHolder {
        private ImageView bofang;
        private TextView tv_name, tv_type,tv_instance,tv_mean,tv_yinbiao;

        public ViewHolder(View view) {
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_type = (TextView) view.findViewById(R.id.tv_type);
            tv_mean = (TextView) view.findViewById(R.id.tv_mean);
            tv_yinbiao = (TextView) view.findViewById(R.id.tv_yinbiao);
            tv_instance = (TextView) view.findViewById(R.id.tv_instance);
            bofang = (ImageView) view.findViewById(R.id.bofang);
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