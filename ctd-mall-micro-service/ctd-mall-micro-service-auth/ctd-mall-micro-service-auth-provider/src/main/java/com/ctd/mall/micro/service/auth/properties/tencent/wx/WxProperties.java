package com.ctd.mall.micro.service.auth.properties.tencent.wx;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * WxProperties
 *
 * @author chentudong
 * @date 2020/4/4 22:39
 * @since 1.0
 */
@ConfigurationProperties(prefix = "tencent.wx")
public class WxProperties
{
    /**
     * appId
     */
    private String appId;

    /**
     * appSecret
     */
    private String appSecret;

    /**
     * redirectUrl
     */
    private String redirectUrl;;

    public String getAppId()
    {
        return appId;
    }

    public void setAppId(String appId)
    {
        this.appId = appId;
    }

    public String getAppSecret()
    {
        return appSecret;
    }

    public void setAppSecret(String appSecret)
    {
        this.appSecret = appSecret;
    }

    public String getRedirectUrl()
    {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }
}
