package com.imooc.activitiweb;

import org.activiti.api.model.shared.model.VariableInstance;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class Part8_ProcessRuntime {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    //获取流程实例
    @Test
    public void getProcessInstance() {
        //用户使用securityUtil登录
        securityUtil.logInAs("bajie");
        //获取流程实例
        Page<ProcessInstance> processInstancePage = processRuntime
                .processInstances(Pageable.of(0,100));

        System.out.println("流程实例数量："+processInstancePage.getTotalItems());

        List<ProcessInstance> list = processInstancePage.getContent();

        for(ProcessInstance pi : list){
            System.out.println("-----------------------");
            System.out.println("getId：" + pi.getId());
            System.out.println("getName：" + pi.getName());
            System.out.println("getStartDate：" + pi.getStartDate());
            System.out.println("getStatus：" + pi.getStatus());
            System.out.println("getProcessDefinitionId：" + pi.getProcessDefinitionId());
            System.out.println("getProcessDefinitionKey：" + pi.getProcessDefinitionKey());

        }

    }
    @Test
    public void getProcessDefinitions(){
        securityUtil.logInAs("bajie");
        Page<ProcessDefinition> processDefinitionPage= processRuntime
                .processDefinitions(Pageable.of(0,5));
        List<ProcessDefinition> processDefinitionList =processDefinitionPage.getContent();
        //实际返回的ListMap
        List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
        for (ProcessDefinition pd : processDefinitionList) {
            HashMap<String, Object> hashMap = new HashMap<>();
            //System.out.println("流程定义ID："+pd.getId());
            hashMap.put("processDefinitionID", pd.getId());//流程定义id
            hashMap.put("name", pd.getName());//流程
            hashMap.put("key", pd.getKey());//key
            //hashMap.put("resourceName", pd.getResourceName());//部署文件名
            //hashMap.put("deploymentID", pd.getDeploymentId());//流程部署id
            hashMap.put("version", pd.getVersion());
            listMap.add(hashMap);

        }
        System.out.println(listMap);
    }

    //启动流程实例
    @Test
    public void startProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("myProcess_ProcessRuntime")
                .withName("第一个流程实例名称")
                //.withVariable("","")//启动时添加参数
                .withBusinessKey("自定义bKey")
                .build()
        );
    }

    //删除流程实例
    @Test
    public void delProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.delete(ProcessPayloadBuilder
                .delete()
                .withProcessInstanceId("6fcecbdb-d3e0-11ea-a6c9-dcfb4875e032")
                .build()
        );
    }

    //挂起流程实例
    @Test
    public void suspendProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.suspend(ProcessPayloadBuilder
                .suspend()
                .withProcessInstanceId("1f2314cb-cefa-11ea-84aa-dcfb4875e032")
                .build()
        );
    }

    //激活流程实例
    @Test
    public void resumeProcessInstance() {
        securityUtil.logInAs("bajie");
        ProcessInstance processInstance = processRuntime.resume(ProcessPayloadBuilder
                .resume()
                .withProcessInstanceId("1f2314cb-cefa-11ea-84aa-dcfb4875e032")
                .build()
                );
    }

    //流程实例参数
    @Test
    public void getVariables() {
        securityUtil.logInAs("bajie");
        List<VariableInstance> list = processRuntime.variables(ProcessPayloadBuilder
                .variables()
                .withProcessInstanceId("44a91433-ad12-11eb-a4b8-001a7dda7111")
                .build()
        );
        for(VariableInstance vi : list){
            System.out.println("-------------------");
            System.out.println("getName：" + vi.getName());
            System.out.println("getValue：" + vi.getValue());
            System.out.println("getTaskId：" + vi.getTaskId());
            System.out.println("getProcessInstanceId：" + vi.getProcessInstanceId());
        }
    }
    @Test
    public void getProcessInstanceTask(){

    }
}
