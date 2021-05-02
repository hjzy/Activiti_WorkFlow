package com.imooc.activitiweb;

import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.mapper.FormMapper;
import com.imooc.activitiweb.mapper.MailMapper;
import com.imooc.activitiweb.pojo.FormData;
import org.activiti.engine.HistoryService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import sun.text.resources.FormatData;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
public class Part5_HistoricTaskInstance {
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ActivitiMapper mapper;
    @Autowired
    private FormMapper formMapper;

    //根据用户名查询历史记录
    @Test
    public void HistoricTaskInstanceByUser() {
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .taskAssignee("bajie")
                .list();
        for (HistoricTaskInstance hi : list) {
            System.out.println("Id：" + hi.getId());
            System.out.println("ProcessInstanceId：" + hi.getProcessInstanceId());
            System.out.println("Name：" + hi.getName());

        }

    }


    //根据流程实例ID查询历史
    @Test
    public void HistoricTaskInstanceByPiID() {
        List<HistoricTaskInstance> list = historyService
                .createHistoricTaskInstanceQuery()
                .orderByHistoricTaskInstanceEndTime().asc()
                .processInstanceId("83597a0f-9f93-11eb-85e0-001a7dda7111")
                .list();
        for (HistoricTaskInstance hi : list) {
            System.out.println("Id：" + hi.getId());
            System.out.println("ProcessInstanceId：" + hi.getProcessInstanceId());
            System.out.println("Name：" + hi.getName());

        }
    }

    @Test
    public void getHistoryFormData() {
        List<FormData> formDataList= formMapper.getFormDataList("36e21374-aa85-11eb-8da6-001a7dda7111");
        for (FormData formData : formDataList) {
            System.out.println(formData);
        }
    }

}
