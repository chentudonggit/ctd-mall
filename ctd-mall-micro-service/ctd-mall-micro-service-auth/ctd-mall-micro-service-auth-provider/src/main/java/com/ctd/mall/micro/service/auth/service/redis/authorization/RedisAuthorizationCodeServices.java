package com.ctd.mall.micro.service.auth.service.redis.authorization;

import com.alibaba.fastjson.JSON;
import com.ctd.mall.framework.redis.repository.RedisRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

/**
 * RedisAuthorizationCodeServices
 *
 * @author chentudong
 * @date 2020/3/14 13:36
 * @since 1.0
 */
public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices
{
    private final RedisRepository redisRepository;

    public RedisAuthorizationCodeServices(RedisRepository redisRepository)
    {
        this.redisRepository = redisRepository;
    }


    /**
     * 替换JdbcAuthorizationCodeServices的存储策略
     * 将存储code到redis，并设置过期时间，10分钟
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication)
    {
        redisRepository.setExpire(redisKey(code), authentication,10);
    }

    @Override
    protected OAuth2Authentication remove(final String code)
    {
        String codeKey = redisKey(code);
        String tokenValue = redisRepository.get(codeKey);
        OAuth2Authentication token = null;
        if(StringUtils.isNotBlank(tokenValue))
        {
            token = JSON.parseObject(tokenValue, OAuth2Authentication.class);
            this.redisRepository.del(codeKey);
        }
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
