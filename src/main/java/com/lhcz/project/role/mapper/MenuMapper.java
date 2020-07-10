package com.lhcz.project.role.mapper;

import com.lhcz.common.MyBaseMapper;
import com.lhcz.project.role.entity.Menu;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * @author 41008
 */
public interface MenuMapper extends MyBaseMapper<Menu> {

    /**
     * 获取可用的菜单列表
     * @return List<Menu>
     */
    @Select("select * from lp_menu where is_using = '1' order by ranking")
    List<Menu> getUsingMemuList();

    /**
     * 获取可用的菜单列表
     * @param userId 用户ID
     * @return List<Menu>
     */
    @Select("select m.* \n" +
            "    from lp_menu m\n" +
            "    where m.is_using = '1' and m.id in \n" +
            "      (select distinct rm.menu_id from lp_role_menu rm where rm.role_id in \n" +
            "        (select distinct ru.role_id from lp_role_user ru \n" +
            "            left join lp_role r on(r.id=ru.role_id) where r.is_using='1' and ru.user_id = #{userId}) \n" +
            "      )\n" +
            "    order by ranking")
    List<Menu> getUsingMemuListByUserId(String userId);

    /**
     * 获取角色下的所有菜单ID
     * @param roleId 角色ID
     * @return Set<String>
     */
    @Select(" select distinct menu_id from lp_role_menu where role_id = #{roleId}")
    Set<String> getMenuIds(String roleId);


}