package com.ctd.mall.micro.service.auth.config.security;

import com.ctd.mall.framework.auth.config.security.password.DefaultPasswordConfig;
import com.ctd.mall.framework.auth.constant.security.SecurityConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

/**
 * SecurityConfig
 *
 * @author chentudong
 * @date 2020/3/7 23:56
 * @since 1.0
 */
@Configuration
@Import(DefaultPasswordConfig.class)
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                //授权服务器关闭basic认证
                .permitAll()
                .and()
                .formLogin()
                .loginPage(SecurityConstants.LOGIN_PAGE)
                .loginProcessingUrl(SecurityConstants.OAUTH_LOGIN_PRO_URL)
                .and()
                .logout()
                .logoutUrl(SecurityConstants.LOGOUT_URL)
                .logoutSuccessUrl(SecurityConstants.LOGIN_PAGE)
                .logoutSuccessHandler(new HttpStatusReturningLogoutSuccessHandler())
                .clearAuthentication(true)
                .and()
                .csrf().disable()
                // 解决不允许显示在iframe的问题
                .headers().frameOptions().disable().cacheControl();

    }
}
