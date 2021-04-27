package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/4/26 21:55
 * @email yifan@yifansun.cn
 */
public class TaskMailListener implements TaskListener {
    @Override
    public void notify(DelegateTask delegateTask) {
        delegateTask.getAssignee();
    }
}
