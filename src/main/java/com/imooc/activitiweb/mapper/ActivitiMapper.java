package com.imooc.activitiweb.mapper;


import com.imooc.activitiweb.pojo.Act_ru_task;
import com.imooc.activitiweb.pojo.UserInfoBean;
import org.apache.ibatis.annotations.*;
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
@Component
public interface ActivitiMapper {

    //读取表单
    @Select("SELECT Control_ID_,Control_VALUE_ from formdata where PROC_INST_ID_ = #{PROC_INST_ID}")
    List<HashMap<String, Object>> selectFormData(@Param("PROC_INST_ID") String PROC_INST_ID);


    //写入表单
    @Insert("<script> insert into formdata (PROC_DEF_ID_,PROC_INST_ID_,FORM_KEY_,Control_ID_,Control_VALUE_,Control_Is_Param_,Control_Label)" +
            "    values" +
            "    <foreach collection=\"maps\" item=\"formData\" index=\"index\" separator=\",\">" +
            "      (#{formData.PROC_DEF_ID_,jdbcType=VARCHAR},#{formData.PROC_INST_ID_,jdbcType=VARCHAR}," +
            "      #{formData.FORM_KEY_,jdbcType=VARCHAR}, #{formData.Control_ID_,jdbcType=VARCHAR},#{formData.Control_VALUE_,jdbcType=VARCHAR},#{formData.Control_Is_Param_,jdbcType=VARCHAR},#{formData.Control_Label,jdbcType=VARCHAR})" +
            "    </foreach>  </script>")
    int insertFormData(@Param("maps") List<HashMap<String, Object>> maps);

    //删除表单
    @Delete("DELETE FROM formdata WHERE PROC_DEF_ID_ = #{PROC_DEF_ID}")
    int DeleteFormData(@Param("PROC_DEF_ID") String PROC_DEF_ID);

    //获取用户名
    @Select("SELECT * from user")
    List<UserInfoBean> selectUser();

    //测试
    @Select("select NAME_,TASK_DEF_KEY_ from act_ru_task")
    List<Act_ru_task> selectName();

    //流程定义数
    Integer getCountProcessDefinition();

    //进行中的流程实例
    Integer getCountRunningProcessInstance();

    //查询流程定义产生的流程实例数e
    List<HashMap<String, Object>> getCountProcessDefinitionCreateProcessInstance();

    //已完成的流程实例数
    Integer getHistoricProcessInstance();

    //查询正在运行的任务
    Integer getCountRunningTask();

    //查询用户数
    Integer getCountUsers();

    //获取过去七天内创建的任务和完成的任务
    List<HashMap<String, Object>> getCountListTask();

    //获取今天产生的任务数
    Integer getCountTodayTasks();

    //获取今天产生的流程实例数
    Integer getCountTodayProcessInstances();

    //获取今天部署的流程定义数
    Integer getCountTodayProcessDefinitionDeployment();

    //获取历史表单
    List<HashMap<String, Object>> getHistoryTaskFrom(String processInstanceId,String formKey);

}
