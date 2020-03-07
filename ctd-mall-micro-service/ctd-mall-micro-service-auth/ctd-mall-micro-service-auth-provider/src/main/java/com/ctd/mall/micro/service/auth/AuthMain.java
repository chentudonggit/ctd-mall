package com.ctd.mall.micro.service.auth;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.concurrent.CountDownLatch;

/**
 * AuthMain
 *
 * @author chentudong
 * @date 2020/3/7 21:59
 * @since 1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@EnableRedisHttpSession
@SpringBootApplication
@ComponentScan("com.ctd.mall.*")
public class AuthMain
{
    public static void main(String[] args) throws Exception
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(AuthMain.class).web(WebApplicationType.SERVLET).bannerMode(Banner.Mode.OFF).run(args);
        CountDownLatch closeLatch = new CountDownLatch(1);
        closeLatch.await();
    }
}
