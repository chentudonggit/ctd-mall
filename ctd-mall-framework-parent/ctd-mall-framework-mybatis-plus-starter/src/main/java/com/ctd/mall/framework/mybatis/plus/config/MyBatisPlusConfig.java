package com.ctd.mall.framework.mybatis.plus.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.ctd.mall.framework.mybatis.plus.config.handler.BaseMetaObjectHandler;
import com.ctd.mall.framework.mybatis.plus.injector.SqlInjector;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatisPlusConfig
 *
 * @author chentudong
 * @date 2020/3/8 9:14
 * @since 1.0
 */
@Configuration
@MapperScan(basePackages = {"com.ctd.mall.**.mapper.**", "com.ctd.mall.**.dao.**"})
public class MyBatisPlusConfig
{
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }

    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    @Bean
    public ISqlInjector setSqlInjector()
    {
        return new SqlInjector();
    }

    @Bean
    protected MetaObjectHandler setMetaObjectHandler()
    {
        return new BaseMetaObjectHandler();
    }
}
