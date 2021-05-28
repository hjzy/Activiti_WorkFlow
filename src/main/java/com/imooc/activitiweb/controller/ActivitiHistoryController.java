package com.imooc.activitiweb.controller;

import com.imooc.activitiweb.SecurityUtil;
import com.imooc.activitiweb.pojo.FormData;
import com.imooc.activitiweb.pojo.UserInfoBean;
import com.imooc.activitiweb.service.ActivitiService;
import com.imooc.activitiweb.service.HistoryFormService;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.activiti.bpmn.model.Process;
import org.activiti.bpmn.model.*;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */
@RestController
@RequestMapping("/activitiHistory")
public class ActivitiHistoryController {

    @Autowired
    HistoryFormService historyFormService;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private HistoryService historyService;
    @Autowired
    private ActivitiService activitiService;

    //根据用户名查询任务
    @GetMapping(value = "/getInstancesByUserName")
    public AjaxResponse InstancesByUser() {
        try {

            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .orderByHistoricTaskInstanceEndTime().asc()
                    .taskAssignee("bajie")
                    .list();

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), historicTaskInstances);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取历史任务失败", e.toString());
        }

    }


    /**
     * @param piID
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Description //TODO
     * @Date 2021/4/19 5:53
     **/
    //根据流程实例id查询任务
    @GetMapping(value = "/getInstancesByPiID")
    public AjaxResponse getInstancesByPiID(@RequestParam("piID") String piID) {
        try {

            //--------------------------------------------另一种写法-------------------------
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .orderByHistoricTaskInstanceEndTime().desc()
                    .processInstanceId(piID)
                    .list();


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), historicTaskInstances);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取历史任务失败", e.toString());
        }

    }

    /**
     * Description //根据任务id查询历史任务
     *
     * @param taskID
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/4/30 21:43
     **/
    @GetMapping(value = "/getInstancesByTaskID")
    public AjaxResponse getInstancesByTaskID(@RequestParam("taskID") String taskID) {
        try {
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            //--------------------------------------------另一种写法-------------------------
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .orderByHistoricTaskInstanceEndTime().desc()
                    .taskId(taskID)
                    .list();

            for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
                List<FormData> formDataList = historyFormService.getFormDataList(historicTaskInstance.getProcessInstanceId(), historicTaskInstance.getTaskDefinitionKey());
                if (formDataList.isEmpty()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("startTime", historicTaskInstance.getStartTime());
                    hashMap.put("endTime", historicTaskInstance.getEndTime());
                    hashMap.put("name", historicTaskInstance.getName());
                    hashMap.put("assignee", historicTaskInstance.getAssignee());
                    hashMap.put("isFormKey", 0);
                    for (String ii : hashMap.keySet()) {
                        Object value = hashMap.get(ii);
                        if (value == null) {
                            hashMap.replace(ii, "未知");
                        }

                    }

                    listMap.add(hashMap);
                } else {
                    for (FormData formData : formDataList) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("startTime", historicTaskInstance.getStartTime());
                        hashMap.put("endTime", historicTaskInstance.getEndTime());
                        hashMap.put("name", historicTaskInstance.getName());
                        hashMap.put("assignee", historicTaskInstance.getAssignee());
                        hashMap.put("controlLabel", formData.getControl_Label());
                        hashMap.put("controlValue", formData.getControl_VALUE_());
                        hashMap.put("controlType", formData.getControl_Type());
                        hashMap.put("controlIsParam", formData.getControl_Is_Param_());
                        hashMap.put("processInstanceId", formData.getPROC_INST_ID_());
                        hashMap.put("taskKey", formData.getFORM_KEY_());
                        hashMap.put("isFormKey", 1);

                        for (String ii : hashMap.keySet()) {
                            Object value = hashMap.get(ii);
                            if (value == null) {
                                hashMap.replace(ii, "未知");
                            }

                        }

                        listMap.add(hashMap);
                    }
                }


            }
            System.out.println(listMap);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取历史任务失败", e.toString());
        }

    }

    /**
     * Description //流程图高亮
     *
     * @param instanceId
     * @param UserInfoBean
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/4/21 2:05
     **/
    @GetMapping("/gethighLine")
    public AjaxResponse gethighLine(@RequestParam("instanceId") String instanceId, @AuthenticationPrincipal UserInfoBean UserInfoBean) {
        try {
            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(instanceId).singleResult();
            //获取bpmnModel对象
            BpmnModel bpmnModel = repositoryService.getBpmnModel(historicProcessInstance.getProcessDefinitionId());
            //因为我们这里只定义了一个Process 所以获取集合中的第一个即可
            Process process = bpmnModel.getProcesses().get(0);
            //获取所有的FlowElement信息
            Collection<FlowElement> flowElements = process.getFlowElements();

            Map<String, String> map = new HashMap<>();
            for (FlowElement flowElement : flowElements) {
                //判断是否是连线
                if (flowElement instanceof SequenceFlow) {
                    SequenceFlow sequenceFlow = (SequenceFlow) flowElement;
                    String ref = sequenceFlow.getSourceRef();//连线的源头task
                    String targetRef = sequenceFlow.getTargetRef();//连线指向的task
                    map.put(ref + targetRef, sequenceFlow.getId());
                }
            }

            //获取流程实例 历史节点(全部)
            List<HistoricActivityInstance> list = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .list();
            //各个历史节点   两两组合 key
            Set<String> keyList = new HashSet<>();
            for (HistoricActivityInstance i : list) {
                for (HistoricActivityInstance j : list) {
                    if (i != j) {
                        keyList.add(i.getActivityId() + j.getActivityId());
                    }
                }
            }
            //高亮连线ID
            //直接从bpmn得到的连线key是StartEvent_1Task1的形式，无法直接得到连线对应的端点
            //以端点两两组合的key和已执行的线的key比对，从而找到已执行的线对应的端点
            Set<String> highLine = new HashSet<>();
            keyList.forEach(s -> highLine.add(map.get(s)));


            //获取流程实例 历史节点（已完成）
            List<HistoricActivityInstance> listFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .finished()
                    .list();
            //高亮节点ID
            Set<String> highPoint = new HashSet<>();
            listFinished.forEach(s -> highPoint.add(s.getActivityId()));

            //获取流程实例 历史节点（待办节点）
            List<HistoricActivityInstance> listUnFinished = historyService.createHistoricActivityInstanceQuery()
                    .processInstanceId(instanceId)
                    .unfinished()
                    .list();

            //需要移除的高亮连线
            Set<String> set = new HashSet<>();
            //待办高亮节点
            Set<String> waitingToDo = new HashSet<>();
            listUnFinished.forEach(s -> {
                waitingToDo.add(s.getActivityId());

                for (FlowElement flowElement : flowElements) {
                    //判断是否是 用户节点
                    if (flowElement instanceof UserTask) {
                        UserTask userTask = (UserTask) flowElement;

                        if (userTask.getId().equals(s.getActivityId())) {
                            List<SequenceFlow> outgoingFlows = userTask.getOutgoingFlows();
                            //因为高亮连线查询的是所有节点两两组合，把待办之后往外发出的连线也包含进去了，
                            // 所以要把高亮待办节点之后出的连线去掉
                            if (outgoingFlows != null && outgoingFlows.size() > 0) {
                                outgoingFlows.forEach(a -> {
                                    if (a.getSourceRef().equals(s.getActivityId())) {
                                        set.add(a.getId());
                                    }
                                });
                            }
                        }
                    }
                }
            });

            highLine.removeAll(set);


            //获取当前用户
            //User sysUser = getSysUser();
            Set<String> iDo = new HashSet<>(); //存放 高亮 我的办理节点
            //当前用户已完成的任务

            String AssigneeName = null;
            if (GlobalConfig.Test) {
                AssigneeName = "bajie";
            } else {
                AssigneeName = UserInfoBean.getUsername();
            }

            List<HistoricTaskInstance> taskInstanceList = historyService.createHistoricTaskInstanceQuery()
                    .taskAssignee(AssigneeName)
                    .finished()
                    .processInstanceId(instanceId).list();

            taskInstanceList.forEach(a -> iDo.add(a.getTaskDefinitionKey()));

            Map<String, Object> reMap = new HashMap<>();
            reMap.put("highPoint", highPoint);//高亮节点
            reMap.put("highLine", highLine);//高亮连线
            reMap.put("waitingToDo", waitingToDo);//待办节点
            reMap.put("iDo", iDo);//当前用户已完成的任务

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), reMap);

        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "渲染历史流程失败", e.toString());
        }
    }

    /**
     * Description //以流程实例id为key拿到历史记录及表单内容
     *
     * @param piID
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/5/2 20:05
     **/
    @GetMapping(value = "/getHistoryFormData")
    public AjaxResponse getHistoryFormData(@RequestParam("piID") String piID) {
        try {
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            List<HistoricTaskInstance> historicTaskInstances = historyService.createHistoricTaskInstanceQuery()
                    .orderByHistoricTaskInstanceEndTime().desc()
                    .processInstanceId(piID)
                    .list();


            for (HistoricTaskInstance historicTaskInstance : historicTaskInstances) {
                List<FormData> formDataList = historyFormService.getFormDataList(historicTaskInstance.getProcessInstanceId(), historicTaskInstance.getTaskDefinitionKey());
                if (formDataList.isEmpty()) {
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("startTime", historicTaskInstance.getStartTime());
                    hashMap.put("endTime", historicTaskInstance.getEndTime());
                    hashMap.put("name", historicTaskInstance.getName());
                    hashMap.put("assignee", historicTaskInstance.getAssignee());
                    hashMap.put("isFormKey", 0);
                    Iterator<String> it = hashMap.keySet().iterator();
                    while (it.hasNext()) {
                        String ii = (String) it.next();
                        Object value = hashMap.get(ii);
                        if (value == null) {
                            hashMap.replace(ii, "未知");
                        }

                    }

                    listMap.add(hashMap);
                } else {
                    for (FormData formData : formDataList) {
                        HashMap<String, Object> hashMap = new HashMap<>();
                        hashMap.put("startTime", historicTaskInstance.getStartTime());
                        hashMap.put("endTime", historicTaskInstance.getEndTime());
                        hashMap.put("name", historicTaskInstance.getName());
                        hashMap.put("assignee", historicTaskInstance.getAssignee());
                        hashMap.put("controlLabel", formData.getControl_Label());
                        hashMap.put("controlValue", formData.getControl_VALUE_());
                        hashMap.put("controlType", formData.getControl_Type());
                        hashMap.put("controlIsParam", formData.getControl_Is_Param_());
                        hashMap.put("processInstanceId", formData.getPROC_INST_ID_());
                        hashMap.put("taskKey", formData.getFORM_KEY_());
                        hashMap.put("isFormKey", 1);

                        Iterator<String> it = hashMap.keySet().iterator();
                        while (it.hasNext()) {
                            String ii = (String) it.next();
                            Object value = hashMap.get(ii);
                            if (value == null) {
                                hashMap.replace(ii, "未知");
                            }

                        }

                        listMap.add(hashMap);
                    }
                }


            }
            System.out.println(listMap);
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取历史任务失败", e.toString());
        }

    }

    @GetMapping(value = "/getHistoryFromDataByPIidAndFromKey")
    public AjaxResponse getHistoryFromDataByPIidAndFromKey(String processInstanceId, String fromKey) {
        try {
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();
            listMap=activitiService.getHistoryTaskFrom(processInstanceId,fromKey);

            //HashMap<String,Object> Map=new HashMap<>();

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取历史数据失败！", e.toString());
        }
    }


}
