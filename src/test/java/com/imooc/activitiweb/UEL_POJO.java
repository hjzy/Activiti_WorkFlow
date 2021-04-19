package com.imooc.activitiweb;

import lombok.Data;

import java.io.Serializable;

@Data
public class UEL_POJO implements Serializable {
    private String zhixingren;
    private String pay;

    public String getZhixingren() {
        return zhixingren;
    }

    public void setZhixingren(String zhixingren) {
        this.zhixingren = zhixingren;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }
}
