package com.imooc.activitiweb.service.impl;

import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.service.ActivitiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/4 22:49
 * @email yifan@yifansun.cn
 */
@Service
public  class ActivitiServiceImpl implements ActivitiService {

    @Autowired
    ActivitiMapper activitiMapper;

    @Override
    public Integer getCountProcessDefinition() {
        return activitiMapper.getCountProcessDefinition();
    }

    @Override
    public Integer getCountRunningProcessInstance() {
        return activitiMapper.getCountRunningProcessInstance();
    }

    @Override
    public List<HashMap<String, Object>> getCountProcessDefinitionCreateProcessInstance() {
        return activitiMapper.getCountProcessDefinitionCreateProcessInstance();
    }

    @Override
    public Integer getHistoricProcessInstance() {
        return activitiMapper.getHistoricProcessInstance();
    }

    @Override
    public Integer getCountRunningTask() {
        return activitiMapper.getCountRunningTask();
    }

    @Override
    public Integer getCountUsers() {
        return activitiMapper.getCountUsers();
    }

    @Override
    public List<HashMap<String, Object>> getCountListTask() {
        return activitiMapper.getCountListTask();
    }

    @Override
    public Integer getCountTodayTasks() {
        return activitiMapper.getCountTodayTasks();
    }

    @Override
    public Integer getCountTodayProcessInstances() {
        return activitiMapper.getCountTodayProcessInstances();
    }

    @Override
    public Integer getCountTodayProcessDefinitionDeployment() {
        return activitiMapper.getCountTodayProcessDefinitionDeployment();
    }
}
