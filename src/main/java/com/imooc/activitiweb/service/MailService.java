package com.imooc.activitiweb.service;

import com.imooc.activitiweb.pojo.Article;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 21:58
 * @email yifan@yifansun.cn
 */
public interface MailService {
    public String SendMailByIsSubscribe();

    public String SendMail(String subject, String text, String to);

    String SendMailToSubscribedUser(Article article);
}
