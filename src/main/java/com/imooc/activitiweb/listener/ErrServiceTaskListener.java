package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @Description 错误事件的监听类
 * @Date 2021/4/22 22:40
 **/
public class ErrServiceTaskListener implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {
        throw new BpmnError("Error_21ldg70");
    }
}
