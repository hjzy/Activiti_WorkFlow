package com.imooc.activitiweb.util;

/*
 * @Description //数据返回给前端处理
 * @Date 2021/4/19 17:43
 **/
public class AjaxResponse {
    //成功或失败
    private Integer status;
    //一般在错误时返回信息
    private String msg;
    private Object obj;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    private AjaxResponse(Integer status, String msg, Object obj) {
        this.status = status;
        this.msg = msg;
        this.obj = obj;
    }

    public static AjaxResponse AjaxData(Integer status, String msg, Object obj) {
        return new AjaxResponse(status, msg, obj);
    }
}
