package com.ctd.mall.framework.mysql;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;

/**
 * MySqlAutoConfiguration
 *
 * @author chentudong
 * @date 2020/3/7 22:18
 * @since 1.0
 */
@Order(-1)
@Configuration
@ComponentScan
@PropertySource(value = {"classpath:/bootstrap.yml"}, ignoreResourceNotFound = true)
public class MySqlAutoConfiguration
{
}
