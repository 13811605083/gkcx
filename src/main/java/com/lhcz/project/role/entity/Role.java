package com.lhcz.project.role.entity;

import com.lhcz.common.SessionBean;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * lp_role
 * @author 4100
 */
@Data
@Table(name="lp_role")
public class Role implements Serializable {

    /**
     * 角色ID
     */
    @Id
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 数据权限（1:全部；2部门；3个人）
     */
    private Integer dataAuthority;

    /**
     * 是否启动（1:是；0否）
     */
    private String isUsing;

    /**
     * 排序
     */
    private Integer ranking;


    public String getDataAuthorityView() {
        if (dataAuthority == SessionBean.ALL_AUTHORITY) {
            return "全部";
        }
        if (dataAuthority == SessionBean.ORG_AUTHORITY) {
            return "本机构";
        }
        if (dataAuthority == SessionBean.SELF_AUTHORITY) {
            return "个人";
        }
        return "-";
    }

}