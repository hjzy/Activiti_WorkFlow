package com.imooc.activitiweb.service.impl;

import com.imooc.activitiweb.mapper.ArticleMapper;
import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {


    private final ArticleMapper articleMapper;

    @Autowired
    public ArticleServiceImpl(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    @Override
    public boolean publishArticle(Article article) {
        int res = articleMapper.insertArticle(article);
        if (res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public Article getArticleById(int id) {
        return articleMapper.getArticleById(id);
    }

    @Override
    public List<Article> getAllArticle() {
        return articleMapper.getAllArticle();
    }

    @Override
    public List<Article> searchArticle(String titleStr) {
        return articleMapper.searchArticle(titleStr);
    }

    @Override
    public boolean deleteArticleById(int id) {
        int res = articleMapper.deleteArticle(id);
        if (res > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateArticleById(Article article) {
        int res = articleMapper.updateArticle(article);
        return res > 0;
    }

    @Override
    public int updateClick(int id) {
        return articleMapper.updateClick(id);
    }

    @Override
    public List<Article> getClick() {
        return articleMapper.getArticleAndClick();
    }

}
