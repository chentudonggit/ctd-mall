package com.ctd.mall.micro.service.auth.manager.token;

import com.ctd.mall.framework.auth.token.mobile.MobileAuthenticationToken;
import com.ctd.mall.framework.auth.utils.auth.AuthUtils;
import com.ctd.mall.framework.auth.vo.client.ClientInfoVO;
import com.ctd.mall.framework.common.core.constant.security.SecurityConstants;
import com.ctd.mall.framework.common.core.holder.context.TenantContextHolder;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * TokenManager
 *
 * @author chentudong
 * @date 2020/3/14 12:20
 * @since 1.0
 */
@Component
public class TokenManager
{
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Resource
    private AuthorizationServerTokenServices authorizationServerTokenServices;

    @Autowired
    private ClientDetailsService clientDetailsService;

    /**
     * 写出token
     *
     * @param response          response
     * @param oAuth2AccessToken oAuth2AccessToken
     */
    public void writerToken(HttpServletResponse response, OAuth2AccessToken oAuth2AccessToken) throws IOException
    {
        ResponseVO.responseSucceed(objectMapper, response, oAuth2AccessToken);
    }

    /**
     * token
     *
     * @param userName userName
     * @param passWord passWord
     * @param request  request
     * @return OAuth2AccessToken
     */
    public OAuth2AccessToken token(String userName, String passWord, HttpServletRequest request)
    {
        AssertUtils.isNull(userName, "userName 不能为空");
        AssertUtils.isNull(passWord, "passWord 不能为空");
        return setOAuth2AccessToken(request, new UsernamePasswordAuthenticationToken(userName, passWord), SecurityConstants.USERNAME_GRANT_TYPE);
    }

    /**
     * setOAuth2AccessToken
     *
     * @param request   request
     * @param token     token
     * @param grantType grantType
     * @return OAuth2AccessToken
     */
    public OAuth2AccessToken setOAuth2AccessToken(HttpServletRequest request, AbstractAuthenticationToken token, String grantType)
    {
        AssertUtils.isNull(request, "request 不能为空");
        AssertUtils.isNull(token, "token 不能为空");
        Authentication authentication = authenticationManager.authenticate(token);
        OAuth2Request oAuth2Request = setOAuth2Request(request, grantType);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        oAuth2Authentication.setAuthenticated(true);
        return oAuth2AccessToken;
    }

    /**
     * setOAuth2Request
     *
     * @param request   request
     * @param grantType grantType
     * @return OAuth2Request
     */
    public OAuth2Request setOAuth2Request(HttpServletRequest request, String grantType)
    {
        ClientDetails clientDetails = nonNullClientDetail(request);
        String clientId = clientDetails.getClientId();
        //保存租户id
        TenantContextHolder.setTenant(clientId);
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), grantType);
        return tokenRequest.createOAuth2Request(clientDetails);
    }

    /**
     * nonNullClientDetail
     *
     * @param request request
     * @return ClientDetails
     */
    public ClientDetails nonNullClientDetail(HttpServletRequest request)
    {
        ClientDetails clientDetails = getClientDetail(request);
        AssertUtils.isNullToUser(clientDetails, "未配置clientId, 请联系管理员.");
        return clientDetails;
    }

    /**
     * clientInfo
     *
     * @param request request
     * @return ClientInfoVO
     */
    public ClientInfoVO getClientInfo(HttpServletRequest request)
    {
        if (Objects.nonNull(request))
        {
            return AuthUtils.extractClient(request);
        }
        return null;
    }

    /**
     * getClientDetail
     *
     * @param request request
     * @return ClientDetails
     */
    public ClientDetails getClientDetail(HttpServletRequest request)
    {
        ClientInfoVO clientInfo = getClientInfo(request);
        if (Objects.isNull(clientInfo))
        {
            return null;
        }
        String clientId = clientInfo.getClientId();
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        AssertUtils.isNull(clientDetails, "clientId = %s 数据不存在，请核对。", clientId);

        if (!clientDetails.getClientSecret().equals(clientInfo.getClientSecret()))
        {
            if (!passwordEncoder.matches(clientInfo.getClientSecret(), clientDetails.getClientSecret()))
            {
                AssertUtils.msgUser("clientId = %s 密码错误，请联系管理员。", clientId);
            }
        }
        return clientDetails;
    }

    /**
     * 手机号/验证码
     *
     * @param mobile  mobile
     * @param code    code
     * @param request request
     * @return OAuth2AccessToken
     */
    public OAuth2AccessToken tokenMobileAndCode(String mobile, String code, HttpServletRequest request)
    {
        AssertUtils.isNullToUser(mobile, "请输入手机号");
        AssertUtils.isNullToUser(code, "请输入验证码");
        return setOAuth2AccessToken(request, new MobileAuthenticationToken(mobile, code, true), SecurityConstants.MOBILE_GRANT_TYPE);
    }
}
