package com.lhcz;

import com.alibaba.druid.pool.DruidDataSource;
import com.lhcz.compontent.LoginHandlerInterceptor;
import com.lhcz.project.login.service.SessionService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tk.mybatis.spring.annotation.MapperScan;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 41008
 */
@Configuration
@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "com.lhcz.project.*.mapper")
public class GkcxApplication implements WebMvcConfigurer {


    @Resource
    private SessionService sessionService;

    public static void main(String[] args) {
        SpringApplication.run(GkcxApplication.class, args);
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource(){
        return new DruidDataSource();
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }

    /**
     * 注册一个登陆拦截器
     * @param registry InterceptorRegistry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> exList = new ArrayList<>();
        exList.add("/webjars/**");
        exList.add("/static/**");
        exList.add("/login");
        exList.add("/logout");
        exList.add("/error");
        exList.add("/");
        InterceptorRegistration registration = registry.addInterceptor(new LoginHandlerInterceptor(sessionService));
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(exList);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
