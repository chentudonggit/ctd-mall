package com.ctd.mall.micro.service.auth.service.redis.authorization;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.concurrent.TimeUnit;

/**
 * RedisAuthorizationCodeServices
 *
 * @author chentudong
 * @date 2020/3/14 13:36
 * @since 1.0
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices
{
    private RedisTemplate<String, Object> redisTemplate;

    public RedisTemplate<String, Object> getRedisTemplate()
    {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 替换JdbcAuthorizationCodeServices的存储策略
     * 将存储code到redis，并设置过期时间，10分钟
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication)
    {
        redisTemplate.opsForValue().set(redisKey(code), authentication, 10, TimeUnit.MINUTES);
    }

    @Override
    protected OAuth2Authentication remove(final String code)
    {
        String codeKey = redisKey(code);
        OAuth2Authentication token = (OAuth2Authentication) redisTemplate.opsForValue().get(codeKey);
        this.redisTemplate.delete(codeKey);
        return token;
    }

    /**
     * redis中 code key的前缀
     *
     * @param code code
     * @return String
     */
    private String redisKey(String code)
    {
        return "oauth:code:" + code;
    }
}
