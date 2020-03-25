package com.ctd.mall.micro.service.auth.service.user;

import com.ctd.mall.framework.auth.vo.user.login.LoginUserVO;
import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.micro.service.user.client.user.UserClient;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * CustomSocialUserDetailsServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 8:48
 * @since 1.0
 */
@ApiIgnore
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
        LoginUserVO loginUser = BeanHelper.convert(userClient.findByOpenId(userId), LoginUserVO.class);
        //封装菜单
        return loginUser;
    }
}
