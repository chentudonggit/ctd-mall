package com.ctd.mall.framework.auth.store.redis;

import com.ctd.mall.framework.auth.properties.security.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

/**
 * AuthRedisTokenStore
 *
 * @author chentudong
 * @date 2020/3/7 17:18
 * @since 1.0
 */
@Component
public class AuthRedisTokenStore
{
    @Autowired
    private RedisConnectionFactory connectionFactory;

    @Autowired
    private SecurityProperties securityProperties;

    public TokenStore tokenStore() {
        return new CustomRedisTokenStore(connectionFactory, securityProperties);
    }
}
