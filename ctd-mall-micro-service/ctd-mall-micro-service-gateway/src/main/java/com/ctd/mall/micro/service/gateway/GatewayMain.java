package com.ctd.mall.micro.service.gateway;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;

/**
 * GatewayMain
 *
 * @author chentudong
 * @date 2020/3/8 14:02
 * @since 1.0
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("com.ctd.mall.*")
public class GatewayMain
{
    public static void main(String[] args) throws Exception
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(GatewayMain.class).web(WebApplicationType.REACTIVE).bannerMode(Banner.Mode.OFF).run(args);
        CountDownLatch closeLatch = new CountDownLatch(1);
        closeLatch.await();
    }
}
