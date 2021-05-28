package com.imooc.activitiweb.service.impl;

import com.imooc.activitiweb.mapper.UserInfoBeanMapper;
import com.imooc.activitiweb.pojo.UserDataForExcel;
import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/5 17:46
 * @email yifan@yifansun.cn
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserInfoBeanMapper userInfoBeanMapper;

    @Override
    public UserInfoBean selectByUsername(String username) {
        return userInfoBeanMapper.selectByUsername(username);
    }

    @Override
    public List<HashMap<String, Object>> getAllUser() {
        return userInfoBeanMapper.getAllUsers();
    }

    @Override
    public int deleteUser(Integer id) {
        return userInfoBeanMapper.deleteUser(id);
    }

    @Override
    public List<HashMap<String, Object>> getUserInfoByUsername(String username) {
        return userInfoBeanMapper.getUserPartInfoByUsername(username);
    }

    @Override
    public int updateUser(Map userInfo) {
        return userInfoBeanMapper.updateUserInfo(userInfo);
    }

    @Override
    public int addUser(Map<String, Object> map) {
        return userInfoBeanMapper.addUser(map);
    }

    @Override
    public int resetPassword(Map<String, Object> map) {
        return userInfoBeanMapper.resetPassword(map);
    }

    @Override
    public int saveUser(List<UserDataForExcel> userDataForExcelList) {
//        for (UserDataForExcel data : userDataForExcelList) {
//            HashMap<String,Object> userMap=new  HashMap<>();
//            userMap.put("name",data.getName());
//            userMap.put("username",data.getUsername());
//            userMap.put("password",data.getPassword());
//            userMap.put("access",data.getRoles());
//            userMap.put("email",data.getEmail());
//            userMap.put("isEmail",data.getIs_subscribe());
//            this.addUser(userMap);
//            System.out.println(userMap);
//        }

        return userInfoBeanMapper.addUserList(userDataForExcelList);
    }
}
