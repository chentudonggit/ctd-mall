package com.ctd.mall.micro.service.auth.config.client;

import com.ctd.springboot.redis.repository.RedisRepository;
import com.ctd.mall.micro.service.auth.service.redis.RedisClientDetailsService;
import com.ctd.mall.micro.service.auth.service.redis.authorization.RedisAuthorizationCodeServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * ClientDetailsConfig
 *
 * @author chentudong
 * @date 2020/3/14 13:35
 * @since 1.0
 */
@Configuration
@Import(RedisRepository.class)
public class ClientDetailsConfig
{
    @Resource
    private DataSource dataSource;
    @Autowired
    private RedisRepository redisRepository;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 声明 ClientDetails实现
     */
    @Bean
    public RedisClientDetailsService clientDetailsService()
    {
        return new RedisClientDetailsService(dataSource, redisRepository);
    }

    @Bean
    public RandomValueAuthorizationCodeServices authorizationCodeServices()
    {
        return new RedisAuthorizationCodeServices(redisRepository);
    }

    @Bean
    public RedisRepository redisRepository()
    {
        return new RedisRepository(redisTemplate);
    }
}
