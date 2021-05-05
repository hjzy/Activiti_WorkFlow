package com.imooc.activitiweb.service;

import com.imooc.activitiweb.pojo.UserInfoBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/5 17:46
 * @email yifan@yifansun.cn
 */
public interface UserService {
    List<HashMap<String,Object>> getAllUser();
}
