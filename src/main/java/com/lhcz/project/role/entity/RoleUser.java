package com.lhcz.project.role.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * lp_role_menu
 * @author 4100
 */
@Data
@Table(name = "lp_role_user")
public class RoleUser implements Serializable {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String userId;

    public RoleUser(){

    }

    /**
     * 构造函数
     * @param roleId roleId
     * @param userId userId
     */
    public RoleUser(String roleId,String userId){
        this.roleId = roleId;
        this.userId = userId;
    }
}