package com.imooc.activitiweb.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.activitiweb.mapper.MailMapper;
import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.pojo.PageHelperInfo;
import com.imooc.activitiweb.service.ArticleService;
import com.imooc.activitiweb.service.MailService;
import com.imooc.activitiweb.service.UserService;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Api
@Controller
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    MailService mailService;

    @Autowired
    public ArticleController(ArticleServiceImpl articleService) {
        this.articleService = articleService;
    }


    //提交文章
    @RequestMapping("/publish")
    @ResponseBody
    public AjaxResponse publishArticle(Article article) {
        try {
            boolean res = articleService.publishArticle(article);
            mailService.SendMailToSubscribedUser(article);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), "完成");
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "添加文章失败", e.toString());
        }
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

    //文件上传控制器
    @RequestMapping("/file/upload")
    @ResponseBody
    public JSONObject fileUpload( MultipartFile file) {
        JSONObject jsonObject = new JSONObject();
        if (file != null) {
            String path = FileUtils.uploadFile(file);

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

    //插入文章附件id
    @RequestMapping("/insertArticleFileId")
    @ResponseBody
    public AjaxResponse insertArticleFileId(String fileName, String articleId,String fileOriginName) {

        try {
            int result= articleService.insertArticleFileID(fileName,articleId,fileOriginName);

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), "完成");
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "插入文章附件id失败！", e.toString());
        }
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
    public AjaxResponse announcementSearchIndex(@RequestParam("page") Integer page, @RequestParam("limit") Integer limit, String titleStr) {

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

    @RequestMapping("/updateClick")
    @ResponseBody
    public AjaxResponse updateClick(int id) {

        try {
            int res = articleService.updateClick(id);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), "完成");
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "点击量添加失败", e.toString());
        }
    }



}
