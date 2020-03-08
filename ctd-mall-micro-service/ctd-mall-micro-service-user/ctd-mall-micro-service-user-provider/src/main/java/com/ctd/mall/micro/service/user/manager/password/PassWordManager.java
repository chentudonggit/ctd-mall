package com.ctd.mall.micro.service.user.manager.password;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.micro.service.user.config.password.PasswordConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * PassWordManager
 *
 * @author chentudong
 * @date 2020/3/8 17:13
 * @since 1.0
 */
@Component
public class PassWordManager
{
    private final PasswordConfig passwordConfig;

    public PassWordManager(PasswordConfig passwordConfig)
    {
        this.passwordConfig = passwordConfig;
    }

    /**
     * PasswordEncoder
     *
     * @return PasswordEncoder
     */
    public PasswordEncoder getPasswordEncoder()
    {
        return passwordConfig.passwordEncoder();
    }

    /**
     * 加密
     *
     * @param rawPassword rawPassword
     * @return String
     */
    public String encode(String rawPassword)
    {
        AssertUtils.isNullToUser(rawPassword, "请输入密码");
        return getPasswordEncoder().encode(rawPassword);
    }

    /**
     * 密码比对
     * rawPassword 需要解密，前端用 rsa 加密
     *
     * @param rawPassword     rawPassword
     * @param encodedPassword encodedPassword
     * @param msg             msg
     */
    public void matches(String rawPassword, String encodedPassword, String msg)
    {
        AssertUtils.isNullToUser(rawPassword, "请输入密码");
        AssertUtils.isNull(encodedPassword, "encodedPassword 不能为空");
        if (!getPasswordEncoder().matches(rawPassword, encodedPassword))
        {
            AssertUtils.msgUser(StringUtils.isNotBlank(msg) ? msg : "密码错误，请核对！");
        }
    }
}
