package com.ctd.mall.micro.service.auth.service.user;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * CustomSocialUserDetailsService
 *
 * @author chentudong
 * @date 2020/3/8 8:44
 * @since 1.0
 */
@RequestMapping("customSocialUserDetailsService")
public interface CustomSocialUserDetailsService extends SocialUserDetailsService
{
    /**
     * loadUserByUserId
     *
     * @param userId the user ID used to lookup the user details
     * @return the SocialUserDetails requested
     * @throws UsernameNotFoundException UsernameNotFoundException
     * @see UserDetailsService#loadUserByUsername(String)
     */
    @Override
    @RequestMapping("loadUserByUserId")
    SocialUserDetails loadUserByUserId(@RequestParam("userId") String userId) throws UsernameNotFoundException;
}
