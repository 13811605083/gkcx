package com.lhcz.project.role.controller;


import com.alibaba.fastjson.JSONObject;
import com.lhcz.common.Constants;
import com.lhcz.common.LayuiTree;
import com.lhcz.common.PageInfo;
import com.lhcz.project.role.entity.Role;
import com.lhcz.project.role.service.MenuService;
import com.lhcz.project.role.service.RoleService;
import com.lhcz.utils.ControllerUtil;
import com.lhcz.utils.FastJsonConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * @author 41008
 */
@Controller
@Slf4j
@RequestMapping("/role")
public class RoleController {
    private @Resource StringRedisTemplate stringRedisTemplate;
    private @Resource RoleService roleService;
    private @Resource MenuService menuService;

    /**
     * 获取角色列表数据
     * @param request HttpServletRequest
     * @return 封装Grid数据
     */
    @ResponseBody
    @RequestMapping(value = "/listData")
    public String listData(HttpServletRequest request){
        try {
            PageInfo pageInfo = roleService.getRolePage(ControllerUtil.getPageInfo(request), ControllerUtil.getRequestParams(request));
            return FastJsonConvertUtil.obj2JsonWithDateFormat(pageInfo);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**判断角色是否存在*/
    @ResponseBody
    @GetMapping("/isExist/{id}")
    public Object isExist(@PathVariable("id")String id){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("valid", !roleService.isExist(id));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return jsonObj;
    }

    /**
     * 添加角色
     * @param role 角色对象
     * @return OK表示成功，ERROR表示失败
     */
    @ResponseBody
    @PostMapping("/addRole")
    public Object addRole(Role role){
        String key = "lcyzb-role-id:"+role.getId().toUpperCase();
        try {
            Boolean isAdd = stringRedisTemplate.hasKey(key);
            if(isAdd != null && isAdd){
                //重复提交直接返回
                return Constants.OK;
            }
            if(1 == roleService.addRole(role)){
                //设置10秒有效期
                stringRedisTemplate.opsForValue().set( key,Constants.OK, 10, TimeUnit.SECONDS);
                return Constants.OK;
            }
        } catch (Exception e) {
            stringRedisTemplate.delete(key);
            log.error(e.getMessage(), e);
        }
        return Constants.ERROR;
    }

    /**
     * 修改角色
     * @param role 角色对象
     * @return OK表示成功，ERROR表示失败
     */
    @ResponseBody
    @PutMapping("/editRole")
    public Object editRole(Role role){
        try {
            roleService.updateRole(role);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Constants.ERROR;
        }
        return Constants.OK;
    }

    /**删除角色*/
    @ResponseBody
    @DeleteMapping("/deleteRole/{id}")
    public Object deleteRole(@PathVariable("id")String id){
        try {
            int rs = roleService.deleteByPrimaryKey(id);
            if(rs == 1){
                return Constants.OK;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Constants.ERROR;
    }

    /**
     * 跳转到编辑页面
     * @param id 角色代码
     * @return 页面
     */
    @GetMapping(value = "/toEdit")
    public String toEdit(@RequestParam(name="id") String id, Map<String,Object> map){
        try {
            Role role = roleService.selectByPrimaryKey(id);
            map.put("role",role);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "role/role_edit";
    }

    /**
     * 跳转到菜单配置页面
     * @param roleId 角色代码
     * @return 页面
     */
    @GetMapping(value = "/toMenu/{roleId}")
    public String toMenu(@PathVariable("roleId") String roleId, Map<String,Object> map){
        try {
            map.put("roleId",roleId);
            List<LayuiTree> list = menuService.getLayuiTreeList(roleId);
            map.put("menuTree", list);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "role/role_menu";
    }

    @ResponseBody
    @PostMapping(value = "/addRoleMenu")
    public String addRoleMenu(@RequestParam String roleId,@RequestParam String menuIds){
        try{
            roleService.addRoleMenu(roleId,menuIds);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return Constants.ERROR;
        }
        return Constants.OK;
    }

    /**
     * 通用跳转
     * @param path 跳转路径
     * @return 页面
     */
    @GetMapping(value = "/to{path}")
    public String toPath(@PathVariable("path") String path){
        return "role/role_"+path.toLowerCase();
    }

}
