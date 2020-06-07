package com.example.studyinfo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studyinfo.Entity.Mywords;
import com.example.studyinfo.Entity.Words;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.DBHelper;

import java.util.ArrayList;
import java.util.List;

public class FuxiAdapter extends BaseAdapter {

    private List<Mywords> datas = new ArrayList<Mywords>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private LayoutInflater layoutInflater2;
    private TextView textView;

    public FuxiAdapter(Context context, List<Mywords> datas) {
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
            convertView = layoutInflater.inflate(R.layout.fuxishow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag(), position);
        return convertView;

    }

    @SuppressLint("SetTextI18n")
    private void initViews(Mywords data, ViewHolder holder, int position) {//初始化数据

        SharedPreferences preferences = context.getSharedPreferences("UserName", Context.MODE_PRIVATE);
        String suser = preferences.getString("name", null);
        holder.tvName.setText(data.getName()); //Name
        holder.tv_yinbiao.setText(data.getYinbiao());
        holder.tv_mean.setText("点我查看翻译");
        holder.tv_mean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tv_mean.setText(data.getMean());
            }
        });
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
        holder.renshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        holder.renshi.setAlpha((float) 0.6);
//                        DBHelper.UpdateMyWords(data.getName(),suser);
                        Looper.prepare();
                        Toast.makeText(context,"认识",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
            }
        });
        holder.burenshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        holder.burenshi.setAlpha((float) 0.6);
//                        DBHelper.InsertMyWord(data.getName(),data.getYinbiao(),data.getMean(),"0",data.getType(),data.instance,suser);
                        Looper.prepare();
                        Toast.makeText(context,"不认识",Toast.LENGTH_SHORT).show();
                        Looper.loop();
                    }
                }).start();
            }
        });

    }
    protected class ViewHolder {
        private ImageView bofang;
        private TextView tvName, tv_yinbiao,tv_mean,renshi,burenshi;

        public ViewHolder(View view) {
            tvName = (TextView) view.findViewById(R.id.tv_name);
            tv_yinbiao = (TextView) view.findViewById(R.id.tv_yinbiao);
            bofang = (ImageView) view.findViewById(R.id.bofang);
            tv_mean = (TextView) view.findViewById(R.id.tv_mean);
            renshi = (TextView) view.findViewById(R.id.renshi);
            burenshi = (TextView) view.findViewById(R.id.burenshi);
        }
    }
}