package com.lhcz.project.user.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * user_info
 * @author 4100
 */
@Data
@Table(name="lp_user_info")
public class UserInfo implements Serializable {
    /**
     * 用户ID
     */
    @Id
    private String userId;

    /**
     * 用户账号
     */
    private String userAccount;

    /**
     * 用户密码
     */
    private String userPwd;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 组织机构代码
     */
    private String areaCode;

    /**
     * 组织机构名称
     */
    private String areaName;

    /**
     * 用户级别1厅级，2地州级，3县市级
     */
    private String userLevel;

    /**
     * 用户组织机构ID
     */
    private String orgId;

    /**
     * 是否启动（1:是；0否）
     */
    private String isUsing;

    /**
     * 拥有的角色，冗余字段用作展示
     */
    private String roles;

    private String deptId;

    private String deptName;

}