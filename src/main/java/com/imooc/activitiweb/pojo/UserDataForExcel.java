package com.imooc.activitiweb.pojo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author yifansun
 * @version 1.0
 * @Description excel参数类
 * @date 2021/5/28 19:43
 * @email yifan@yifansun.cn
 */
@Data
public class UserDataForExcel {
    @ExcelProperty
    private String username;

    @ExcelProperty
    private String name;

    @ExcelProperty
    private String roles;

    @ExcelProperty
    private String email;

    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int is_subscribe;
}
