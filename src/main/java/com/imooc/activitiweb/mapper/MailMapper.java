package com.imooc.activitiweb.mapper;

import com.imooc.activitiweb.pojo.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/27 22:46
 * @email yifan@yifansun.cn
 */
@Mapper
public interface MailMapper {
    public List<UserInfoBean> getSubscribeUser();
}
