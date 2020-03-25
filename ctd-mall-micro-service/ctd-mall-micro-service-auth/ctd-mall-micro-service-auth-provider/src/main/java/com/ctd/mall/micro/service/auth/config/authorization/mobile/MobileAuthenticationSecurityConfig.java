package com.ctd.mall.micro.service.auth.config.authorization.mobile;

import com.ctd.mall.micro.service.auth.config.validate.code.ValidateCodeCacheConfig;
import com.ctd.mall.micro.service.auth.provider.mobile.MobileAuthenticationProvider;
import com.ctd.mall.micro.service.auth.service.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * 手机登录配置
 *
 * @author chentudong
 * @date 2020/3/25 20:35
 * @since 1.0
 */
@Component
public class MobileAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
{
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ValidateCodeCacheConfig validateCodeCacheConfig;

    @Override
    public void configure(HttpSecurity http) {
        MobileAuthenticationProvider provider = new MobileAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        provider.setValidateCodeCacheConfig(validateCodeCacheConfig);
        http.authenticationProvider(provider);
    }
}
