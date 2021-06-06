package com.imooc.activitiweb.listener;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.Expression;
import org.springframework.beans.factory.annotation.Autowired;
/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */

/**
 * @Description 执行监听器
 * @Date 2021/4/22 20:58
 **/
public class PiListener implements ExecutionListener {
    @Autowired
    //该值是在bpmnjs中任务监听器可以添加的注入字段，sendType即为字段名称
    private Expression sendType;

    @Override
    public void notify(DelegateExecution execution) {
        System.out.println(execution.getEventName());
        System.out.println(execution.getProcessDefinitionId());
        if ("start".equals(execution.getEventName())) {
            //记录节点开始时间
        } else if ("end".equals(execution.getEventName())) {
            //记录节点结束时间
        }
        //输出前端传入的字段
        System.out.println("sendType:" + sendType.getValue(execution).toString());


    }
}
