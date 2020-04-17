package com.ctd.mall.micro.service.auth.controller.auth;

import com.ctd.springboot.common.core.utils.param.ParamUtils;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.springboot.common.core.vo.result.ResultVO;
import com.ctd.springboot.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.auth.manager.token.TokenManager;
import com.ctd.mall.micro.service.auth.service.user.CustomSocialUserDetailsService;
import com.ctd.mall.micro.service.auth.service.user.CustomUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

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
@Api(tags = "认证")
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
    private final ClientDetailsService clientDetailsService;

    public AuthController(CustomSocialUserDetailsService customSocialUserDetailsService,
                          CustomUserDetailsService customUserDetailsService, ClientDetailsService clientDetailsService)
    {
        this.customSocialUserDetailsService = customSocialUserDetailsService;
        this.customUserDetailsService = customUserDetailsService;
        this.clientDetailsService = clientDetailsService;
    }

    /**
     * findByOpenId
     *
     * @param openId openId
     * @return ResponseVO
     */
    @ApiOperation("openId 获取用户详情")
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
    @ApiOperation("用户账号密码获取token")
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

    /**
     * 手机号/验证码获取 token
     *
     * @param map      map
     * @param request  request
     * @param response response
     */
    @ApiOperation("手机号/验证码获取 token")
    @PostMapping("tokenMobileAndCode")
    public void tokenMobileAndCode(@RequestBody ModelMap map, HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        tokenManager.writerToken(response, customUserDetailsService.tokenMobileAndCode(ParamUtils.getParam(map,
                ParamUtils.MOBILE), ParamUtils.getParam(map, ParamUtils.CODE), request));
    }

    /**
     * 获取客户端详情
     *
     * @param clientId clientId
     * @return ResponseVO
     */
    @ApiOperation("获取客户端详情")
    @GetMapping("findClientById/{clientId}")
    public ResponseVO findClientById(@PathVariable String clientId)
    {
        return ResponseVO.data(clientDetailsService.loadClientByClientId(clientId));
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
