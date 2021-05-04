package com.imooc.activitiweb;

import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.service.ActivitiService;
import com.imooc.activitiweb.util.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

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

    @Autowired
    MailUtil mailUtil;

    @Autowired
    SecurityUtil securityUtil;

    @Autowired
    ActivitiService activitiService;

    @Autowired
    ActivitiMapper activitiMapper;
    @Test
    public void main() {
        mailUtil.SendMail("怡雪","谢谢你","1959243970@qq.com");
    }
    @Test
    public void userOut(){
        securityUtil.logInAs("bajie");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
    }
    @Test
    public void Time(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String ft=formatter.format(date);
        System.out.println(ft);
    }

    @Test
    public  void count() {
        System.out.println(activitiMapper.getCountUsers());
        List<HashMap<String,Object>> listMapCount1 =activitiService.getCountProcessDefinitionCreateProcessInstance();
        System.out.println(listMapCount1);
        int i=activitiMapper.getCountRunningTask();
        System.out.println(i);
    }
}
