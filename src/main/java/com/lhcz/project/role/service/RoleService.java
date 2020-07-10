package com.lhcz.project.role.service;

import com.lhcz.common.PageInfo;
import com.lhcz.common.Transfer;
import com.lhcz.project.role.entity.Role;

import java.util.List;
import java.util.Map;

/**
 * @author 4100
 */
public interface RoleService {

    /**
     * 获取角色列表信息
     * @param pageInfo 封装的PageInfo
     * @param requestParams 查询参数
     * @return 封装的PageInfo
     */
    PageInfo getRolePage(PageInfo pageInfo, Map<String, Object> requestParams);

    /**
     * 判断角色编号是否存在
     * @param id 角色编号
     * @return boolean
     */
    boolean isExist(String id);

    /**
     * 添加角色
     * @param role 角色对象
     * @return Integer
     */
    Integer addRole(Role role) ;

    /**
     * 修改角色
     * @param role 角色对象
     */
    void updateRole(Role role) ;

    /**
     * 通过主键查询实例
     * @param id 角色代码
     * @return 角色对象
     */
    Role selectByPrimaryKey(String id);

    /**
     * 删除角色
     * @param id 角色代码
     * @return 删除数量
     */
    int deleteByPrimaryKey(String id) ;

    /**
     * 添加角色菜单
     * @param roleId 角色ID
     * @param menuIds 菜单IDS
     */
    void addRoleMenu(String roleId,String menuIds);

    /**
     * 获取所有的角色信息转成lauui穿梭框结构
     * @return List<Transfer>
     */
    List<Transfer> listRoleTransfer();

    /**
     * 获取用户下的所有角色ID
     * @param userId 用户ID
     * @return List<Transfer>
     */
    List<String> listSelectRoleTransfer(String userId);

    /**
     * 为用户分配角色
     * @param userId 用户ID
     * @param roleIds 角色ID
     */
    void addRoleUser(String userId,String roleIds);

    /**
     * 获取用户下的所有角色
     * @param userId 用户ID
     * @return List<Role>
     */
    List<Role> getUseingRoleListByUserId(String userId);
}
