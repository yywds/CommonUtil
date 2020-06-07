package com.example.studyinfo.Entity;

import java.io.Serializable;

/**
 * (Users)实体类
 *
 * @author makejava
 * @since 2020-05-13 12:58:40
 */
public class Users{
    /**
    * 编号
    */
    public String id;
    /**
    * 用户名
    */
    public String name;
    /**
    * 密码
    */
    public String password;
    /**
    * 手机号
    */
    public String phone;
    /**
    * 个性签名
    */
    public String nickname;
    /**
    * 头像
    */
    public String image;


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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}