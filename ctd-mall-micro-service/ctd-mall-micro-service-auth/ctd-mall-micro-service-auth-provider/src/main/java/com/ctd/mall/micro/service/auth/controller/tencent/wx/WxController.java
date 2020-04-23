package com.ctd.mall.micro.service.auth.controller.tencent.wx;

import com.ctd.mall.micro.service.auth.config.tencent.wx.WxConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 微信
 *
 * @author chentudong
 * @date 2020/4/4 22:28
 * @since 1.0
 */
@RestController
@RequestMapping("wx")
public class WxController
{
    @Autowired
    private WxConfig wxConfig;
}
