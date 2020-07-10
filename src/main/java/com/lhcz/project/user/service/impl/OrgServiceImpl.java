package com.lhcz.project.user.service.impl;

import com.lhcz.project.user.entity.OrgInfo;
import com.lhcz.project.user.mapper.OrgInfoMapper;
import com.lhcz.project.user.service.OrgService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 41008
 */
@Service
public class OrgServiceImpl implements OrgService {

    @Resource
    OrgInfoMapper orgInfoMapper;

    @Override
    public OrgInfo getOrgInfo(String id) {
        return orgInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<OrgInfo> listOrgInfo() {
        Example example = new Example(OrgInfo.class);
        example.orderBy("ranking").asc();
        return orgInfoMapper.selectByExample(example);
    }

    @Override
    public List<OrgInfo> listOrgInfoClause() {
        Example example = new Example(OrgInfo.class);
        example.orderBy("ranking").asc();
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orgLev", "2");
        return orgInfoMapper.selectByExample(example);
    }
}
