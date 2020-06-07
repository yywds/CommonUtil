package com.example.studyinfo.Entity;

import java.io.Serializable;

/**
 * (Mywords)实体类
 *
 * @author makejava
 * @since 2020-05-13 12:58:40
 */
public class Mywords {

    /**
    * 编号
    */
    public String id;
    /**
    * 单词
    */
    public String name;
    /**
    * 音标
    */
    public String yinbiao;
    /**
    * 翻译
    */
    public String mean;
    /**
    * 状态
    */
    public String status;
    /**
    * 类型
    */
    public String type;
    /**
     * 类型
     */
    public String instance;
    /**
    * 用户名
    */
    public String username;
    /**
     * 时间
     */
    public String time;
    /**
     * 单词复习状态
     */
    public String fuxistatus;
    /**
     * 生词复习状态
     */
    public String shengcistatus;


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

    public String getYinbiao() {
        return yinbiao;
    }

    public void setYinbiao(String yinbiao) {
        this.yinbiao = yinbiao;
    }

    public String getMean() {
        return mean;
    }

    public void setMean(String mean) {
        this.mean = mean;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getFuxistatus() {
        return fuxistatus;
    }

    public void setFuxistatus(String fuxistatus) {
        this.fuxistatus = fuxistatus;
    }

    public String getShengcistatus() {
        return shengcistatus;
    }

    public void setShengcistatus(String shengcistatus) {
        this.shengcistatus = shengcistatus;
    }
}