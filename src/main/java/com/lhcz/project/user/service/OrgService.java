package com.lhcz.project.user.service;

import com.lhcz.project.user.entity.OrgInfo;

import java.util.List;

/**
 * @author bmy
 */
public interface OrgService {


    /**
     * 通过主键获取实例
     * @param id 主键
     * @return OrgInfo
     */
    OrgInfo getOrgInfo(String id);

    /**
     * 获取组织机构列表
     * @return List<OrgInfo>
     */
    List<OrgInfo> listOrgInfo();


    /**
     * 获取组织机构列表--分公司
     * @return List<OrgInfo>
     */
    List<OrgInfo> listOrgInfoClause();

}
