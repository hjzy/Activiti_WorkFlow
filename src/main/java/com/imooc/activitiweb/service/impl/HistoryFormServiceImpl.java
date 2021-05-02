package com.imooc.activitiweb.service.impl;


import com.imooc.activitiweb.mapper.FormMapper;
import com.imooc.activitiweb.pojo.FormData;
import com.imooc.activitiweb.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yifansun
 * @version 1.0
 * @Description
 * @date 2021/5/1 22:26
 * @email yifan@yifansun.cn
 */
@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    FormMapper formMapper;
    @Override
    public List<FormData> getFormDataList(String pro_ins_id) {
        return formMapper.getFormDataList(pro_ins_id);
    }
}
