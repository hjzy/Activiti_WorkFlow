package com.imooc.activitiweb.controller;

import com.alibaba.fastjson.JSONObject;
import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.service.ActivitiService;
import com.imooc.activitiweb.service.UserService;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    ActivitiService activitiService;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    //获取用户
    @GetMapping(value = "/getUsers")
    public AjaxResponse getUsers() {
        try {
            List<HashMap<String, Object>> mapList = userService.getAllUser();


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), mapList);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取用户列表失败", e.toString());
        }
    }

    @RequestMapping("/delete")
    public AjaxResponse deleteUser(Integer id) {
        try {
            int result = userService.deleteUser(id);


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), result);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "删除用户失败", e.toString());
        }
    }

    @RequestMapping("/getUserByUsername")
    public AjaxResponse getUserByUsername(String username) {
        try {

            List<HashMap<String, Object>> mapList = userService.getUserInfoByUsername(username);


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), mapList);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取用户信息失败", e.toString());
        }
    }

    @RequestMapping("/updateUser")
    public AjaxResponse updateUser(String userInfo) {
        try {
            System.out.println(userInfo);
            Map<String, Object> userMap = (Map<String, Object>) JSONObject.parse(userInfo);
            //List<HashMap<String,Object>> mapList = userService.getUserInfoByUsername(username);
            Object access = userMap.get("access");
            if ("教师".equals(access)) {
                userMap.replace("access", "ROLE_ACTIVITI_USER");
            } else if ("专家".equals(access)) {
                userMap.replace("access", "ROLE_ACTIVITI_EXPERT");
            } else if ("管理员".equals(access)) {
                userMap.replace("access", "ROLE_ACTIVITI_ADMIN");
            }
            Object isEmail = userMap.get("isEmail");
            if ("是".equals(isEmail)) {
                userMap.replace("isEmail", 1);
            } else {
                userMap.replace("isEmail", 0);
            }
            userService.updateUser(userMap);
            System.out.println(userMap);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), userMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取用户信息失败", e.toString());
        }
    }

    @RequestMapping("/addUser")
    public AjaxResponse addUser(String userInfo) {
        try {

            System.out.println(userInfo);
            Map<String, Object> userMap = (Map<String, java.lang.Object>)JSONObject.parse(userInfo);
            Object access = userMap.get("access");
            if ("教师".equals(access)) {
                userMap.replace("access", "ROLE_ACTIVITI_USER");
            } else if ("专家".equals(access)) {
                userMap.replace("access", "ROLE_ACTIVITI_EXPERT");
            } else if ("管理员".equals(access)) {
                userMap.replace("access", "ROLE_ACTIVITI_ADMIN");
            }
            Object isEmail = userMap.get("isEmail");
            if ("是".equals(isEmail)) {
                userMap.replace("isEmail", 1);
            } else {
                userMap.replace("isEmail", 0);
            }
            String temp =(String) userMap.get("username");
            String tempPassword= temp.substring(4);
            String password = passwordEncoder.encode(tempPassword);
            userMap.put("password",password);
            int result= userService.addUser(userMap);
            System.out.println(userMap);
            System.out.println(result);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), result);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "添加用户失败", e.toString());
        }
    }
}
