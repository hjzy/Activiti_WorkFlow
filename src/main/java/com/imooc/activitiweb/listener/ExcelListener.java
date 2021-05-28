package com.imooc.activitiweb.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.imooc.activitiweb.pojo.UserDataForExcel;

import com.imooc.activitiweb.service.UserService;

import com.imooc.activitiweb.service.impl.UserServiceImpl;
import com.imooc.activitiweb.util.MD5Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/28 19:41
 * @email yifan@yifansun.cn
 */

public class ExcelListener extends AnalysisEventListener<UserDataForExcel> {
    private static final int BATCH_COUNT = 5;

    private UserService userService;
    List<UserDataForExcel> userList = new ArrayList<>();

    public ExcelListener(UserService userService) {
        this.userService = userService;
    }

    //一行一行读取excel内容
    @Override
    public void invoke(UserDataForExcel data, AnalysisContext analysisContext) {
        data.setIs_subscribe(1);
        data.setPassword(PasswordEncoderFactories
                .createDelegatingPasswordEncoder()
                .encode(data.getUsername().substring(4)).substring(8));
        userList.add(data);
        if (userList.size() >= BATCH_COUNT) {
            userService.saveUser(userList);
            // 存储完成清理 list
            userList.clear();
        }
    }

    //读取表头内容
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println("表头：" + headMap);
    }

    //读取完成之后
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        userService.saveUser(userList);
    }


}