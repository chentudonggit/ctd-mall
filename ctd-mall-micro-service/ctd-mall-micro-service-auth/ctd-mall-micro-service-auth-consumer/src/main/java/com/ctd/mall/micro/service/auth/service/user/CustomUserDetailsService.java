package com.ctd.mall.micro.service.auth.service.user;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
}
