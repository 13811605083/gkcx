package com.lhcz.project.role.mapper;

import com.lhcz.common.MyBaseMapper;
import com.lhcz.project.role.entity.Role;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @author 41008
 */
public interface RoleMapper extends MyBaseMapper<Role> {

    /**
     * 通过条件查询角色列表数据
     * @param map 入参
     * @return 列表
     */
    @Select("select * from lp_role order by ranking")
    List<Role> getRoleList(Map<String, Object> map);

    /**
     * 通过条件查询角色列表总数
     * @param map 入参
     * @return 数量
     */
    @Select("select count(id) from lp_role")
    Integer getRoleListCount(Map<String, Object> map);

    /**
     * 判断ID是否存在
     * @param id 角色代码
     * @return 数量
     */
    @Select("select count(id) from lp_role where id = #{id}")
    Integer getCountById(String id);

    /**
     * 获取用户下的所有角色
     * @param userId 用户ID
     * @return List<Role>
     */
    @Select("select r.id, r.role_name, r.data_authority, r.is_using, r.ranking \n" +
            "    from lp_role_user ru left join  lp_role r on(ru.role_id=r.id) \n" +
            "    where ru.user_id=#{userId} and r.is_using='1'\n" +
            "    order by r.ranking")
    List<Role> getUseingRoleListByUserId(String userId);


}