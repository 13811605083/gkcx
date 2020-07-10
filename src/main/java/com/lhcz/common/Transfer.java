package com.lhcz.common;

import lombok.Data;

/**
 * Layui穿梭框数据实体类
 * @author 41008
 */
@Data
public class Transfer {

    private String value;

    private String title;

    private Boolean disabled = false;

    public Transfer(){

    }

    public Transfer(String value,String title){
        this.value = value;
        this.title = title;
    }

}
