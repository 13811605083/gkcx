package com.lhcz.project.user.service;

import com.lhcz.common.PageInfo;
import com.lhcz.project.user.entity.UserInfo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author bmy
 */
public interface UserService {


    /**
     * 通过主键获取实例
     * @param id 用户的ID
     * @return UserInfo
     */
    UserInfo getUserInfo(String id);

    /**
     * 获取用户列表信息
     * @param request HttpServletRequest
     * @return PageInfo
     */
    PageInfo getUserInfoPage(HttpServletRequest request);

    /**
     * 添加用户数数据
     * @param userInfo 用户对象
     * @return Integer
     */
    Integer addUserInfo(UserInfo userInfo);

    /**
     * 修改用户
     * @param userInfo 用户对象
     */
    void updateUserInfo(UserInfo userInfo);

    /**
     * 通过主键删除用户
     * @param id 主键
     * @return Integer
     */
    Integer deleteByPrimaryKey(String id) ;

    /**
     * 判断账号是否存在
     * @param account 账号
     * @return boolean
     */
    boolean isExist(String account);

    /**
     * 用户登陆
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @return OK:登陆成功，其他登陆失败
     */
    String login(HttpServletRequest request, HttpServletResponse response);

    /**
     * 用户登出
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    void logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 判断修改的密码是否和原有密码一致
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param password 密码
     */
    boolean checkPwd(HttpServletRequest request, HttpServletResponse response, String password);
    /**
     * 更新密码
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    void updatePassword(HttpServletRequest request, HttpServletResponse response);
}
