package com.lhcz.project.role.mapper;

import com.lhcz.common.MyBaseMapper;
import com.lhcz.project.role.entity.RoleMenu;
import org.apache.ibatis.annotations.Delete;

/**
 * @author 41008
 */
public interface RoleMenuMapper extends MyBaseMapper<RoleMenu> {

    /**
     * 通过角色ID删除角色菜单
     * @param roleId 角色ID
     * @return Integer
     */
    @Delete("delete from lp_role_menu where role_id = #{roleId}")
    Integer deleteRoleMenu(String roleId);
}