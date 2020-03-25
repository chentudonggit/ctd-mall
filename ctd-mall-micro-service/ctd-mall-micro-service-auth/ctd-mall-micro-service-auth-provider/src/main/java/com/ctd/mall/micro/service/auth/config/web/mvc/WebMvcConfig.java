package com.ctd.mall.micro.service.auth.config.web.mvc;

import com.ctd.mall.micro.service.auth.resolver.client.ClientArgumentResolver;
import com.ctd.mall.micro.service.auth.resolver.token.TokenArgumentResolver;
import com.ctd.mall.micro.service.user.client.user.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * WebMvcConfig
 *
 * @author chentudong
 * @date 2020/3/26 0:15
 * @since 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport
{
    @Lazy
    @Autowired
    private UserClient userClient;
    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userClient));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver());
    }
}
