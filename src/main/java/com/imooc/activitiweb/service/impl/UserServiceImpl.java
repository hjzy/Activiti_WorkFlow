package com.imooc.activitiweb.service.impl;

import com.imooc.activitiweb.mapper.UserInfoBeanMapper;
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
public  class UserServiceImpl implements UserService {
    @Autowired
    UserInfoBeanMapper userInfoBeanMapper;

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
}
