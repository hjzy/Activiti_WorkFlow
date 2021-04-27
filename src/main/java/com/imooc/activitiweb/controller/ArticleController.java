package com.imooc.activitiweb.controller;


import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.service.ArticleService;
import com.imooc.activitiweb.service.impl.ArticleServiceImpl;
import com.imooc.activitiweb.util.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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


    @RequestMapping("/index")
    // @ResponseBody
    @ApiOperation(value = "首页初始化", notes = "")
    public ModelAndView initIndex() {
        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("article-index");

        List<Article> articleList1 = articleService.getArticleByTypeLimitSize(1, 6);
        List<Article> articleList2 = articleService.getArticleByTypeLimitSize(2, 6);
        List<Article> articleList3 = articleService.getArticleByTypeLimitSize(3, 6);
        List<Article> articleList4 = articleService.getArticleByTypeLimitSize(4, 6);
        List<Article> articleList5 = articleService.getArticleByTypeLimitSize(5, 6);
        List<Article> articleList6 = articleService.getArticleByTypeLimitSize(6, 6);
        List<Article> articleList7 = articleService.getArticleByTypeLimitSize(7, 6);


        modelAndView.addObject("articleList1", articleList1);
        modelAndView.addObject("articleList2", articleList2);
        modelAndView.addObject("articleList3", articleList3);
        modelAndView.addObject("articleList4", articleList4);
        modelAndView.addObject("articleList5", articleList5);
        modelAndView.addObject("articleList6", articleList6);
        modelAndView.addObject("articleList7", articleList7);


        return modelAndView;
    }


    //提交文章
    @RequestMapping("/publish")
    @ResponseBody
    public String publishArticle(Article article) {
        boolean res = articleService.publishArticle(article);
        if (res) {
            return "success";
        }
        return "false";
    }

    //    markdown图片上传控制器
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
    @RequestMapping("/page")
    public ModelAndView page(Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article-admin");

        System.out.println("Page Number>>>>>>>>>>>>" + pageNum);
        //引入分页查询，在查询之前获取当前页记录
        PageHelper.startPage(pageNum, pageSize);

        //分页查询
        List<Article> articleList = articleService.getAllArticle();

        //包装查询结果
        PageInfo pageInfo = new PageInfo(articleList, 1);
        pageInfo.setList(articleList);
        //model.addAttribute("sdj1",sdj1);
        //model.addAttribute("pageInfo", pageInfo);
        modelAndView.addObject("pageInfo", pageInfo);
        //model.addAttribute("addUrl", "http://localhost:8081/markdown/toedit");
        modelAndView.addObject("addUrl", "/markdown/toedit");
        //获取当前页
        modelAndView.addObject("pageNum", pageNum);
        //获取一页显示的条
        modelAndView.addObject("pageSize", pageSize);
        //是否为第一页
        modelAndView.addObject("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        modelAndView.addObject("totalPages", pageInfo.getPages());
        //是否为最后一页
        modelAndView.addObject("isLastPage", pageInfo.isIsLastPage());

        return modelAndView;
    }

    //搜索
    @RequestMapping("/search")
    public ModelAndView search(HttpServletRequest request, Model model, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                               @RequestParam(defaultValue = "5") Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("article-admin");
        String titleStr = request.getParameter("titleStr");
        System.out.println("Page Number>>>>>>>>>>>>" + pageNum);
        //引入分页查询，在查询之前获取当前页记录
        PageHelper.startPage(pageNum, pageSize);
        //分页查询
        List<Article> articleList = articleService.searchArticle(titleStr);

        //包装查询结果
        PageInfo pageInfo = new PageInfo(articleList, 1);
        pageInfo.setList(articleList);
        //model.addAttribute("sdj1",sdj1);
        //model.addAttribute("pageInfo", pageInfo);
        modelAndView.addObject("pageInfo", pageInfo);
        //model.addAttribute("addUrl", "http://localhost:8081/markdown/toedit");
        modelAndView.addObject("addUrl", "/markdown/toedit");
        //获取当前页
        modelAndView.addObject("pageNum", pageNum);
        //获取一页显示的条
        modelAndView.addObject("pageSize", pageSize);
        //是否为第一页
        modelAndView.addObject("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        modelAndView.addObject("totalPages", pageInfo.getPages());
        //是否为最后一页
        modelAndView.addObject("isLastPage", pageInfo.isIsLastPage());

        return modelAndView;
    }

    //删除
    @RequestMapping("/delete/{id}")
    //  @ResponseBody
    public String deleteArticleById(@PathVariable(name = "id") int id) {
        // ModelAndView modelAndView = new ModelAndView();
        boolean res = articleService.deleteArticleById(id);
//        if(res) {
//            return "success";
//        }
//        return "false";
        return "redirect:/article/page";
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


    @RequestMapping("/viewByType")
    // @ResponseBody
    public ModelAndView viewByType(@RequestParam(value = "type", defaultValue = "1") Integer type, @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(defaultValue = "5") Integer pageSize) {
        ModelAndView modelAndView = new ModelAndView();
        String typeName = null;
        modelAndView.setViewName("article-view-by-type");

        System.out.println("Page Number>>>>>>>>>>>>" + pageNum);
        //引入分页查询，在查询之前获取当前页记录
        PageHelper.startPage(pageNum, pageSize);

        //分页查询
        List<Article> articleList = articleService.getArticleByType(type);

        //包装查询结果
        PageInfo pageInfo = new PageInfo(articleList, 1);
        pageInfo.setList(articleList);
        //model.addAttribute("sdj1",sdj1);
        //model.addAttribute("pageInfo", pageInfo);
        modelAndView.addObject("pageInfo", pageInfo);
        //model.addAttribute("addUrl", "http://localhost:8081/markdown/toedit");
        // modelAndView.addObject("addUrl", "/markdown/toedit");
        //获取当前页
        modelAndView.addObject("pageNum", pageNum);
        //获取一页显示的条
        modelAndView.addObject("pageSize", pageSize);
        //是否为第一页
        modelAndView.addObject("isFirstPage", pageInfo.isIsFirstPage());
        //获得总页数
        modelAndView.addObject("totalPages", pageInfo.getPages());
        //是否为最后一页
        modelAndView.addObject("isLastPage", pageInfo.isIsLastPage());

        modelAndView.addObject("type", type);
        switch (type) {
            case 1:
                typeName = "壮学研究";
                break;
            case 2:
                typeName = "壮乡见闻";
                break;
            case 3:
                typeName = "文化艺术";
                break;
            case 4:
                typeName = "历史印记";
                break;
            case 5:
                typeName = "民风民俗";
                break;
            case 6:
                typeName = "壮学文档";
                break;
            case 7:
                typeName = "民族政策";
                break;

        }

        modelAndView.addObject("typeName", typeName);
        return modelAndView;
        // return "redirect:/article/page";
    }


//    @RequestMapping("getEchartsPie")
//    public ModelAndView getEchartsPieJson(){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("article-charts");
//        List<Count> charts=articleService.getArticleCountMapForEcharts();
//        JSONArray jsonArray = new JSONArray();
//
//        for(Count c:charts){
//            JSONObject json=new JSONObject();
//            json.put("name",c.getTypeName());
//            json.put("value",c.getCount());
//            jsonArray.put(json);
//        }
//        modelAndView.addObject("json", jsonArray);
//        return modelAndView;
//    }

//    //测试echarts用
//    @RequestMapping("echarts")
//    public ModelAndView getEcharts(Model model){
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("article-echarts-test");
//        String skirt = "裙子";
//        int nums = 30;
//      //  List<Count> charts=articleService.getCountForEcharts();
//        List<Count> charts=articleService.getArticleCountMapForEcharts();
//      //  System.out.println(charts);
//
//        modelAndView.addObject("charts", charts);
//        modelAndView.addObject("nums", nums);
//        return modelAndView;
//    }

}
