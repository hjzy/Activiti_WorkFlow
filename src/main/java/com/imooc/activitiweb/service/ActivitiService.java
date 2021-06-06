package com.imooc.activitiweb.service;

import java.util.HashMap;
import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/4 22:48
 * @email yifan@yifansun.cn
 */
public interface ActivitiService {    //流程定义数
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
    List<HashMap<String, Object>> getHistoryTaskFrom(String processInstanceId, String formKey);


}
