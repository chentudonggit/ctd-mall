package com.ctd.mall.micro.service.auth.config.validate.code;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.utils.mobile.MobileUtils;
import com.ctd.mall.framework.redis.repository.RedisRepository;
import com.ctd.mall.micro.service.common.enums.validate.code.cache.ValidateCodeCacheEnum;
import com.ctd.mall.micro.service.auth.properties.validate.code.ValidateCodeCacheProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * ValidateCodeCacheConfig
 *
 * @author chentudong
 * @date 2020/3/25 17:12
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(ValidateCodeCacheProperties.class)
public class ValidateCodeCacheConfig
{
    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateCodeCacheConfig.class);

    private static final Random RANDOM = new Random();
    /**
     * 手机验证码
     * phone-code
     */
    private static final ConcurrentHashMap<String, String> PHONE_CODE_MAP = new ConcurrentHashMap<>();

    /**
     * 判断验证码是否过期
     */
    private static final ConcurrentHashMap<String, Long> PHONE_CODE_TIME_MAP = new ConcurrentHashMap<>();

    @Autowired(required = false)
    private RedisRepository redisRepository;

    private final ValidateCodeCacheProperties validateCodeCacheProperties;

    public ValidateCodeCacheConfig(ValidateCodeCacheProperties validateCodeCacheProperties)
    {
        this.validateCodeCacheProperties = validateCodeCacheProperties;
        if (ValidateCodeCacheEnum.Redis.equals(validateCodeCacheProperties.getCacheType()))
        {
            AssertUtils.isNull(redisRepository, "请配置 redis ");
        }
    }

    /**
     * 发送验证码
     *
     * @param mobile mobile
     * @return String
     */
    public String sendSmsCode(String mobile)
    {
        MobileUtils.assertMobile(mobile);
        AssertUtils.isNull(mobile, "key 不能为空");
        ValidateCodeCacheEnum cacheType = validateCodeCacheProperties.getCacheType();
        Long second = validateCodeCacheProperties.getSecond();
        String value = getRandomCode();
        if (ValidateCodeCacheEnum.Memory.equals(cacheType))
        {
            PHONE_CODE_MAP.put(mobile, value);
            PHONE_CODE_TIME_MAP.put(mobile, System.currentTimeMillis());
            LOGGER.info("phone: {} ....  code: {}", mobile, value);
        } else
        {
            redisRepository.setExpire(mobile, value, second);
        }
        return value;
    }

    /**
     * 获取验证码
     *
     * @param mobile mobile
     * @return String
     */
    public String findCodeByMobile(String mobile)
    {
        ValidateCodeCacheEnum cacheType = validateCodeCacheProperties.getCacheType();
        if (ValidateCodeCacheEnum.Memory.equals(cacheType))
        {
            return PHONE_CODE_MAP.get(mobile);
        } else
        {
            Object o = redisRepository.get(mobile);
            if (Objects.nonNull(o))
            {
                return (String) o;
            }
            return null;
        }
    }

    /**
     * 删除验证码
     *
     * @param key key
     * @return Boolean
     */
    public Boolean remove(String key)
    {
        AssertUtils.isNull(key, "key 不能为空");
        ValidateCodeCacheEnum cacheType = validateCodeCacheProperties.getCacheType();
        if (ValidateCodeCacheEnum.Memory.equals(cacheType))
        {
            PHONE_CODE_MAP.remove(key);
            PHONE_CODE_TIME_MAP.remove(key);
        } else
        {
            redisRepository.del(key);
        }
        return true;
    }

    /**
     * 校验
     *
     * @param key   key
     * @param value value
     * @return Boolean
     */
    public Boolean validate(String key, String value)
    {
        AssertUtils.isNull(key, "key 不能为空");
        AssertUtils.isNull(value, "value 不能为空");
        ValidateCodeCacheEnum cacheType = validateCodeCacheProperties.getCacheType();
        if (ValidateCodeCacheEnum.Memory.equals(cacheType))
        {
            String mCode = PHONE_CODE_MAP.get(key);
            if (StringUtils.isBlank(mCode) || !mCode.equals(value))
            {
                AssertUtils.msgUser("%s, 验证码不正确", value);
            }

            Long time = PHONE_CODE_TIME_MAP.get(key);
            long timeMillis = System.currentTimeMillis();
            timeMillis -= time;
            if ((timeMillis > validateCodeCacheProperties.getSecond()))
            {
                AssertUtils.msgUser("%s, 验证码已过期，请重新获取", value);
            }
        } else
        {
            Object o = redisRepository.get(key);
            if (Objects.isNull(o))
            {
                AssertUtils.msgUser("%s, 验证码已过期，请重新获取", value);
            } else if (!value.equals(o))
            {
                AssertUtils.msgUser("%s, 验证码不正确", value);
            }
        }
        return true;
    }

    /**
     * getRandomCode
     *
     * @return String
     */
    private String getRandomCode()
    {
        return (RANDOM.nextInt(9999 - 1000 + 1) + 1000) + "";
    }
}
