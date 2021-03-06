package com.imooc.activitiweb.security;


import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.mapper.UserInfoBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserInfoBeanMapper userInfoBeanMapper;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserInfoBean userInfoBean = userInfoBeanMapper.selectByUsername(username);
        if (userInfoBean == null) {
            throw new UsernameNotFoundException("数据库中无此用户！");
        }
        return userInfoBean;
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}