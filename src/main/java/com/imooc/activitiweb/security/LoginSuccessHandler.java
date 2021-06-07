package com.imooc.activitiweb.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imooc.activitiweb.SecurityUtil;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */
@Component("loginSuccessHandler")
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    SecurityUtil securityUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功1");

    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        logger.info("登录成功2");

        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        System.out.println(roles);
        securityUtil.logInAs(authentication.getName());
        String path = httpServletRequest.getContextPath();

        String basePath = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName() + ":" + httpServletRequest.getServerPort() + path + "/";
        httpServletResponse.setContentType("application/json;charset=UTF-8");
        HashMap<String, String> authMap = new HashMap<>();
        if (roles.contains("ROLE_ACTIVITI_ADMIN")) {
            authMap.put("userRole", "admin");
        } else if (roles.contains("ROLE_ACTIVITI_USER")) {
            authMap.put("userRole", "user");
        } else if (roles.contains("ROLE_ACTIVITI_EXPERT")) {
            authMap.put("userRole", "expert");
        }
        authMap.put("userName", authentication.getName());
        System.out.println(authMap);
        httpServletResponse.getWriter().write(objectMapper.writeValueAsString(
                AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                        GlobalConfig.ResponseCode.SUCCESS.getDesc(),
                        authMap
                )));
    }
}
