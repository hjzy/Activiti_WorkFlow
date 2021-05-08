package com.imooc.activitiweb.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.pojo.PageHelperInfo;
import com.imooc.activitiweb.service.ArticleService;
import com.imooc.activitiweb.service.impl.ArticleServiceImpl;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.FileUtils;
import com.imooc.activitiweb.util.GlobalConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Api
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    public ArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }


    //提交文章
    @RequestMapping("/publish")
    @ResponseBody
    public String publishArticle(Article article) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String ft = formatter.format(date);
        article.setDate(ft);
        boolean res = articleService.publishArticle(article);
        if (res) {
            return "success";
        }
        return "false";
    }

    //markdown图片上传控制器
    @RequestMapping("/image/upload")
    @ResponseBody
    public JSONObject imageUpload(@RequestParam("editormd-image-file") MultipartFile image) {
        JSONObject jsonObject = new JSONObject();
        if (image != null) {
            String path = FileUtils.uploadFile(image);
            System.out.println(path);
            jsonObject.put("url", path);
            jsonObject.put("success", 1);
            jsonObject.put("message", "upload success!");
            return jsonObject;
        }
        jsonObject.put("success", 0);
        jsonObject.put("message", "upload error!");
        return jsonObject;
    }

    //获取文章并直接显示
    @RequestMapping("/get/{id}")
    public ModelAndView getArticleById(@PathVariable(name = "id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        Article article = articleService.getArticleById(id);
        modelAndView.setViewName("article");
        if (article == null) {
            modelAndView.addObject("article", new Article());
        }
        modelAndView.addObject("article", article);
        return modelAndView;
    }

    //在视图中显示图文内容
    @RequestMapping("/view/{id}")
    public ModelAndView viewArticle(@PathVariable(name = "id") int id, HttpServletRequest request, HttpServletResponse response) {
        //String announceNum = request.getParameter("num");
        ModelAndView modelAndView = new ModelAndView();

        Article article = articleService.getArticleById(id);
        if (article == null) {
            //modelAndView.addObject("article", new Article());
            modelAndView.setViewName("404");
        } else {
            modelAndView.setViewName("article-view");
            modelAndView.addObject("article", article);
        }
        return modelAndView;
    }


    //在视图中显示所有文章，并进行分页
    @ResponseBody
    @RequestMapping("/announcementIndex")
    public AjaxResponse announcementIndex(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit) {

        try {
            PageHelper.startPage(page, limit);
            List<Article> articleList = articleService.getAllArticle();
            PageInfo pageInfo = new PageInfo(articleList);
            Integer count = Math.toIntExact(pageInfo.getTotal());


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), PageHelperInfo.ReturnInfo(pageInfo.getList(), count));
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "error", e.toString());
        }
    }

    @ResponseBody
    @RequestMapping("/announcementSearchIndex")
    public AjaxResponse announcementSearchIndex(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit,String titleStr) {

        try {
            PageHelper.startPage(page, limit);
            //分页查询
            List<Article> articleList = articleService.searchArticle(titleStr);
            PageInfo<Article> pageInfo = new PageInfo(articleList);
            Integer count = Math.toIntExact(pageInfo.getTotal());


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), PageHelperInfo.ReturnInfo(pageInfo.getList(), count));
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "error", e.toString());
        }
    }


    //删除
    @RequestMapping("/delete")
    @ResponseBody
    public AjaxResponse deleteArticleById(int id) {

        try {
            boolean res = articleService.deleteArticleById(id);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), "完成");
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "error", e.toString());
        }
    }

    @RequestMapping("/toEdit/{id}")
    public ModelAndView toEditArticle(@PathVariable(name = "id") int id, HttpServletRequest request, HttpServletResponse response) {
        //String announceNum = request.getParameter("num");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article-edit");
        Article article = articleService.getArticleById(id);
        if (article == null) {
            modelAndView.addObject("article", new Article());
        }
        modelAndView.addObject("article", article);
        return modelAndView;
    }

    @RequestMapping("/edit")
    public String updateArticle(Article article) {
        boolean res = articleService.updateArticleById(article);
        return "redirect:/article/page";
    }


}
