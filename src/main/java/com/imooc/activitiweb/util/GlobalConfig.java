package com.imooc.activitiweb.util;
/*
 * @Description 枚举与静态常量
 * @Date 2021/4/19 17:44
 **/
public class GlobalConfig {
    /**
     * 测试场景
     */
    public static final Boolean Test = true;

    //windows路径
    public static final String BPMN_PathMapping = "file:F:\\Develop_Project\\Activiti_WorkFlow\\src\\main\\resources\\resources\\bpmn\\";

    //Linux路径
    //public static final String BPMN_PathMapping = "file:/root/Activiti/";

    public enum ResponseCode {
        SUCCESS(0, "成功"),
        ERROR(1, "错误");

        private final int code;
        private final String desc;

        ResponseCode(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public int getCode() {
            return code;
        }

        public String getDesc() {
            return desc;
        }
    }

}
