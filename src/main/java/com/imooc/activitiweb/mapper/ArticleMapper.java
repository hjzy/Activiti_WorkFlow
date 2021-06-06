package com.imooc.activitiweb.mapper;

import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.pojo.Count;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */
@Mapper
public interface ArticleMapper {

    public int insertArticle(Article article);


    public Article getArticleById(int id);

    List<Article> getAllArticle();

    List<Article> searchArticle(String titleStr);

    public int deleteArticle(int id);

    public int updateArticle(Article article);

    int updateClick(int id);

    List<Article> getArticleAndClick();

    int insertArticleFileID(String articleId, String fileName, String fileOriginName);

    List<HashMap<String, String>> getAttachments(int id);
}
