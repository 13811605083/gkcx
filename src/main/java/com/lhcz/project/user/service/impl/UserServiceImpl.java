package com.lhcz.project.user.service.impl;

import com.github.pagehelper.PageHelper;
import com.lhcz.common.ConfigInfo;
import com.lhcz.common.Constants;
import com.lhcz.common.PageInfo;
import com.lhcz.common.SessionBean;
import com.lhcz.project.login.service.SessionService;
import com.lhcz.project.role.entity.Menu;
import com.lhcz.project.role.entity.Role;
import com.lhcz.project.role.service.MenuService;
import com.lhcz.project.role.service.RoleService;
import com.lhcz.project.user.entity.OrgInfo;
import com.lhcz.project.user.entity.UserInfo;
import com.lhcz.project.user.mapper.UserInfoMapper;
import com.lhcz.project.user.service.OrgService;
import com.lhcz.project.user.service.UserService;
import com.lhcz.utils.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author 4100
 */
@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Resource
    private UserInfoMapper userInfoMapper;

    @Resource
    private OrgService orgService;

    @Resource
    private MenuService menuService;

    @Resource
    private RoleService roleService;

    @Resource
    private SessionService sessionSevice;

    @Resource
    private ConfigInfo configInfo;

    @Override
    public UserInfo getUserInfo(String id){
        return userInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public PageInfo getUserInfoPage(HttpServletRequest request) {
        PageInfo pageInfo = ControllerUtil.getPageInfo(request);
        PageHelper.startPage(pageInfo.getPage(),pageInfo.getLimit());
        Example example = new Example(UserInfo.class);
        example.orderBy("areaCode");
        Example.Criteria criteria = example.createCriteria();
        //封装检索条件
        StringUtil.resolverParameters( criteria, request);
        List<UserInfo> userInfoList = userInfoMapper.selectByExample(example);
        int count = userInfoMapper.selectCountByExample(example);
        pageInfo.setData(userInfoList);
        pageInfo.setCount(count);
        return pageInfo;
    }

    @Override
    public Integer addUserInfo(UserInfo userInfo) {
        userInfo.setUserId(UUIDFactory.getUuid());
        OrgInfo orgInfo = orgService.getOrgInfo(userInfo.getOrgId());
        userInfo.setAreaCode(orgInfo.getOrgCode());
        userInfo.setAreaName(orgInfo.getOrgName());
        userInfo.setUserLevel(orgInfo.getOrgLev());
        userInfo.setUserPwd(EncodeUtils.encodePwd(userInfo.getUserPwd()));
        return userInfoMapper.insertSelective(userInfo);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        OrgInfo orgInfo = orgService.getOrgInfo(userInfo.getOrgId());
        userInfo.setAreaCode(orgInfo.getOrgCode());
        userInfo.setAreaName(orgInfo.getOrgName());
        userInfo.setUserLevel(orgInfo.getOrgLev());
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

    @Override
    public Integer deleteByPrimaryKey(String id) {
        return userInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public boolean isExist(String account) {
        int count =  userInfoMapper.getCountByAccount(account);
        return count != 0;
    }

    @Override
    public String login(HttpServletRequest request, HttpServletResponse response) {
        String account = request.getParameter("account");
        String pwd = request.getParameter("password");
        String key = CookieUtils.getLcSessionId(request,response);
        HttpSession session = request.getSession();
        if(Constants.ADMIN.equals(account)){
            //系统管理员登陆
            if (configInfo.getPwd().equals(pwd)){
                //密码正确
                SessionBean bean = new SessionBean();
                bean.setUserId("admin");
                bean.setUserAccount("admin");
                bean.setUserName("admin");
                bean.setOrgName("管理员");
                bean.setDataAuthority(SessionBean.ALL_AUTHORITY);
                List<Menu> menuList = menuService.getUsingMemuList();
                bean.setMenuList(menuList);
                sessionSevice.addSessionToCache(key , bean);
                session.setAttribute(Constants.SESSION_BEAN,bean);
                session.setMaxInactiveInterval(-1);
                return Constants.OK;
            }else{
                //密码错误
                return "账号或密码错误！";
            }
        }
        //普通用户登陆
        UserInfo userInfo = userInfoMapper.selectByUserAccount(account);
        if(userInfo != null){
            //MD5后转16进制
            String hex = EncodeUtils.encodePwd(pwd);
            assert hex != null;
            if(pwd.equals(userInfo.getUserPwd()) || hex.equals(userInfo.getUserPwd())){
                if("0".equals(userInfo.getIsUsing())){
                    return "该用户已停用，请与管理员联系！";
                }

                OrgInfo orgInfo = orgService.getOrgInfo(userInfo.getOrgId());
                if(orgInfo == null){
                    return "该用户未分配所属组织机构，无法登陆！";
                }
                //密码正确
                SessionBean bean = new SessionBean();
                bean.setOrgInfo(orgInfo);
                bean.setUserId(userInfo.getUserId());
                bean.setUserAccount(userInfo.getUserAccount());
                bean.setUserName(userInfo.getUserName());
                bean.setOrgName(userInfo.getAreaName());
                List<Menu> menuList = menuService.getUsingMemuList(bean.getUserId());
                bean.setMenuList(menuList);
                List<Role> roleList = roleService.getUseingRoleListByUserId(bean.getUserId());
                for(Role role : roleList){
                    //设置数据权限
                    if(role.getDataAuthority() < bean.getDataAuthority()){
                        bean.setDataAuthority(role.getDataAuthority());
                    }
                }
                bean.setRoleList(roleList);
                sessionSevice.addSessionToCache( key , bean);
                session.setAttribute( Constants.SESSION_BEAN, bean);
                session.setMaxInactiveInterval(-1);
                return Constants.OK;
            }
        }else{
            return "账号不存在！";
        }
        return "账号和密码不匹配，请重新输入！";
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        String token = CookieUtils.getLcSessionId(request,response);
        sessionSevice.deleteSessionFromCache(token);
        request.getSession().removeAttribute(Constants.SESSION_BEAN);
    }

    @Override
    public boolean checkPwd(HttpServletRequest request, HttpServletResponse response,String password) {
        SessionBean bean = (SessionBean) request.getSession().getAttribute(Constants.SESSION_BEAN);
        String userId = bean.getUserId();
        password = EncodeUtils.encodePwd(password);
        return userInfoMapper.checkPwd(userId,password);
    }

    @Override
    public void updatePassword(HttpServletRequest request, HttpServletResponse response) {
        String pwd = request.getParameter("userPwd");
        String key = CookieUtils.getLcSessionId(request,response);
        SessionBean bean = sessionSevice.getSessionObejctFromCache(key);
        String userId = bean.getUserId();
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setUserPwd(EncodeUtils.encodePwd(pwd));
        userInfoMapper.updateByPrimaryKeySelective(userInfo);
    }

}
