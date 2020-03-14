package com.ctd.mall.micro.service.auth.manager.token;

import com.ctd.mall.framework.auth.utils.auth.AuthUtils;
import com.ctd.mall.framework.auth.vo.client.ClientInfoVO;
import com.ctd.mall.framework.common.core.holder.context.TenantContextHolder;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
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
        ClientDetails clientDetails = getClientDetail(request);
        if (Objects.isNull(clientDetails))
        {
            return null;
        }
        String clientId = clientDetails.getClientId();
        //保存租户id
        TenantContextHolder.setTenant(clientId);
        TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "customer");
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, passWord);
        OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
        Authentication authentication = authenticationManager.authenticate(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
        OAuth2AccessToken oAuth2AccessToken = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
        oAuth2Authentication.setAuthenticated(true);
        return oAuth2AccessToken;
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
        if (Objects.isNull(clientDetails))
        {
            throw new UnapprovedClientAuthenticationException("clientId = " + clientId + " 数据不存在，请核对。");
        } else if (!passwordEncoder.matches(clientInfo.getClientSecret(), clientDetails.getClientSecret()))
        {
            throw new UnapprovedClientAuthenticationException("clientSecret 填写错误");
        }
        return clientDetails;
    }
}
