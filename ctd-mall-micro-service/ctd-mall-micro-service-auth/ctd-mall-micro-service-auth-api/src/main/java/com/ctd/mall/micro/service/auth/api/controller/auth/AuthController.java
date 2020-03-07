package com.ctd.mall.micro.service.auth.api.controller.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController
 *
 * @author chentudong
 * @date 2020/3/7 22:29
 * @since 1.0
 */
@RestController
public class AuthController
{
    /**
     * index
     *
     * @return index
     */
    @RequestMapping({"/", "/index"})
    public String index()
    {
        return "hello oauth2";
    }
}
