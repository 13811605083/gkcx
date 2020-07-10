package com.lhcz.project.role.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * lp_role_menu
 * @author 4100
 */
@Data
@Table(name = "lp_role_menu")
public class RoleMenu implements Serializable {
    /**
     * 角色ID
     */
    private String roleId;

    /**
     * 菜单ID
     */
    private String menuId;

    /**
     * 菜单数据权限（1:全部；2部门；3个人）
     */
    private Integer dataAuthority;

    public RoleMenu(){
    }

    /**
     * 构造函数
     * @param roleId roleId
     * @param menuId menuId
     * @param dataAuthority dataAuthority
     */
    public RoleMenu(String roleId,String menuId,Integer dataAuthority){
        this.roleId = roleId;
        this.menuId = menuId;
        this.dataAuthority = dataAuthority;
    }

}