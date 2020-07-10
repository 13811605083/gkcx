package com.lhcz.project.login.controller;


import com.lhcz.common.Constants;
import com.lhcz.common.SessionBean;
import com.lhcz.project.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.data.redis.connection.PoolException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author 41008
 */
@Controller
@Slf4j
public class LoginController {
    @Resource
    private UserService userService;

    /**用户登陆*/
    @PostMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes){
        try {
            String account = request.getParameter("account");
            String pwd = request.getParameter("password");
            if (StringUtils.isEmpty(account)) {
                redirectAttributes.addFlashAttribute("msg", "账号不能为空！");
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/";
            } else if (StringUtils.isEmpty(pwd)) {
                redirectAttributes.addFlashAttribute("msg", "密码不能为空！");
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/";
            }
            //登陆逻辑
            String result = userService.login(request, response);
            if (Constants.OK.equals(result)) {
                //登录成功
                SessionBean bean =  (SessionBean)request.getSession().getAttribute(Constants.SESSION_BEAN);
                if(bean.containsRole("HR_YZB")){
                    redirectAttributes.addFlashAttribute("hryzb","1");
                }else{
                    redirectAttributes.addFlashAttribute("hryzb","0");
                }
                return "redirect:/index";
            } else {//登录不成功
                redirectAttributes.addFlashAttribute("msg", result);
                redirectAttributes.addFlashAttribute("account", account);
                return "redirect:/";
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
            if(e instanceof MyBatisSystemException){
                redirectAttributes.addFlashAttribute("msg", "数据库连接失败！");
            }else if(e instanceof PoolException){
                redirectAttributes.addFlashAttribute("msg", "redis连接失败！");
            }
            return "redirect:/";
        }
    }

    /**用户登出*/
    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        try {
            userService.logout(request,response);
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return "redirect:/";
    }

    /**登录成功后跳转页面*/
    @GetMapping(value = "/index")
    public String index(Map<String,Object> map,HttpServletRequest request){
        return "index";
    }

    /**会话过期时跳转*/
    @RequestMapping(value = "/login")
    public String login(Map<String,String> map){
        map.put("msg", "会话已过期，请重新登录！");
        return "login";
    }

    /**根路径默认转登录页*/
    @RequestMapping(value = "/")
    public String rootPath(){
        return "login";
    }

    /**跳转页面通配符*/
    @GetMapping(value = "/{path}")
    public String path(@PathVariable("path") String path){
        return path;
    }
}
