package com.imooc.activitiweb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 13:24
 * @email yifan@yifansun.cn
 */
@SpringBootTest
public class MailTest {
    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Test
    @Async
    public void main() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("覃小姐");
        mailMessage.setText("谢谢你");
        mailMessage.setTo("361157542@qq.com");
        mailMessage.setFrom("2540584259@qq.com");
        javaMailSender.send(mailMessage);
    }
}
