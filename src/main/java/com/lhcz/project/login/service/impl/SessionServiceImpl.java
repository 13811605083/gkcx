package com.lhcz.project.login.service.impl;


import com.alibaba.fastjson.JSONObject;
import com.lhcz.common.ConfigInfo;
import com.lhcz.common.Constants;
import com.lhcz.common.SessionBean;
import com.lhcz.project.login.service.SessionService;
import com.lhcz.utils.FastJsonConvertUtil;
import com.lhcz.utils.StringUtil;
import com.lhcz.utils.UUIDFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 将登录信息保存到redis中
 * @author 41008
 */
@Service
@Slf4j
public class SessionServiceImpl implements SessionService {


    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Resource
    private ConfigInfo configInfo;

    /**
     * 判断用户是否登录
     * @param key Constants.LCSESSIONID
     * @return boolean
     */
    @Override
    public Boolean isLoginFromCache(String key){
        String k = Constants.TOKEN + ":" + key;
        Boolean b = stringRedisTemplate.hasKey(k);
        if(b != null && b){
            stringRedisTemplate.expire( k, configInfo.getTimeout() , configInfo.getTimeUnit());
        }
        return b;

    }

    /**
     * 保存用户信息到redis中
     * @param key Constants.LCSESSIONID
     * @param bean SessionBean
     */
    @Override
    public void addSessionToCache(String key, SessionBean bean){
        String k = Constants.TOKEN + ":" + key;
        String v = FastJsonConvertUtil.obj2JsonWithNull(bean);
        stringRedisTemplate.opsForValue().set( k , v , configInfo.getTimeout() , configInfo.getTimeUnit());
    }

    /**
     * 从redis中取出用户信息
     * @param key Constants.LCSESSIONID
     * @return json格式用户信息
     */
    @Override
    public String getSessionStringFromCache(String key){
        String k = Constants.TOKEN + ":" + key;
        String r = stringRedisTemplate.opsForValue().get(k);
        //重新设置过期时间
        if(r != null){
            stringRedisTemplate.expire( k, configInfo.getTimeout(), configInfo.getTimeUnit());
        }
        return r;
    }

    /**
     * 从redis中取出用户信息
     * @param key Constants.LCSESSIONID
     * @return SessionBean
     */
    @Override
    public SessionBean getSessionObejctFromCache(String key){
        String k = Constants.TOKEN + ":" + key;
        String r = stringRedisTemplate.opsForValue().get(k);
        //重新设置过期时间
        if(r != null){
            stringRedisTemplate.expire( k, configInfo.getTimeout(), configInfo.getTimeUnit());
        }
        return FastJsonConvertUtil.json2Obj(r,SessionBean.class);
    }

    /**
     * 退出登录
     * @param key Constants.LCSESSIONID
     */
    @Override
    public void deleteSessionFromCache(String key){
        String k = Constants.TOKEN + ":" + key;
        stringRedisTemplate.delete(k);
    }

}
