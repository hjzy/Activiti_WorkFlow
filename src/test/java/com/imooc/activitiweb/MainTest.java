package com.imooc.activitiweb;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson.JSONObject;
import com.imooc.activitiweb.listener.ExcelListener;
import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.pojo.Article;
import com.imooc.activitiweb.pojo.UserDataForExcel;
import com.imooc.activitiweb.service.ActivitiService;
import com.imooc.activitiweb.service.ArticleService;

import com.imooc.activitiweb.service.UserService;
import com.imooc.activitiweb.util.GlobalConfig;
import com.imooc.activitiweb.util.MailUtil;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 13:24
 * @email yifan@yifansun.cn
 */
@SpringBootTest
public class MainTest {
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
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    ArticleService articleService;
    @Autowired
    UserService userService;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;

    @Test
    public void main() {
        //mailUtil.SendMail("怡雪","谢谢你","1959243970@qq.com");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        //组装
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            //正文
            helper.setSubject("轶凡，你好~plus");
            helper.setText("<p style='color:red'>谢谢你</p>", true);


            String filePath = GlobalConfig.BPMN_PathMapping + "1cd8b349-0162-470c-9e45-49bb8da4dbf6.bpmn"; // 上传后的路径

            //本地路径格式转上传路径格式
            filePath = filePath.replace("\\", "/");
            filePath = filePath.replace("file:", "");

            //附件
            helper.addAttachment("2.bpmn", new File(filePath));
            //helper.addAttachment("2.jpg", new File(""));

            helper.setTo("1959243970@qq.com");
            helper.setFrom("2540584259@qq.com");

        } catch (MessagingException e) {
            e.printStackTrace();
        }


        javaMailSender.send(mimeMessage);
    }

    @Test
    public void userOut() {
        securityUtil.logInAs("bajie");
        System.out.println(SecurityContextHolder.getContext().getAuthentication().getName());
    }

    @Test
    public void Time() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String ft = formatter.format(date);
        try {
            Date date1 = formatter.parse(ft);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(ft);
    }

    @Test
    public void count() {
        System.out.println(activitiMapper.getCountUsers());
        List<HashMap<String, Object>> listMapCount1 = activitiService.getCountProcessDefinitionCreateProcessInstance();
        System.out.println(listMapCount1);
        int i = activitiMapper.getCountRunningTask();
        System.out.println(i);
    }

    @Test
    public void encode() {

        String userInfo = "{\"username\":\"1700301127\",\"name\":\"孙轶凡\",\"email\":\"yifan.hjzy@gmail.com\",\"isEmail\":\"是\",\"access\":\"教师\"}";
        Map<String, Object> userMap = (Map<String, java.lang.Object>) JSONObject.parse(userInfo);

        String temp = (String) userMap.get("username");
        String tempPassword = temp.substring(4);
        String password = passwordEncoder.encode(tempPassword);
        userMap.put("password", password);
        System.out.println(userMap);
        //int result= userService.addUser(userMap);
        System.out.println(userMap);
        //System.out.println(System.getProperty("user.dir"));
        System.out.println(passwordEncoder.matches("301127", password));
    }

    @Test
    public void click() {
        List<HashMap<String, Object>> clickListMap = new ArrayList<HashMap<String, Object>>();
        ;
        List<Article> articleList = articleService.getClick();

        for (Article article : articleList) {
            HashMap<String, Object> click = new HashMap<>();
            click.put(article.getTitle(), article.getClick());
            clickListMap.add(click);
        }
        System.out.println(clickListMap);
    }

    @Test
    public void charts() {
        List<HashMap<String, Object>> hashMapListTask = activitiService.getCountListTask();
        Collections.reverse(hashMapListTask);
        for (HashMap<String, Object> stringObjectHashMap : hashMapListTask) {
            System.out.println(stringObjectHashMap);
        }
    }

    @Test
    public void excel() {
        String filename = "F:\\Develop_Project\\user.xlsx";
        File file = new File(filename);

        //EasyExcel.read(in, UserDataForExcel.class,new ExcelListener(userService)).sheet().doRead();
        //EasyExcel.read(filename, UserDataForExcel.class,new ExcelListener(userService)).sheet().doRead();
    }

    @Test
    public void user() {
        BpmnModel model = repositoryService.getBpmnModel("Process_1_UEL_Test1:1:faa6ce34-b006-11eb-9237-001a7dda7111");
        Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
        HashMap<String,String> userMap=new HashMap<>();
        for (FlowElement e : flowElements) {
            String clazz = e.getClass().toString();
            if(clazz.endsWith("UserTask")){
                UserTask userTask = (UserTask) repositoryService.getBpmnModel("Process_1_UEL_Test1:1:faa6ce34-b006-11eb-9237-001a7dda7111")
                        .getFlowElement(e.getId());
                if(userTask.getAssignee().contains("${")){
                    String username=userTask.getAssignee();
                    username= username.substring(2,username.length()-1);
                    userMap.put(e.getName(),username);
                    System.out.println(userMap);
                }
            }

        }
    }

}
