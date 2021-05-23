package com.imooc.activitiweb.pojo;

import lombok.Data;
/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @email yifan@yifansun.cn
 */
@Data
public class Count {
    private int type;
    private int count;
    private String TypeName;

    public String getTypeName() {

        switch (type) {
            case 0:
                TypeName = "测试用文章";
                break;
            case 1:
                TypeName = "壮学研究";
                break;
            case 2:
                TypeName = "壮乡见闻";
                break;
            case 3:
                TypeName = "文化艺术";
                break;
            case 4:
                TypeName = "历史印记";
                break;
            case 5:
                TypeName = "民风民俗";
                break;
            case 6:
                TypeName = "壮学文档";
                break;
            case 7:
                TypeName = "民族政策";
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }

        return TypeName;
    }
}
