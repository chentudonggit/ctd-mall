package com.ctd.mall.micro.service.auth.provider.mobile;

import com.ctd.springboot.auth.token.mobile.MobileAuthenticationToken;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.utils.param.ParamUtils;
import com.ctd.mall.micro.service.auth.config.validate.code.ValidateCodeCacheConfig;
import com.ctd.mall.micro.service.auth.service.user.CustomUserDetailsService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * MobileAuthenticationProvider
 *
 * @author chentudong
 * @date 2020/3/25 20:38
 * @since 1.0
 */
public class MobileAuthenticationProvider implements AuthenticationProvider
{
    private ValidateCodeCacheConfig validateCodeCacheConfig;
    private CustomUserDetailsService userDetailsService;
    private PasswordEncoder passwordEncoder;

    /**
     * Performs authentication with the same contract as
     * {@link AuthenticationManager#authenticate(Authentication)}
     * .
     *
     * @param authentication the authentication request object.
     * @return a fully authenticated object including credentials. May return
     * <code>null</code> if the <code>AuthenticationProvider</code> is unable to support
     * authentication of the passed <code>Authentication</code> object. In such a case,
     * the next <code>AuthenticationProvider</code> that supports the presented
     * <code>Authentication</code> class will be tried.
     * @throws AuthenticationException if authentication fails.
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        if(authentication instanceof MobileAuthenticationToken)
        {
            MobileAuthenticationToken authenticationToken = (MobileAuthenticationToken) authentication;
            String mobile = ParamUtils.getParam(authenticationToken.getPrincipal());
            String password = ParamUtils.getParam(authenticationToken.getCredentials());
            UserDetails user = userDetailsService.loadUserByMobile(mobile);
            AssertUtils.isNullToUser(user, "%s 未注册，请先注册。", mobile);
            if(authenticationToken.isSmsCode())
            {
                //验证码
                validateCodeCacheConfig.validate(mobile, password);
            }else
            {
                //账号密码
                if (!passwordEncoder.matches(password, user.getPassword())) {
                    AssertUtils.msgUser("手机号或密码错误");
                }
            }
            MobileAuthenticationToken authenticationResult = new MobileAuthenticationToken(user, password, user.getAuthorities());
            authenticationResult.setDetails(authenticationToken.getDetails());
            return authenticationResult;
        }
        return null;
    }

    /**
     * Returns <code>true</code> if this <Code>AuthenticationProvider</code> supports the
     * indicated <Code>Authentication</code> object.
     * <p>
     * Returning <code>true</code> does not guarantee an
     * <code>AuthenticationProvider</code> will be able to authenticate the presented
     * instance of the <code>Authentication</code> class. It simply indicates it can
     * support closer evaluation of it. An <code>AuthenticationProvider</code> can still
     * return <code>null</code> from the {@link #authenticate(Authentication)} method to
     * indicate another <code>AuthenticationProvider</code> should be tried.
     * </p>
     * <p>
     * Selection of an <code>AuthenticationProvider</code> capable of performing
     * authentication is conducted at runtime the <code>ProviderManager</code>.
     * </p>
     *
     * @param authentication authentication
     * @return <code>true</code> if the implementation can more closely evaluate the
     * <code>Authentication</code> class presented
     */
    @Override
    public boolean supports(Class<?> authentication)
    {
        return MobileAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public CustomUserDetailsService getUserDetailsService()
    {
        return userDetailsService;
    }

    public void setUserDetailsService(CustomUserDetailsService userDetailsService)
    {
        this.userDetailsService = userDetailsService;
    }

    public PasswordEncoder getPasswordEncoder()
    {
        return passwordEncoder;
    }

    public void setPasswordEncoder(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder = passwordEncoder;
    }

    public ValidateCodeCacheConfig getValidateCodeCacheConfig()
    {
        return validateCodeCacheConfig;
    }

    public void setValidateCodeCacheConfig(ValidateCodeCacheConfig validateCodeCacheConfig)
    {
        this.validateCodeCacheConfig = validateCodeCacheConfig;
    }
}
