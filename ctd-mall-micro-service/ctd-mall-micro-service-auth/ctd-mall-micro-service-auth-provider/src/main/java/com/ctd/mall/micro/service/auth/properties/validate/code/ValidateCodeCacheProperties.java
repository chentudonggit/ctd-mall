package com.ctd.mall.micro.service.auth.properties.validate.code;

import com.ctd.mall.micro.service.common.enums.validate.code.cache.ValidateCodeCacheEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * ValidateCodeCacheProperties
 *
 * @author chentudong
 * @date 2020/3/25 17:26
 * @since 1.0
 */
@ConfigurationProperties(prefix = "ctd.validate.code")
public class ValidateCodeCacheProperties
{
    /**
     * 缓存类型
     */
    private ValidateCodeCacheEnum cacheType = ValidateCodeCacheEnum.Memory;

    /**
     * 存多少时间
     */
    private Long second;

    public ValidateCodeCacheEnum getCacheType()
    {
        return cacheType;
    }

    public void setCacheType(ValidateCodeCacheEnum cacheType)
    {
        this.cacheType = cacheType;
    }

    public Long getSecond()
    {
        if (Objects.isNull(second))
        {
            return 5 * 60 * 1000L;
        }
        return second;
    }

    public void setSecond(Long second)
    {
        this.second = second;
    }
}
