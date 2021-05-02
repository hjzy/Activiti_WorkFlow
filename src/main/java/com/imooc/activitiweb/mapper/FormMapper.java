package com.imooc.activitiweb.mapper;

import com.imooc.activitiweb.pojo.FormData;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/1 22:16
 * @email yifan@yifansun.cn
 */
@Mapper
public interface FormMapper {
    List<FormData> getFormDataList(String pro_ins_id);
    List<FormData> getFormDataByProcessInstanceIdAndTaskDefinitionKey(String pro_ins_id,String task_definition_key);
}
