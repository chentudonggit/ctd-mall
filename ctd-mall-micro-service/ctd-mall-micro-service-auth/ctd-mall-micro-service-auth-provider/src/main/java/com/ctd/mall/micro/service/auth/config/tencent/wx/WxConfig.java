package com.ctd.mall.micro.service.auth.config.tencent.wx;

import com.ctd.springboot.common.core.constant.tencent.TencentConstants;
import com.ctd.mall.micro.service.auth.properties.tencent.wx.WxProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * 微信配置
 *
 * @author chentudong
 * @date 2020/4/4 22:38
 * @since 1.0
 */
@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxConfig
{
    @Autowired
    private WxProperties wxProperties;

    /**
     * loginWxCode
     *
     * @return String
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    public String loginWxCode() throws UnsupportedEncodingException
    {
        return String.format(TencentConstants.WEI_XIN_LOGIN_QR_CODE_CONNECT_QR_CONNECT,
                wxProperties.getAppId(),
                URLEncoder.encode(wxProperties.getRedirectUrl(), "utf-8"),
                "atguigu");
    }

    public WxProperties getWxProperties()
    {
        return wxProperties;
    }
}
