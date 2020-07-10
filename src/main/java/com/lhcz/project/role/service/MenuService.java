package com.lhcz.project.role.service;

import com.lhcz.common.LayuiTree;
import com.lhcz.project.role.entity.Menu;

import java.util.List;

/**
 * @author 4100
 */
public interface MenuService {

    /**s
     * 获取layui结构的树
     * @param roleId 角色ID
     * @return List<Menu>
     */
    List<LayuiTree> getLayuiTreeList(String roleId);

    /**
     * 获取所有可用的菜单数据
     * @return List<Menu>
     */
    List<Menu> getUsingMemuList();

    /**
     * 获取用户下所有可用的菜单数据
     * @param userId 用户ID
     * @return List<Menu>
     */
    List<Menu> getUsingMemuList(String userId);

}
