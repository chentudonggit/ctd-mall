package com.ctd.mall.framework.auth.properties;

import com.ctd.mall.framework.auth.enums.token.TokenStoreType;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * TokenStoreProperties
 *
 * @author chentudong
 * @date 2020/3/7 20:59
 * @since 1.0
 */
@ConfigurationProperties(prefix = "ctd.oauth2.token.store")
public class TokenStoreProperties
{
    /**
     * type
     */
    TokenStoreType type = TokenStoreType.redis;

    public TokenStoreType getType()
    {
        return type;
    }

    public void setType(TokenStoreType type)
    {
        this.type = type;
    }
}
