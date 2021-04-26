package com.imooc.activitiweb.pojo;

import lombok.Data;

@Data
public class Article {

    private int id;

    private String title;

    private String author;

    private String content;

    private int type;

    private String ArticleViewUrl;

    private String ArticleDeleteUrl;

    private String ArticleEditUrl;

    private String TypeName;
    public String getTypeName()
    {

        switch (type)
        {
            case 0:
                TypeName="测试用文章";
                break;
            case 1:
                TypeName="壮学研究";
                break;
            case 2:
                TypeName="壮乡见闻";
                break;
            case 3:
                TypeName="文化艺术";
                break;
            case 4:
                TypeName="历史印记";
                break;
            case 5:
                TypeName="民风民俗";
                break;
            case 6:
                TypeName="壮学文档";
                break;
            case 7:
                TypeName="民族政策";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return TypeName;
    }

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


    public String getArticleViewUrl() {
        ArticleViewUrl = "/article/view/" + id;
        return ArticleViewUrl;
    }

    public String getArticleDeleteUrl() {
        ArticleDeleteUrl = "/article/delete/" + id;
        return ArticleDeleteUrl;
    }

    public String getArticleEditUrl() {
        ArticleEditUrl = "/article/toEdit/" + id;
        return ArticleEditUrl;
    }

}