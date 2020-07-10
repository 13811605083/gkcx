package com.lhcz.project.role.service.impl;

import com.lhcz.common.LayuiTree;
import com.lhcz.project.role.entity.Menu;
import com.lhcz.project.role.mapper.MenuMapper;
import com.lhcz.project.role.service.MenuService;
import com.lhcz.utils.StringUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author seifur
 */
@Service
public class MemuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    @Override
    public List<LayuiTree> getLayuiTreeList(String roleId) {
        //返回的结果
        List<LayuiTree> rs = new ArrayList<>();
        //返回的结果中间集合
        List<LayuiTree> rsTemp = new ArrayList<>();
        //所有可用的菜单集合
        List<Menu> menus = menuMapper.getUsingMemuList();
        //角色下所有的菜单列表
        Set<String> roleMenus = menuMapper.getMenuIds(roleId);
        //所有可用的菜单ID集合
        Set<String> allMenuIds = new HashSet<>();
        for(Menu menu : menus){
            allMenuIds.add(menu.getId());
            LayuiTree t = new LayuiTree();
            t.setId(menu.getId());
            t.setPid(menu.getPid());
            t.setTitle(menu.getMenuName());
            t.setSpread(true);
            if(roleMenus.contains(t.getId())){
                t.setChecked(true);
            }
            rsTemp.add(t);
        }
        for(LayuiTree child : rsTemp) {
            //一级节点
            if (!allMenuIds.contains(child.getPid())) {
                rs.add(child);
                //子节点
            } else {
                for (LayuiTree parent : rsTemp) {
                    //找到父节点
                    if (parent.getId().equals(child.getPid())) {
                        if (parent.getChildren() == null) {
                            List<LayuiTree> children = new ArrayList<>();
                            children.add(child);
                            parent.setChildren(children);
                        } else {
                            parent.getChildren().add(child);
                        }
                        break;
                    }
                }
            }
        }
        for(LayuiTree tree : rs){
            //一级节点有子节点的checked设成false,否则Layui的Tree渲染有问题
            if(!StringUtil.isNull(tree.getChildren())){
                tree.setChecked(false);
            }
        }

        return rs;
    }

    @Override
    public List<Menu> getUsingMemuList() {
        return menuMapper.getUsingMemuList();
    }

    @Override
    public List<Menu> getUsingMemuList(String userId) {
        return menuMapper.getUsingMemuListByUserId(userId);
    }
}
