package com.example.studyinfo.Entity;

import java.io.Serializable;

/**
 * (Kecheng)实体类
 *
 * @author makejava
 * @since 2020-05-13 16:25:36
 */
public class Kecheng {
    /**
    * 编号
    */
    public String id;
    /**
    * 视频标题
    */
    public String title;
    /**
    * 视频地址
    */
    public String vurl;
    /**
    * 视频页面
    */
    public String image;
    /**
    * 类型
    */
    public String type;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVurl() {
        return vurl;
    }

    public void setVurl(String vurl) {
        this.vurl = vurl;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}