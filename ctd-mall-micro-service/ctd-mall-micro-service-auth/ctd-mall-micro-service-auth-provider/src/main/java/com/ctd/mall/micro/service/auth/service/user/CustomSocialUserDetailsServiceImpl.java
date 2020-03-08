package com.ctd.mall.micro.service.auth.service.user;

import com.ctd.mall.micro.service.user.client.user.UserClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.RestController;

/**
 * CustomSocialUserDetailsServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 8:48
 * @since 1.0
 */
@RestController
public class CustomSocialUserDetailsServiceImpl implements CustomSocialUserDetailsService
{
    private final UserClient userClient;

    public CustomSocialUserDetailsServiceImpl(UserClient userClient)
    {
        this.userClient = userClient;
    }

    /**
     * loadUserByUserId
     *
     * @param userId the user ID used to lookup the user details
     * @return the SocialUserDetails requested
     * @throws UsernameNotFoundException UsernameNotFoundException
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException
    {
        return userClient.findByOpenId(userId);
    }
}
