package com.ctd.mall.micro.service.user.config.web.mvc;

import com.ctd.mall.micro.service.user.resolver.token.TokenArgumentResolver;
import com.ctd.mall.micro.service.user.service.user.UserService;
import com.ctd.springboot.common.core.resolver.client.ClientArgumentResolver;
import com.ctd.springboot.swagger.config.SwaggerWebMvcConfig;
import com.ctd.springboot.web.converter.jackson.JacksonHttpMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;

import java.util.List;

/**
 * WebMvcConfig
 *
 * @author chentudong
 * @date 2020/3/27 0:28
 * @since 1.0
 */
@Configuration
public class WebMvcConfig extends SwaggerWebMvcConfig
{
    @Autowired
    private UserService userService;

    /**
     * Token参数解析
     *
     * @param argumentResolvers 解析类
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers)
    {
        //注入用户信息
        argumentResolvers.add(new TokenArgumentResolver(userService));
        //注入应用信息
        argumentResolvers.add(new ClientArgumentResolver());
    }

    @Override
    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        super.configureMessageConverters(converters);
        converters.add(new JacksonHttpMessageConverter());
    }
}
