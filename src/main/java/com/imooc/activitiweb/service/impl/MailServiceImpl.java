package com.imooc.activitiweb.service.impl;

import com.imooc.activitiweb.mapper.MailMapper;
import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.service.MailService;
import com.imooc.activitiweb.util.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 22:00
 * @email yifan@yifansun.cn
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    MailUtil mailUtil;

    @Autowired
    MailMapper mailMapper;


    @Override
    public String SendMailByIsSubscribe() {
        return null;
    }

    @Override
    public String SendMail(String subject, String text, String to) {
        mailUtil.SendMail("怡雪", "谢谢你", "19592439 70@qq.com");
        return null;
    }

    @Override
    public String SendMailToSubscribedUser(String deploymentName) {
        List<UserInfoBean> userInfoBeanList = mailMapper.getSubscribeUser();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String time = df.format(new Date());// new Date()为获取当前系统时间
        for (UserInfoBean bean : userInfoBeanList) {
            String to = bean.getEmail();
            String subject = "【通知】管理员发布了新的项目！";
            String context = "新项目名称：" + deploymentName + "\n"
                    + "发布时间：" + time;
            mailUtil.SendMail(subject, context, to);
        }
        return null;
    }
}
