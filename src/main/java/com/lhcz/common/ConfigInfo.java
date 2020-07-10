package com.lhcz.common;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author 41008
 */

@Data
@Component
@ConfigurationProperties(prefix = "mfg")
public class ConfigInfo implements Serializable {

    /**系统管理员密码*/
    private String pwd = "123456";

    /**登录过期时间(默认单位分钟)*/
    private int timeout = 30;

    /**登录过期时间单位--默认分钟(TimeUnit.MINUTES)*/
    private TimeUnit timeUnit = TimeUnit.MINUTES;

    /**app用户登录超时时间(单位默认天)*/
    private int appTimeout = 30;

    /**app登录过期时间单位--默认天(TimeUnit.DAYS)*/
    private TimeUnit appTimeUnit = TimeUnit.DAYS;


}
