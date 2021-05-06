package com.imooc.activitiweb.util;

import lombok.Builder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 18:51
 * @email yifan@yifansun.cn
 */
@Component
public class MailUtil {
    @Autowired
    JavaMailSenderImpl javaMailSender;

    @Async
    public void SendMail(String subject, String text, String to) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setSubject(subject);
            mailMessage.setText(text);
            mailMessage.setTo(to);
            mailMessage.setFrom("2540584259@qq.com");
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
