package com.example.studyinfo.Entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Comment)实体类
 *
 * @author makejava
 * @since 2020-05-13 13:00:20
 */
public class Comment{
    /**
    * 编号
    */
    public String id;
    /**
    * 用户名
    */
    public String name;
    /**
    * 评论内容
    */
    public String content;
    /**
    * 评论时间
    */
    public Date time;
    /**
    * 标题
    */
    public String title;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}