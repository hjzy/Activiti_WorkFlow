package com.imooc.activitiweb;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.StartMessagePayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
/**
 * @Description //信号事件，使用代码抛出信号，触发捕获事件
 * @Date 2021/4/22 21:54
 *
 * @return
 **/
@SpringBootTest
public class Part10_EventAndTask {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ProcessRuntime processRuntime;
    @Test
    public void signalStart() {
        runtimeService.signalEventReceived("Signal_0igedde");
    }
  //消息事件，利用消息事件撤回申请
    @Test
    public void msgBack() {
        Execution exec = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("Message_2qvor1p")
                .processInstanceId("618bdd31-ef41-11ea-854b-dcfb4875e032")
                .singleResult();
        runtimeService.messageEventReceived("Message_2qvor1p",exec.getId());

       // runtimeService.startProcessInstanceByMessage("Message_2qvor1p");

//        ProcessInstance pi = processRuntime.start(StartMessagePayloadBuilder
//                        .start("Message_2qvor1p")
//                .build()
//                );

    }


}
