package com.imooc.activitiweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/1 22:13
 * @email yifan@yifansun.cn
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormData {
    private String PROC_DEF_ID_;
    private String PROC_INST_ID_;
    private String FORM_KEY_;
    private String Control_ID_;
    private String Control_Is_Param_;
    private String Control_Label;
    private String Control_VALUE_;
    private String Control_Type;

}
