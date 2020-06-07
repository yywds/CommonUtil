package com.example.studyinfo.Entity;

/**
 * (Meiwen)实体类
 *
 * @author makejava
 * @since 2020-05-13 12:58:39
 */
public class Meiwen  {
    /**
    * 编号
    */
    private String id;
    /**
    * 标题
    */
    private String title;
    /**
    * 图片
    */
    private String image;
    /**
    * 内容
    */
    private String content;


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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}