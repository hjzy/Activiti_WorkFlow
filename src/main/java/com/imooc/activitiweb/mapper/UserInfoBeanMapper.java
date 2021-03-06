package com.imooc.activitiweb.mapper;


import com.imooc.activitiweb.pojo.UserDataForExcel;
import com.imooc.activitiweb.pojo.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */
@Mapper
public interface UserInfoBeanMapper {
    /**
     * 从数据库中查询用户
     *
     * @param username
     * @return
     */

    UserInfoBean selectByUsername(@Param("username") String username);

    List<HashMap<String, Object>> getAllUsers();

    int deleteUser(Integer id);

    List<HashMap<String, Object>> getUserPartInfoByUsername(String username);

    int updateUserInfo(Map map);

    int addUser(Map<String, Object> map);

    int resetPassword(Map<String, Object> map);

    int addUserList(List<UserDataForExcel> userDataForExcelList);
}
