package com.example.studyinfo.Entity;

import java.io.Serializable;

/**
 * (Movie)实体类
 *
 * @author makejava
 * @since 2020-05-13 18:26:09
 */
public class Movie {
    /**
    * 编号
    */
    public String id;
    /**
    * 标题
    */
    public String title;
    /**
    * 地址
    */
    public String vurl;
    /**
    * 图片
    */
    public String image;


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

}