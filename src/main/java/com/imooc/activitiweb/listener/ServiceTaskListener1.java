package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */

/**
 * @Description 服务任务
 * @Date 2021/4/22 23:58
 **/
public class ServiceTaskListener1 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        //服务任务能拿到的部分属性
        System.out.println(execution.getEventName());
        System.out.println(execution.getProcessDefinitionId());
        System.out.println(execution.getProcessInstanceId());
        //设置流程变量
        execution.setVariable("aa", "bb");
    }
}
