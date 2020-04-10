package com.ctd.mall.micro.service.auth.controller.tencent.wx;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.micro.service.auth.config.tencent.wx.WxConfig;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

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

    /**
     * 微信登陆二维码
     * @return ResponseVO
     */
    @ApiOperation("微信登陆二维码")
    @GetMapping("loginWxCode")
    public ResponseEntity loginWxCode()
    {
        try
        {
            return ResponseEntity.status(HttpStatus.MOVED_PERMANENTLY).header(HttpHeaders.LOCATION, wxConfig.loginWxCode()).build();
        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            AssertUtils.msgUser("微信二维码失败，请联系管理员");
        }
        return null;
    }
}
