package com.lhcz.compontent;

import com.lhcz.common.Constants;
import com.lhcz.common.SessionBean;
import com.lhcz.project.login.service.SessionService;
import com.lhcz.utils.CookieUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截器
 * @author 41008
 */
@Log4j2
public class LoginHandlerInterceptor implements HandlerInterceptor {

    private SessionService sessionSevice;

    public LoginHandlerInterceptor(SessionService sessionSevice){
        this.sessionSevice = sessionSevice;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String accessUri = request.getRequestURI();
        String contextPath = request.getContextPath();

        //不需要过滤的资源
        String[] ex = {".js",".css"};
        for(String s : ex){
            if(accessUri.endsWith(s)){
                return true;
            }
        }
        String key = CookieUtils.getLcSessionId(request,response);
        Boolean isLogin = sessionSevice.isLoginFromCache(key);
        SessionBean sessionBean = (SessionBean)request.getSession().getAttribute(Constants.SESSION_BEAN);
        if(isLogin != null && isLogin){
            //已登录成功-会话在有效期内
            if(sessionBean == null){
                //为当前Session设置用户信息
                SessionBean bean = sessionSevice.getSessionObejctFromCache(key);
                request.getSession().setAttribute(Constants.SESSION_BEAN, bean);
                request.getSession().setMaxInactiveInterval(-1);
            }
            return true;
        }else{
            if(sessionBean != null){
                //之前登陆过，但会话已过期
                response.sendRedirect(contextPath+"/login");
            }else{
                //未登录
                response.sendRedirect(contextPath);
            }
            return false;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
