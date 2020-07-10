package com.lhcz.common;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * layui树插件类
 * @author seifur
 */
@Data
public class LayuiTree implements Serializable {

    /**名称*/
    private String title;

    /**ID*/
    private String id;

    /**父节点ID*/
    private String pid;

    /**字段*/
    private String field;

    /**复选框*/
    private Boolean checked;

    /**是否展开*/
    private Boolean spread = true;

    /**连接*/
    private String href;

    /**子节点数据*/
    private List<LayuiTree> children;



}
