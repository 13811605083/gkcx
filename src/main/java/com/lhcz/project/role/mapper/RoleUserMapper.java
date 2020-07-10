package com.lhcz.project.role.mapper;

import com.lhcz.common.MyBaseMapper;
import com.lhcz.project.role.entity.RoleUser;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 41008
 */
public interface RoleUserMapper extends MyBaseMapper<RoleUser> {

    /**
     * 删除用户所有角色
     * @param userId 用户ID
     */
    @Select("delete from lp_role_user where user_id = #{userId}")
    void deleteRoleUser(String userId);

    /**
     * 获取用户下的所有角色
     * @param userId 用户ID
     * @return List<String>
     */
    @Select("select distinct t.role_id from lp_role_user t where t.user_id = #{userId}")
    List<String> listSelectRoleTransfer(String userId);
}