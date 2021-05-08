package com.imooc.activitiweb.service;

import com.imooc.activitiweb.pojo.UserInfoBean;
import org.apache.ibatis.annotations.Param;

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
public interface UserService {

    UserInfoBean selectByUsername(@Param("username") String username);

    List<HashMap<String, Object>> getAllUser();

    int deleteUser(Integer id);

    List<HashMap<String, Object>> getUserInfoByUsername(String username);

    int updateUser(Map userInfo);

    int addUser(Map<String, Object> map);

    int resetPassword(Map<String, Object> map);
}
