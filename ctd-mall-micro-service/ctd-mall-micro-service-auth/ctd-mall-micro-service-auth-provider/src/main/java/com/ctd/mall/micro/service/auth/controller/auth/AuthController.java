package com.ctd.mall.micro.service.auth.controller.auth;

import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.framework.common.core.vo.result.ResultVO;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.auth.manager.token.TokenManager;
import com.ctd.mall.micro.service.auth.service.user.CustomSocialUserDetailsService;
import com.ctd.mall.micro.service.auth.service.user.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * AuthController
 *
 * @author chentudong
 * @date 2020/3/8 13:40
 * @since 1.0
 */
@RestController
@RequestMapping("oauth")
public class AuthController
{
    @Resource
    private ObjectMapper objectMapper;

    @Autowired
    private TokenManager tokenManager;

    private final CustomSocialUserDetailsService customSocialUserDetailsService;
    private final CustomUserDetailsService customUserDetailsService;

    public AuthController(CustomSocialUserDetailsService customSocialUserDetailsService, CustomUserDetailsService customUserDetailsService)
    {
        this.customSocialUserDetailsService = customSocialUserDetailsService;
        this.customUserDetailsService = customUserDetailsService;
    }

    /**
     * findByOpenId
     *
     * @param openId openId
     * @return ResponseVO
     */
    @RequestMapping("findByOpenId")
    public ResponseVO findByOpenId(String openId)
    {
        return ResponseVO.data(customSocialUserDetailsService.loadUserByUserId(openId));
    }

    /**
     * token
     *
     * @param user     user
     * @param request  request
     * @param response response
     */
    @PostMapping("/user/token")
    public void token(@RequestBody UserVO user,
                      HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        try
        {
            tokenManager.writerToken(response, customUserDetailsService.token(user.getUsername(), user.getPassword(), request));
        } catch (BadCredentialsException | InternalAuthenticationServiceException e)
        {
            exceptionHandler(response, "账号或密码错误，请重新输入");
        } catch (Exception e)
        {
            exceptionHandler(response, e);
        }
    }

    private void exceptionHandler(HttpServletResponse response, Exception e) throws IOException
    {
        exceptionHandler(response, e.getMessage());
    }

    private void exceptionHandler(HttpServletResponse response, String msg) throws IOException
    {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        ResponseVO.responseWrite(objectMapper, response, ResultVO.failed(msg));
    }
}
