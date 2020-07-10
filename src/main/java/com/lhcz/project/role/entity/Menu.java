package com.lhcz.project.role.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * lp_menu
 * @author 4100
 */
@Data
@Table(name="lp_menu")
public class Menu implements Serializable {
    /**
     * 菜单编号
     */
    @Id
    private String id;

    /**
     * 父节点ID
     */
    private String pid;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 菜单连接地址
     */
    private String menuUrl;

    /**
     * 菜单样式
     */
    private String clazz;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 菜单状态（0:未启用；1:启用）
     */
    private String isUsing;

    /**
     * 菜单显示顺序
     */
    private Integer ranking;

    /**
     * 菜单级别
     */
    private Integer lev;

    /**
     * 是否是叶子节点
     */
    private Integer isLeaf;



}