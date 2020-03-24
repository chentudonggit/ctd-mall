package com.ctd.mall.micro.service.auth.provider.open.id;

import com.ctd.mall.framework.auth.token.openid.OpenIdAuthenticationToken;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.social.security.SocialUserDetailsService;

/**
 * OpenIdAuthenticationProvider
 *
 * @author chentudong
 * @date 2020/3/8 8:53
 * @since 1.0
 */
public class OpenIdAuthenticationProvider implements AuthenticationProvider
{
    private SocialUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication)
    {
        OpenIdAuthenticationToken authenticationToken = (OpenIdAuthenticationToken) authentication;
        String openId = (String) authenticationToken.getPrincipal();
        UserDetails user = userDetailsService.loadUserByUserId(openId);
        AssertUtils.isNull(user, "openId = %s, 用户不存在，请核对。", openId);
        OpenIdAuthenticationToken authenticationResult = new OpenIdAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication)
    {
        return OpenIdAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public SocialUserDetailsService getUserDetailsService()
    {
        return userDetailsService;
    }

    public void setUserDetailsService(SocialUserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }
}
