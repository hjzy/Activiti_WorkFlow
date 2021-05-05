package com.imooc.activitiweb.service.impl;

import com.imooc.activitiweb.mapper.UserInfoBeanMapper;
import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

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
    public List<HashMap<String,Object>> getAllUser() {
        return userInfoBeanMapper.getAllUsers();
    }
}
