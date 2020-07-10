package com.lhcz.common;

import com.lhcz.project.role.entity.Menu;
import com.lhcz.project.role.entity.Role;
import com.lhcz.project.user.entity.OrgInfo;
import com.lhcz.utils.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author 41008
 */
@Data
public class SessionBean implements Serializable {

    private static final long serialVersionUID = 1L;

    /**数据权限（全部）*/
    public static final int ALL_AUTHORITY = 1;
    /**数据权限（本机构）*/
    public static final int ORG_AUTHORITY = 2;
    /**数据权限（个人）*/
    public static final int SELF_AUTHORITY = 3;

    /**用户uuid*/
    private String userId;

    /**用户登录账号*/
    private String userAccount;

    /**用户名称*/
    private String userName;

    /**用户所属机构*/
    private String orgName;

    /**组织机构信息*/
    private OrgInfo orgInfo;

    /**
     * 数据权限（1:全部；2部门；3个人）
     */
    private Integer dataAuthority = SessionBean.SELF_AUTHORITY;

    /**用户的菜单数据*/
    private List<Menu> menuList;

    /**用户的角色数据*/
    private List<Role> roleList;

    public Boolean containsRole(String roleId){
        if(StringUtil.isNull(roleList)){
            return false;
        }
        for(Role role : roleList){
            if(role.getId().equals(roleId)){
                return true;
            }
        }
        return false;
    }

}
