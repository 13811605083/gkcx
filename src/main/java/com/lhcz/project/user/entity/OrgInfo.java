package com.lhcz.project.user.entity;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * lp_org_info
 * @author 4100
 */
@Data
@Table(name="lp_org_info")
public class OrgInfo implements Serializable {
    /**
     * ID
     */
    @Id
    private String id;

    /**
     * 组织机构代码
     */
    private String orgCode;

    /**
     * 组织机构名称
     */
    private String orgName;

    /**
     * 级别(1:总公司；2分公司)
     */
    private String orgLev;

    /**
     * 关键字
     */
    private String keyWord;

    /**
     * 排序
     */
    private Integer ranking;


}