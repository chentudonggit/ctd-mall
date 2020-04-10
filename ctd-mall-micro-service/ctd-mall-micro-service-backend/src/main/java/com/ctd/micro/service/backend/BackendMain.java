package com.ctd.micro.service.backend;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.CountDownLatch;

/**
 * BackendMain
 *
 * @author chentudong
 * @date 2020/4/10 23:38
 * @since 1.0
 */
@SpringBootApplication
@ComponentScan("com.ctd.mall.*")
public class BackendMain
{
    public static void main(String[] args) throws Exception
    {
        SpringApplicationBuilder builder = new SpringApplicationBuilder();
        builder.sources(BackendMain.class).web(WebApplicationType.SERVLET).bannerMode(Banner.Mode.OFF).run(args);
        CountDownLatch closeLatch = new CountDownLatch(1);
        closeLatch.await();
    }
}
