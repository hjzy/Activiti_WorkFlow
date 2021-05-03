package com.imooc.activitiweb.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class Article {

    private int id;

    private String title;

    private String author;

    private String content;

    private int type;

    private String date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }




}