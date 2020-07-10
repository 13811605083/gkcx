package com.lhcz.project.role.service.impl;

import com.github.pagehelper.PageHelper;
import com.lhcz.common.PageInfo;
import com.lhcz.common.Transfer;
import com.lhcz.project.role.entity.Role;
import com.lhcz.project.role.entity.RoleMenu;
import com.lhcz.project.role.entity.RoleUser;
import com.lhcz.project.role.mapper.RoleMapper;
import com.lhcz.project.role.mapper.RoleMenuMapper;
import com.lhcz.project.role.mapper.RoleUserMapper;
import com.lhcz.project.role.service.RoleService;
import com.lhcz.project.user.entity.UserInfo;
import com.lhcz.project.user.mapper.UserInfoMapper;
import com.lhcz.utils.StringUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author 41008
 */
@Service
public class RoleServiceImpl implements RoleService {
    private @Resource RoleMapper roleMapper;
    private @Resource RoleMenuMapper roleMenuMapper;
    private @Resource RoleUserMapper roleUserMapper;
    private @Resource UserInfoMapper userInfoMapper;

    @Override
    public PageInfo getRolePage(PageInfo pageInfo, Map<String, Object> requestParams) {
        PageHelper.startPage(pageInfo.getPage(),pageInfo.getLimit());
        List<Role> roleList = roleMapper.getRoleList(requestParams);
        int count = roleMapper.getRoleListCount(requestParams);
        pageInfo.setData(roleList);
        pageInfo.setCount(count);
        return pageInfo;
    }

    @Override
    public boolean isExist(String id) {
        int count = roleMapper.getCountById(StringUtil.trim(id));
        return count != 0;
    }

    @Override
    public Integer addRole(Role role) {
        role.setId(role.getId().toUpperCase());
        return roleMapper.insertSelective(role);
    }

    @Override
    public void updateRole(Role role) {
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public Role selectByPrimaryKey(String id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public int deleteByPrimaryKey(String id) {
        roleMenuMapper.deleteRoleMenu(id);
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void  addRoleMenu(String roleId, String menuIds) {
        roleMenuMapper.deleteRoleMenu(roleId);
        if(StringUtil.isNull(menuIds)){
            return ;
        }
        Role role = roleMapper.selectByPrimaryKey(roleId);
        String[] ids = menuIds.split(",");
        List<RoleMenu> roleMenuList = new ArrayList<>();
        for(String menuId : ids){
            roleMenuList.add(new RoleMenu(roleId,menuId,role.getDataAuthority()));
        }
        if(roleMenuList.size() > 0) {
            roleMenuMapper.insertList(roleMenuList);
        }
    }

    @Override
    public List<Transfer> listRoleTransfer() {
        Example example = new Example(Role.class);
        example.orderBy("ranking").asc();
        List<Role> roleList = roleMapper.selectByExample(example);
        List<Transfer> transferList = new ArrayList<>();
        for(Role role : roleList){
            Transfer t = new Transfer();
            t.setValue(role.getId());
            t.setTitle(role.getRoleName());
            t.setDisabled("0".equals(role.getIsUsing()));
            transferList.add(t);
        }
        return transferList;
    }

    @Override
    public List<String> listSelectRoleTransfer(String userId) {
       return roleUserMapper.listSelectRoleTransfer(userId);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public void addRoleUser(String userId, String roleIds) {
        UserInfo userInfo = userInfoMapper.selectByPrimaryKey(userId);
        StringBuilder roles = new StringBuilder();
        Example example = new Example(Role.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andIn("id", Arrays.asList(roleIds.split(",")));
        List<Role> roleList = roleMapper.selectByExample(example);
        List<RoleUser> roleUserList = new ArrayList<>();
        for(Role role : roleList){
            if(roles.length() > 0){
                roles.append(",");
            }
            roles.append(role.getRoleName());
            roleUserList.add(new RoleUser(role.getId(),userId));
        }
        userInfo.setRoles(roles.toString());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
        roleUserMapper.deleteRoleUser(userId);
        if(roleUserList.size() > 0){
            roleUserMapper.insertList(roleUserList);
        }
    }

    @Override
    public List<Role> getUseingRoleListByUserId(String userId) {
        return roleMapper.getUseingRoleListByUserId(userId);
    }
}
