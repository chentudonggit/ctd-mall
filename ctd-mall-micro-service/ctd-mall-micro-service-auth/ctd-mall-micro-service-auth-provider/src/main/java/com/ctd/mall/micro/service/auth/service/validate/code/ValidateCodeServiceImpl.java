package com.ctd.mall.micro.service.auth.service.validate.code;

import com.ctd.mall.micro.service.auth.config.validate.code.ValidateCodeCacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 验证码服务
 *
 * @author chentudong
 * @date 2020/3/25 17:10
 * @since 1.0
 */
@ApiIgnore
@RestController
public class ValidateCodeServiceImpl implements ValidateCodeService
{
    @Autowired
    private ValidateCodeCacheConfig validateCodeCacheConfig;

    /**
     * 发送验证码
     *
     * @param mobile mobile
     * @return String
     */
    @Override
    public String sendSmsCode(String mobile)
    {
        return validateCodeCacheConfig.sendSmsCode(mobile);
    }

    /**
     * 获取验证码
     *
     * @param mobile mobile
     * @return String
     */
    @Override
    public String findCodeByMobile(String mobile)
    {
        return validateCodeCacheConfig.findCodeByMobile(mobile);
    }

    /**
     * 删除验证码
     *
     * @param key key
     * @return Boolean
     */
    @Override
    public Boolean remove(String key)
    {
        return validateCodeCacheConfig.remove(key);
    }

    /**
     * 校验
     *
     * @param key  key
     * @param code code
     * @return Boolean
     */
    @Override
    public Boolean validate(String key, String code)
    {
        return validateCodeCacheConfig.validate(key, code);
    }
}
