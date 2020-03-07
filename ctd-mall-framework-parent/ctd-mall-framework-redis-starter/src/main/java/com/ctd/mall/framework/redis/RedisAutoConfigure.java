package com.ctd.mall.framework.redis;

import com.ctd.mall.framework.redis.properties.cache.CacheManagerProperties;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * RedisAutoConfigure
 *
 * @author chentudong
 * @date 2020/3/7 21:27
 * @since 1.0
 */
@EnableConfigurationProperties({RedisProperties.class, CacheManagerProperties.class})
@EnableCaching
@Configuration
public class RedisAutoConfigure
{
}
