package com.ctd.mall.micro.service.auth.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * CustomUserDetailsService
 *
 * @author chentudong
 * @date 2020/3/8 8:13
 * @since 1.0
 */
@RequestMapping("customUserDetailsService")
public interface CustomUserDetailsService extends UserDetailsService
{
    /**
     * 手机号码获取用户
     *
     * @param mobile mobile
     * @return UserDetails
     */
    @RequestMapping("loadUserByMobile")
    UserDetails loadUserByMobile(@RequestParam("mobile") String mobile);

    /**
     * loadUserByUsername
     *
     * @param username username
     * @return UserDetails
     */
    @Override
    @RequestMapping("loadUserByUsername")
    UserDetails loadUserByUsername(@RequestParam("username") String username);

    /**
     * token
     *
     * @param username username
     * @param password password
     * @param request  request
     * @return OAuth2AccessToken
     */
    @RequestMapping("token")
    OAuth2AccessToken token(@RequestParam("username") String username,
                            @RequestParam("password") String password,
                            @RequestParam("request") HttpServletRequest request);

    /**
     * 手机号/验证码
     *
     * @param mobile  mobile
     * @param code    code
     * @param request request
     * @return OAuth2AccessToken
     */
    @RequestMapping("tokenMobileAndCode")
    OAuth2AccessToken tokenMobileAndCode(@RequestParam("mobile") String mobile,
                                         @RequestParam("code") String code,
                                         @RequestParam("request") HttpServletRequest request);
}
