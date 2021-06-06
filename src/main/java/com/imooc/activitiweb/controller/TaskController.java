package com.imooc.activitiweb.controller;

import com.imooc.activitiweb.SecurityUtil;
import com.imooc.activitiweb.mapper.ActivitiMapper;
import com.imooc.activitiweb.util.AjaxResponse;
import com.imooc.activitiweb.util.GlobalConfig;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.FormProperty;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    ActivitiMapper mapper;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private RepositoryService repositoryService;

    //获取我的代办任务

    /**
     * Description //TODO
     *
     * @param
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/5/23 18:17
     **/
    @GetMapping(value = "/getTasks")
    public AjaxResponse getTasks() {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }
            Page<Task> tasks = taskRuntime.tasks(Pageable.of(0, 100));

            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            for (Task tk : tasks.getContent()) {
                ProcessInstance processInstance = processRuntime.processInstance(tk.getProcessInstanceId());
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("id", tk.getId());
                hashMap.put("name", tk.getName());
                hashMap.put("status", tk.getStatus());
                hashMap.put("createdDate", tk.getCreatedDate());
                if (tk.getAssignee() == null) {//执行人，null时前台显示未拾取
                    hashMap.put("assignee", "待拾取任务");
                } else {
                    hashMap.put("assignee", tk.getAssignee());//
                }

                hashMap.put("instanceName", processInstance.getName());
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), listMap);


        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "获取我的代办任务失败", e.toString());
        }
    }


    //完成待办任务

    /**
     * Description //TODO
     *
     * @param taskID
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/5/23 18:17
     **/
    @GetMapping(value = "/completeTask")
    public AjaxResponse completeTask(@RequestParam("taskID") String taskID) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }

            Task task = taskRuntime.task(taskID);

            //Assignee为空而又能查出任务，说明该用户是候选人
            if (task.getAssignee() == null) {
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
            }
            taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId())
                    //.withVariable("num", "2")//执行环节设置变量
                    .build());


            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), null);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "完成失败", e.toString());
        }
    }


    //启动

    /**
     * Description //TODO
     *
     * @param processDefinitionKey
     * @param instanceName
     * @param instanceVariable
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/5/23 18:17
     **/
    @GetMapping(value = "/startProcess4")
    public AjaxResponse startProcess3(@RequestParam("processDefinitionKey") String processDefinitionKey,
                                      @RequestParam("instanceName") String instanceName,
                                      @RequestParam("instanceVariable") String instanceVariable) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("wukong");
            }

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), null);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "失败", e.toString());
        }
    }


    //渲染表单

    /**
     * Description //TODO
     *
     * @param taskID
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/5/23 18:17
     **/
    @GetMapping(value = "/formDataShow")
    public AjaxResponse formDataShow(@RequestParam("taskID") String taskID) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }
            //拿到对应的task
            Task task = taskRuntime.task(taskID);

//-----------------------构建表单控件历史数据字典------------------------------------------------
            //本实例所有保存的表单数据HashMap，为了快速读取控件以前环节存储的值
            HashMap<String, String> controlListMap = new HashMap<>();

            //根据流程实例ID查出本实例所有保存的表单数据
            List<HashMap<String, Object>> tempControlList = mapper.selectFormData(task.getProcessInstanceId());

            for (HashMap ls : tempControlList) {
                String Control_ID = ls.get("Control_ID_").toString();//拿到控件id
                String Control_VALUE = ls.get("Control_VALUE_").toString();//拿到控件值
                controlListMap.put(Control_ID, Control_VALUE);
            }
            //String controlistMapValue = controlistMap.get("控件ID");
            //controlistMap.containsKey()


/*  ------------------------------------------------------------------------------
            FormProperty_0ueitp2-_!类型-_!名称-_!默认值-_!是否参数
            例子：
            FormProperty_0137fhi-_!file-_!文件-_!无-_!f
            FormProperty_0lovri0-_!string-_!姓名-_!请输入姓名-_!f
            FormProperty_1iu6onu-_!long-_!年龄-_!请输入年龄-_!s
            FormProperty_227uamu-_!cUser-_!经办用户-_!张三-_!s
            FormProperty_0e84poa-_!cUser-_!经办用户-_!FormProperty_227uamu-_!s

            默认值：无、字符常量、FormProperty_开头定义过的控件ID
            是否参数：f为不是参数，s是字符，t是时间(不需要int，因为这里int等价于string)
            注：类型是可以获取到的，但是为了统一配置原则，都配置到
 */

            //注意!!!!!!!!:表单Key必须要和任务编号一模一样，因为参数需要任务key，但是无法获取，只能获取表单key“task.getFormKey()”当做任务key

            //根据传入的taskId拿到所有的UserTask信息
            UserTask userTask = (UserTask) repositoryService.getBpmnModel(task.getProcessDefinitionId())
                    .getFlowElement(task.getFormKey());

            //如果userTask为空，告知用户无表单
            if (userTask == null) {
                return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                        GlobalConfig.ResponseCode.SUCCESS.getDesc(), "无表单");
            }
            //拿到表单信息
            List<FormProperty> formProperties = userTask.getFormProperties();

            //返回给前端的list
            List<HashMap<String, Object>> listMap = new ArrayList<HashMap<String, Object>>();

            //根据"-_!"分隔符拿到所需各项信息
            for (FormProperty fp : formProperties) {
                String[] splitFP = fp.getId().split("-_!");

                HashMap<String, Object> hashMap = new HashMap<>();
                //控件id，FormProperty_1iu6onu
                hashMap.put("id", splitFP[0]);
                //控件类型，String，Int等
                hashMap.put("controlType", splitFP[1]);
                //控件标签，年龄，姓名等
                hashMap.put("controlLable", splitFP[2]);

                //使得可以读取上一个环节填入的参数值
                //默认值如果是表单控件ID
                /*
                 * 如何在流程流转中使用前面动态表单中填写的值为后面动态表单中的值赋值？
                 * 例如，前面八戒填写了请假日期，在后面的审批环节中，排他网关需要用到八戒填写的值
                 * 在设定变量的时候，将变量的类型设为s（如FormProperty_1iu6onu-_!long-_!天数-_!请输入请假天数-_!s）
                 * 在后续需要用到该变量的地方，如排他网关中，需要用到八戒的请假天数，则可以使用${FormProperty_1iu6onu}<3
                 * 来表示八戒的请假天数小于3天
                 * 如果后续审批的时候需要用到该变量，则将变量的默认值设置为八戒的请假天数的id即可（如FormProperty_0ueitp2-_!long-_!天数-_!FormProperty_1iu6onu-_!s）
                 *
                 **/
                if (splitFP[3].startsWith("FormProperty_")) {
                    /*
                    如果默认值是“FormProperty_开头定义过的控件ID”这种形式，说明我们想读取之前的这个控件填入的数据，就可以根据控件id得到该值
                     **/
                    //控件ID存在
                    if (controlListMap.containsKey(splitFP[3])) {
                        hashMap.put("controlDefValue", controlListMap.get(splitFP[3]));
                    } else {
                        //控件ID不存在
                        hashMap.put("controlDefValue", "读取失败，检查" + splitFP[0] + "配置");
                    }
                } else {
                    //默认值如果不是表单控件ID则写入默认值
                    hashMap.put("controlDefValue", splitFP[3]);
                }


                hashMap.put("controlIsParam", splitFP[4]);//参数类型，如Sting，文件等
                listMap.add(hashMap);
            }

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "失败", e.toString());
        }
    }


    //保存表单

    /**
     * Description //TODO
     *
     * @param taskID
     * @param formData
     * @return com.imooc.activitiweb.util.AjaxResponse
     * @Date 2021/5/23 18:17
     **/
    @PostMapping(value = "/formDataSave")
    public AjaxResponse formDataSave(@RequestParam("taskID") String taskID,
                                     @RequestParam("formData") String formData) {
        try {
            if (GlobalConfig.Test) {
                securityUtil.logInAs("bajie");
            }

            Task task = taskRuntime.task(taskID);

            //formData:控件id-_!控件值-_!是否参数!_!控件id-_!控件值-_!是否参数
            //FormProperty_0lovri0-_!不是参数-_!f!_!FormProperty_1iu6onu-_!数字参数-_!s


            HashMap<String, Object> variables = new HashMap<String, Object>();
            Boolean hasVariables = false;//是否有参数


            List<HashMap<String, Object>> listMap = new ArrayList<>();

            //前端传来的字符串，拆分成每个控件
            String[] formDataList = formData.split("!_!");//分割每个控件
            //提取控件的值
            for (String controlItem : formDataList) {
                String[] formDataItem = controlItem.split("-_!");

                HashMap<String, Object> hashMap = new HashMap<>();
                if (!formDataItem[1].equals("")) {

                    hashMap.put("PROC_DEF_ID_", task.getProcessDefinitionId());//流程定义id
                    hashMap.put("PROC_INST_ID_", task.getProcessInstanceId());//流程实例id
                    hashMap.put("FORM_KEY_", task.getFormKey());//表单key
                    hashMap.put("Control_ID_", formDataItem[0]);//控件id
                    hashMap.put("Control_VALUE_", formDataItem[1]);//控件值
                    hashMap.put("Control_Is_Param_", formDataItem[2]);//控件类型
                    hashMap.put("Control_Label", formDataItem[3]);
                    hashMap.put("Control_Type", formDataItem[4]);
                    listMap.add(hashMap);
                    System.out.println(formDataItem[3]);
                    //构建参数集合
                    switch (formDataItem[2]) {
                        case "f"://非参数
                            System.out.println("控件值不作为参数");
                            break;
                        case "s"://字符
                            variables.put(formDataItem[0], formDataItem[1]);
                            hasVariables = true;
                            break;
                        case "t"://时间
                            SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                            variables.put(formDataItem[0], timeFormat.parse(formDataItem[2]));
                            hasVariables = true;
                            break;
                        case "b"://布尔型
                            variables.put(formDataItem[0], BooleanUtils.toBoolean(formDataItem[2]));
                            hasVariables = true;
                            break;
                        case "e"://文件
                            variables.put(formDataItem[0], formDataItem[1]);
                            hasVariables = true;
                        default:
                            System.out.println("控件参数类型配置错误：" + formDataItem[0] + "的参数类型不存在，" + formDataItem[2]);
                    }
                }

            }//for结束

            if (hasVariables) {
                //带参数完成任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                        .withVariables(variables)
                        .build());
            } else {
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(taskID)
                        .build());
            }

            //写入数据库
            if (listMap.size() != 0) {
                int result = mapper.insertFormData(listMap);
            }

            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.SUCCESS.getCode(),
                    GlobalConfig.ResponseCode.SUCCESS.getDesc(), listMap);
        } catch (Exception e) {
            return AjaxResponse.AjaxData(GlobalConfig.ResponseCode.ERROR.getCode(),
                    "失败", e.toString());
        }
    }


}
