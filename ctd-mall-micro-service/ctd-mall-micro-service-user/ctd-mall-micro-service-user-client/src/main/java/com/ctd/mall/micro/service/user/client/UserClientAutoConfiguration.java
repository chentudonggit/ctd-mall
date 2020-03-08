package com.ctd.mall.micro.service.user.client;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * UserClientAutoConfiguration
 *
 * @author chentudong
 * @date 2020/3/8 13:24
 * @since 1.0
 */
@EnableFeignClients
@ComponentScan
@Configuration
public class UserClientAutoConfiguration
{
}
