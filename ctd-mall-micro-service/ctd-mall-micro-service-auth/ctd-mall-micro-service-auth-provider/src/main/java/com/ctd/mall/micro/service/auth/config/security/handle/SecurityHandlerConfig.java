package com.ctd.mall.micro.service.auth.config.security.handle;

import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.micro.service.auth.handler.logout.oauth.OauthLogoutHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.exceptions.*;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * SecurityHandlerConfig
 *
 * @author chentudong
 * @date 2020/3/14 14:37
 * @since 1.0
 */
@Configuration
public class SecurityHandlerConfig
{
    @Resource
    private ObjectMapper objectMapper;

    /**
     * 登陆失败，返回401
     */
    @Bean
    public AuthenticationFailureHandler loginFailureHandler()
    {
        return (request, response, exception) -> {
            String msg;
            if (exception instanceof BadCredentialsException)
            {
                msg = "密码错误";
            } else
            {
                msg = exception.getMessage();
            }
            ResponseVO.responseWriter(objectMapper, response, msg, HttpStatus.UNAUTHORIZED.value());
        };
    }

    @Bean
    public OauthLogoutHandler oauthLogoutHandler()
    {
        return new OauthLogoutHandler();
    }

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator()
    {
        return new DefaultWebResponseExceptionTranslator()
        {
            public static final String BAD_MSG = "坏的凭证";

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception
            {
                OAuth2Exception oAuth2Exception;
                if (Objects.nonNull(e.getMessage()) && e.getMessage().equals(BAD_MSG))
                {
                    oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
                } else if (e instanceof InternalAuthenticationServiceException)
                {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof RedirectMismatchException)
                {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof InvalidScopeException)
                {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else
                {
                    oAuth2Exception = new UnsupportedResponseTypeException("服务内部错误", e);
                }
                ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
                ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
                OAuth2Exception body = response.getBody();
                if (Objects.nonNull(body))
                {
                    body.addAdditionalInformation(ResponseVO.KEY_CODE, oAuth2Exception.getHttpErrorCode() + "");
                    body.addAdditionalInformation(ResponseVO.KEY_MESSAGE, oAuth2Exception.getMessage());
                }
                return response;
            }
        };
    }

    /**
     * 登陆成功
     */
    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler()
    {
        return new SavedRequestAwareAuthenticationSuccessHandler()
        {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                                Authentication authentication) throws IOException, ServletException
            {
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
}
