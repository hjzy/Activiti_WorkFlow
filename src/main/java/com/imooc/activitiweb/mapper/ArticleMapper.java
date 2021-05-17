package com.imooc.activitiweb.mapper;

import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.pojo.Count;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ArticleMapper {

    public int insertArticle(Article article);


    public Article getArticleById(int id);

    List<Article> getAllArticle();

    List<Article> searchArticle(String titleStr);

    public int deleteArticle(int id);

    public int updateArticle(Article article);

    public List<Article> getArticleByType(int type);

    public List<Article> getArticleByTypeLimitSize(int type, int size);


    @MapKey("type")
    Map<Integer, Count> getArticleCountMapForEcharts();

}
