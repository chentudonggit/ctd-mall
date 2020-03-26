package com.ctd.mall.micro.service.user.config.web.mvc;

import com.ctd.mall.framework.common.core.resolver.client.ClientArgumentResolver;
import com.ctd.mall.micro.service.user.resolver.token.TokenArgumentResolver;
import com.ctd.mall.micro.service.user.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * WebMvcConfig
 *
 * @author chentudong
 * @date 2020/3/27 0:28
 * @since 1.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport
{
    @Autowired
    private UserService userService;
    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver());
    }
}
