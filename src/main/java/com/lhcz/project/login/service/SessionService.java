package com.lhcz.project.login.service;

import com.lhcz.common.SessionBean;

import javax.servlet.http.HttpServletRequest;

/**
 * 将登录信息保存到redis中
 * @author 41008
 */
public interface SessionService {

    /**
     * 判断用户是否登录
     * @param key Constants.LCSESSIONID
     * @return boolean
     */
    Boolean isLoginFromCache(String key);

    /**
     * 保存用户信息到redis中
     * @param key Constants.LCSESSIONID
     * @param bean SessionBean
     */
    void addSessionToCache(String key, SessionBean bean);

    /**
     * 从redis中取出用户信息
     * @param key Constants.LCSESSIONID
     * @return json格式用户信息
     */
    String getSessionStringFromCache(String key);

    /**
     * 从redis中取出用户信息
     * @param key Constants.LCSESSIONID
     * @return SessionBean
     */
    SessionBean getSessionObejctFromCache(String key);

    /**
     * 退出登录
     * @param key Constants.LCSESSIONID
     */
    void deleteSessionFromCache(String key);


}
