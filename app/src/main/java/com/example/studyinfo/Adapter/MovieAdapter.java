package com.example.studyinfo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.example.studyinfo.Entity.Movie;
import com.example.studyinfo.R;
import com.example.studyinfo.Utils.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

import cn.jzvd.JZVideoPlayerStandard;

import static com.kongzue.dialog.v2.Notification.TYPE_ERROR;

public class MovieAdapter extends BaseAdapter {

    private List<Movie> datas = new ArrayList<Movie>();//新闻列表集合

    private Context context;
    private LayoutInflater layoutInflater;
    private int notifactionType = TYPE_ERROR;

    public MovieAdapter(Context context, List<Movie> datas) {
        this.datas = datas;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datas.size(); //返回列表的长度
    }

    @Override
    public Movie getItem(int position) {
        return datas.get(position); //通过列表的位置 获得集合中的对象
    }

    @Override
    public long getItemId(int position) { // 获得集合的Item的位置
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.movieshow, null);//找到布局文件
            convertView.setTag(new ViewHolder(convertView));
        }
        initViews(getItem(position), (ViewHolder) convertView.getTag());
        return convertView;

    }

    private void initViews(Movie data, ViewHolder holder) {

       holder.jzVideoPlayerStandard.setUp(data.getVurl(), JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, data.getTitle());
       Glide.with(context).load(AppNetConfig.GetImageUrl + data.image).into(holder.jzVideoPlayerStandard.thumbImageView);
//       holder.jzVideoPlayerStandard.thumbImageView.setImageURI(Uri.parse(data.getCoverImg()));
//       holder.jzVideoPlayerStandard.startVideo();
    }

    protected class ViewHolder {
        private JZVideoPlayerStandard jzVideoPlayerStandard;

        public ViewHolder(View view) {
            jzVideoPlayerStandard = (JZVideoPlayerStandard) view.findViewById(R.id.show);

        }
    }
}