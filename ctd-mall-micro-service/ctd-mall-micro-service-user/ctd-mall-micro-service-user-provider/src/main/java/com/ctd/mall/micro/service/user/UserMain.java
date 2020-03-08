package com.ctd.mall.micro.service.user;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;

/**
 * UserMain
 *
 * @author chentudong
 * @date 2020/3/8 9:38
 * @since 1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.ctd.mall.*")
public class UserMain
{
    public static void main(String[] args) throws Exception
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(UserMain.class).web(WebApplicationType.SERVLET).bannerMode(Banner.Mode.OFF).run(args);
        CountDownLatch closeLatch = new CountDownLatch(1);
        closeLatch.await();
    }
}
