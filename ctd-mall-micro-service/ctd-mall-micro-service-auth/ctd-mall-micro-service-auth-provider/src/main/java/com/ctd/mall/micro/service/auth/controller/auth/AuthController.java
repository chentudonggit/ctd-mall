package com.ctd.mall.micro.service.auth.controller.auth;

import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.micro.service.auth.service.user.CustomSocialUserDetailsService;
import com.ctd.mall.micro.service.auth.service.user.CustomUserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    private final CustomSocialUserDetailsService customSocialUserDetailsService;
    private final CustomUserDetailsService CustomUserDetailsService;

    public AuthController(CustomSocialUserDetailsService customSocialUserDetailsService, CustomUserDetailsService customUserDetailsService)
    {
        this.customSocialUserDetailsService = customSocialUserDetailsService;
        CustomUserDetailsService = customUserDetailsService;
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
}
