package com.ctd.mall.micro.service.auth.config.security.open.id;

import com.ctd.mall.micro.service.auth.provider.open.id.OpenIdAuthenticationProvider;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.stereotype.Component;

/**
 * OpenIdAuthenticationSecurityConfig
 *
 * @author chentudong
 * @date 2020/3/8 8:56
 * @since 1.0
 */
@Component
public class OpenIdAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>
{
    private final SocialUserDetailsService userDetailsService;

    public OpenIdAuthenticationSecurityConfig(SocialUserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(HttpSecurity http)
    {
        //openId provider
        OpenIdAuthenticationProvider provider = new OpenIdAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        http.authenticationProvider(provider);
    }
}
