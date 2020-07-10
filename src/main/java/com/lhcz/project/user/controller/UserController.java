package com.lhcz.project.user.controller;


import com.alibaba.fastjson.JSONObject;
import com.lhcz.common.Constants;
import com.lhcz.common.PageInfo;
import com.lhcz.common.Transfer;
import com.lhcz.project.role.service.RoleService;
import com.lhcz.project.user.entity.UserInfo;
import com.lhcz.project.user.service.OrgService;
import com.lhcz.project.user.service.UserService;
import com.lhcz.utils.FastJsonConvertUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 41008
 */
@Controller
@Log4j2
@RequestMapping("/user")
public class UserController{

    private @Resource StringRedisTemplate stringRedisTemplate;
    private @Resource UserService userService;
    private @Resource OrgService orgService;
    private @Resource RoleService roleService;

    /**用户列表数据*/
    @ResponseBody
    @RequestMapping(value = "/listData")
    public String listData(HttpServletRequest request){
        try {
            PageInfo pageInfo = userService.getUserInfoPage(request);
            return FastJsonConvertUtil.obj2JsonWithDateFormat(pageInfo);
        }catch(Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
    /**判断账号是否存在*/
    @ResponseBody
    @GetMapping("/isExist/{account}")
    public Object isExist(@PathVariable("account")String account){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("valid", !userService.isExist(account));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return jsonObj;
    }

    /**判断密码是否与现有密码相同*/
    @ResponseBody
    @GetMapping("/checkPW/{password}")
    public Object checkPwd(@PathVariable("password")String password, HttpServletRequest request, HttpServletResponse response){
        JSONObject jsonObj = new JSONObject();
        try {
            jsonObj.put("valid", userService.checkPwd(request,response,password));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return jsonObj;
    }
    /**添加用户*/
    @ResponseBody
    @PostMapping("/addUserInfo")
    public Object addUserInfo(UserInfo userInfo){
        String key = "lcyzb-userInfo-userId:"+userInfo.getUserAccount();
        try {
            Boolean isAdd = stringRedisTemplate.hasKey(key);
            if(isAdd != null && isAdd){
                //重复提交直接返回
                return Constants.OK;
            }
            if(1 == userService.addUserInfo(userInfo)){
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

    @ResponseBody
    @PutMapping("/editUserInfo")
    public Object editUserInfo(UserInfo userInfo){
        try {
            userService.updateUserInfo(userInfo);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Constants.ERROR;
        }
        return Constants.OK;
    }

    /**修改密码*/
    @ResponseBody
    @PostMapping("/editPW")
    public Object editPwd(HttpServletRequest request, HttpServletResponse response){
        try {
            userService.updatePassword(request,response);
            return Constants.OK;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Constants.ERROR;
    }
    /**删除用户*/
    @ResponseBody
    @DeleteMapping("/deleteUser/{id}")
    public Object deleteUser(@PathVariable("id")String id){
        try {
            int rs = userService.deleteByPrimaryKey(id);
            if(rs == 1){
                return Constants.OK;
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return Constants.ERROR;
    }

    /**跳转列表页面*/
    @GetMapping(value = "/toList")
    public String toList(Map<String,Object> map){
        map.put( "orgList", orgService.listOrgInfo());
        return "user/user_list";
    }

    /**跳转添加页面*/
    @GetMapping(value = "/toAdd")
    public String toAdd(Map<String,Object> map){
        map.put( "orgList", orgService.listOrgInfo());
        return "user/user_add";
    }

    /**跳转编辑页面*/
    @GetMapping(value = "/toEdit")
    public String toEdit(@RequestParam(name="id") String id, Map<String,Object> map){
        map.put( "orgList", orgService.listOrgInfo());
        UserInfo userInfo = userService.getUserInfo(id);
        map.put("userInfo",userInfo);
        return "user/user_edit";
    }

    /**跳转到用户角色配置页面*/
    @GetMapping(value = "/toRole/{userId}")
    public String toMenu(@PathVariable("userId") String userId, Map<String,Object> map){
        try {
            map.put("userId",userId);
            List<Transfer> roleData = roleService.listRoleTransfer();
            map.put("roleData",roleData);
            List<String> selectRoleData = roleService.listSelectRoleTransfer(userId);
            map.put("selectRoleData",selectRoleData);
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
        return "user/user_role";
    }

    /**为用户分配角色*/
    @ResponseBody
    @PostMapping(value = "/addRoleUser")
    public String addRoleUser(@RequestParam String roleIds,@RequestParam String userId){
        try{
            roleService.addRoleUser(userId,roleIds);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return Constants.ERROR;
        }
        return Constants.OK;
    }


    /**通用跳转*/
    @GetMapping(value = "/to{path}")
    public String path(@PathVariable("path")String path){
        return "user/user_"+path.toLowerCase();
    }


}
