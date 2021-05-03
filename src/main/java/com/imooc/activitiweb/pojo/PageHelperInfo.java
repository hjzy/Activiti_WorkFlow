package com.imooc.activitiweb.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/4 0:04
 * @email yifan@yifansun.cn
 */

public class PageHelperInfo {

    /**
     * 	数据  对应 pagerheper的数据
     */
    private Object data;
    /**
     * 	总数据条数 对应layui的count
     */
    private Integer count;

    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public Integer getCount() {
        return count;
    }


    public void setCount(Integer count) {
        this.count = count;
    }

    public PageHelperInfo(Object data, Integer count) {
        this.data = data;
        this.count = count;
    }
    public static PageHelperInfo ReturnInfo(Object data, Integer count){
        return new PageHelperInfo(data,count);
    }
}
