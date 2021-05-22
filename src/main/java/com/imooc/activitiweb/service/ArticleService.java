package com.imooc.activitiweb.service;

import com.imooc.activitiweb.mapper.ArticleMapper;
import com.imooc.activitiweb.pojo.Article;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 21:48
 * @email yifan@yifansun.cn
 */
public interface ArticleService {

    public boolean publishArticle(Article article);

    public Article getArticleById(int id);

    public List<Article> getAllArticle();

    public List<Article> searchArticle(String titleStr);

    public boolean deleteArticleById(int id);

    public boolean updateArticleById(Article article);

    public int updateClick(int id);

    public List<Article> getClick();

    int insertArticleFileID(String articleId,String fileName,String fileOriginName);
}
