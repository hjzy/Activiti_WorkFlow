package com.imooc.activitiweb.controller;

import com.imooc.activitiweb.service.ActivitiService;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Description
 *
 * @author yifansun
 * @version 1.0
 * @date 2021/5/4 23:06
 * @email yifan@yifansun.cn
 */

@RestController
@RequestMapping("/charts")
public class ChartsController {

    @Autowired
    ActivitiService activitiService;

    @RequestMapping("/getCount")
    public AjaxResponse getCount() {
        try {
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            //流程定义数
            int count1 = activitiService.getCountProcessDefinition();
            HashMap<String, Object> countProcessDefinition = new HashMap<>();
            countProcessDefinition.put("countProcessDefinition", count1);
            listMap.add(countProcessDefinition);

            //用户数
            int count2 = activitiService.getCountUsers();
            HashMap<String, Object> countUsers = new HashMap<>();
            countUsers.put("countUsers", count2);
            listMap.add(countUsers);

            //正在运行的流程实例数
            int count3 = activitiService.getCountRunningProcessInstance();
            HashMap<String, Object> countRunningProcessInstance = new HashMap<>();
            countRunningProcessInstance.put("countRunningProcessInstance", count3);
            listMap.add(countRunningProcessInstance);

            //正在运行的任务数
            int count4 = activitiService.getCountRunningTask();
            HashMap<String, Object> countRunningTask = new HashMap<>();
            countRunningTask.put("countRunningTask", count4);
            listMap.add(countRunningTask);

            //历史流程实例数
            int count6 = activitiService.getHistoricProcessInstance();
            HashMap<String, Object> historicProcessInstance = new HashMap<>();
            historicProcessInstance.put("historicProcessInstance", count6);
            listMap.add(historicProcessInstance);

            //当天部署的流程定义数
            int count7 = activitiService.getCountTodayProcessDefinitionDeployment();
            HashMap<String, Object> countTodayProcessDefinitionDeployment = new HashMap<>();
            countTodayProcessDefinitionDeployment.put("countTodayProcessDefinitionDeployment", count7);
            listMap.add(countTodayProcessDefinitionDeployment);

            //当天创建的流程实例数
            int count8 = activitiService.getCountTodayProcessInstances();
            HashMap<String, Object> countTodayProcessInstances = new HashMap<>();
            countTodayProcessInstances.put("countTodayProcessInstances", count8);
            listMap.add(countTodayProcessInstances);

            //当天创建的任务数
            int count5 = activitiService.getCountTodayTasks();
            HashMap<String, Object> countTodayTasks = new HashMap<>();
            countTodayTasks.put("countTodayTasks", count5);
            listMap.add(countTodayTasks);

            //排名前列的流程定义创建的流程实例数
            List<HashMap<String, Object>> hashMapListProcessDefinition = activitiService.getCountProcessDefinitionCreateProcessInstance();


            List<HashMap<String, Object>> hashMapListTask = activitiService.getCountListTask();

            List<List<HashMap<String, Object>>> list = new ArrayList<>();
            list.add(listMap);
            list.add(hashMapListProcessDefinition);
            list.add(hashMapListTask);


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), list);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取统计信息失败", e.toString());
        }
    }
}
