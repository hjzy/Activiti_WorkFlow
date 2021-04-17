package com.imooc.activitiweb;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class Part4_Task {

    @Autowired
    private TaskService taskService;

    //任务查询
    @Test
    void getTasks(){
        List<Task> list = taskService.createTaskQuery().list();
        for(Task tk : list){
            System.out.println("Id："+tk.getId());
            System.out.println("Name："+tk.getName());
            System.out.println("Assignee："+tk.getAssignee());
        }
    }

    //查询我的代办任务
    @Test
    void getTasksByAssignee(){
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("wukong")
                .list();
        for(Task tk : list){
            System.out.println("Id："+tk.getId());
            System.out.println("Name："+tk.getName());
            System.out.println("Assignee："+tk.getAssignee());
        }

    }

    //执行任务
    @Test
    void completeTask(){
        taskService.complete("902c5794-9ee0-11eb-8d12-001a7dda7111");
        System.out.println("完成任务");

    }

    //拾取任务
    @Test
    void claimTask(){
        Task task = taskService.createTaskQuery().taskId("835e8323-9f93-11eb-85e0-001a7dda7111").singleResult();
        taskService.claim("835e8323-9f93-11eb-85e0-001a7dda7111","bajie");
    }

    //归还与交办任务
    @Test
    void setTaskAssignee(){
        Task task = taskService.createTaskQuery().taskId("1f2a8edf-cefa-11ea-84aa-dcfb4875e032").singleResult();
        taskService.setAssignee("1f2a8edf-cefa-11ea-84aa-dcfb4875e032","null");//归还候选任务
        taskService.setAssignee("1f2a8edf-cefa-11ea-84aa-dcfb4875e032","wukong");//交办
    }



}
