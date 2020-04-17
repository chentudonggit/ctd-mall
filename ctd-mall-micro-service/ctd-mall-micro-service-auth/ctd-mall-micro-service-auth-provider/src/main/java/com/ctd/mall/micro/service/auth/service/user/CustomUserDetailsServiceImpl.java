package com.ctd.mall.micro.service.auth.service.user;

import com.ctd.springboot.auth.vo.user.login.LoginUserVO;
import com.ctd.springboot.common.core.bean.BeanHelper;
import com.ctd.mall.micro.service.auth.config.validate.code.ValidateCodeCacheConfig;
import com.ctd.mall.micro.service.auth.manager.token.TokenManager;
import com.ctd.mall.micro.service.user.client.user.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * CustomUserDetailsServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 8:29
 * @since 1.0
 */
@ApiIgnore
@RestController
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService
{
    @Autowired
    private ValidateCodeCacheConfig validateCodeCacheConfig;
    @Autowired
    private TokenManager tokenManager;

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
        return BeanHelper.convert(userClient.findByMobile(mobile), LoginUserVO.class);
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
        return BeanHelper.convert(userClient.findByUserName(username), LoginUserVO.class);
    }

    /**
     * 获取token
     *
     * @param userName userName
     * @param passWord passWord
     * @param request  request
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken token(String userName, String passWord, HttpServletRequest request)
    {
        return tokenManager.token(userName, passWord, request);
    }

    /**
     * 手机号/验证码
     *
     * @param mobile  mobile
     * @param code    code
     * @param request request
     * @return OAuth2AccessToken
     */
    @Override
    public OAuth2AccessToken tokenMobileAndCode(String mobile, String code, HttpServletRequest request)
    {
        OAuth2AccessToken oAuth2AccessToken = tokenManager.tokenMobileAndCode(mobile, code, request);
        if (Objects.nonNull(oAuth2AccessToken))
        {
            validateCodeCacheConfig.remove(mobile);
        }
        return oAuth2AccessToken;
    }
}
