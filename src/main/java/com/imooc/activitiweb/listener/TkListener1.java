package com.imooc.activitiweb.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TkListener1 implements TaskListener {
    /**
     * @Description //TODO
     * @Date 2021/4/22 19:14
     * @param delegateTask
     * @return void
     * DelegateTask含有当前任务的全部信息
     *         delegateTask.getAssignee();//获取任务执行人
     *         delegateTask.getCandidates();//获取候选人
     *         delegateTask.getCategory();//任务的类别。这是一个可选字段，并允许将任务“标记”为属于某个类别。
     *         delegateTask.getId();//获得任务ID
     *         delegateTask.getDelegationState();//当前任务的委派状态。
     *         delegateTask.getCreateTime();//获取创建时间
     *         delegateTask.getCurrentActivitiListener();//获取当前的监听器
     *         delegateTask.getProcessInstanceId();//获取流程实例ID
     *         delegateTask.getDescription();//获取对该事件的描述
     *         delegateTask.getDueDate();//获取任务的截止日期
     *         delegateTask.getFormKey();//用户任务的表单key
     *         delegateTask.getName();//获取任务名
     *         delegateTask.getOwner();//获取任务所有者
     *         delegateTask.getTaskDefinitionKey();//获取任务的key
     *         delegateTask.getVariable();//获取全局参数
     *         delegateTask.getVariableLocal();//获取本地参数
     *
     **/
    @Override
    public void notify(DelegateTask delegateTask) {



        System.out.println("执行人："+delegateTask.getAssignee());
        //根据用户名查询用户电话并调用发送短信接口
        delegateTask.setVariable("delegateAssignee",delegateTask.getAssignee());
    }
}
