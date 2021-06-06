package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */
public class ServiceTaskListener2 implements JavaDelegate {

    @Override
    public void execute(DelegateExecution execution) {

        execution.getVariable("aa");
        System.out.println(execution.getVariable("aa"));

    }
}
