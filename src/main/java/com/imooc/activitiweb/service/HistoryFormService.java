package com.imooc.activitiweb.service;

import com.imooc.activitiweb.mapper.FormMapper;
import com.imooc.activitiweb.pojo.FormData;

import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/1 22:26
 * @email yifan@yifansun.cn
 */
public interface HistoryService {
    List<FormData> getFormDataList(String pro_ins_id);
}
