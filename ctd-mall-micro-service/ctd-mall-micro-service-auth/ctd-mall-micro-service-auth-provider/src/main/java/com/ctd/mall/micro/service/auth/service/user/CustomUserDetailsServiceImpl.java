package com.ctd.mall.micro.service.auth.service.user;

import com.ctd.mall.micro.service.user.client.user.UserClient;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomUserDetailsServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 8:29
 * @since 1.0
 */
@RestController
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService
{
    private final UserClient userClient;

    public CustomUserDetailsServiceImpl(UserClient userClient)
    {
        this.userClient = userClient;
    }
    /**
     * 手机号码获取用户
     *
     * @param mobile mobile
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByMobile(String mobile)
    {
        return userClient.findByMobile(mobile);
    }

    /**
     * loadUserByUsername
     *
     * @param username username
     * @return UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String username)
    {
        return userClient.findByUserName(username);
    }
}
