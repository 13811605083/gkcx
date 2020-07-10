package com.lhcz.utils;

import com.lhcz.common.Constants;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Cookie工具集
 * @author 41008
 */
public class CookieUtils {

    /**
     * 获取 CSESSIONID
     *
     * @return String
     */
    public static String getLcSessionId(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (Constants.LCSESSIONID.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        String uuid = UUIDFactory.getUuid();
        Cookie cookie = new Cookie(Constants.LCSESSIONID, uuid);
        //设置存活时间 -1:关闭浏览器销毁 0:立即销毁 其他:存活时间
        cookie.setMaxAge(-1);
        //设置路径
        cookie.setPath("/");
        //设置跨域
        //cookie.setDomain("lhcz.com");
        //写回浏览器
        response.addCookie(cookie);
        return uuid;
    }

}
