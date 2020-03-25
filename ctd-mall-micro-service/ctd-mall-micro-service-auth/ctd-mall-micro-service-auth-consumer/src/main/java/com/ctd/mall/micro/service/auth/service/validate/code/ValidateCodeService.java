package com.ctd.mall.micro.service.auth.service.validate.code;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 验证码服务
 *
 * @author chentudong
 * @date 2020/3/25 17:00
 * @since 1.0
 */
@RequestMapping("validateCodeService")
public interface ValidateCodeService
{

    /**
     * 发送验证码
     *
     * @param mobile mobile
     * @return String
     */
    @RequestMapping("sendSmsCode")
    String sendSmsCode(@RequestParam("mobile") String mobile);

    /**
     * 获取验证码
     *
     * @param mobile mobile
     * @return String
     */
    @RequestMapping("findCodeByMobile")
    String findCodeByMobile(@RequestParam("mobile") String mobile);

    /**
     * 删除验证码
     *
     * @param key key
     * @return Boolean
     */
    @RequestMapping("remove")
    Boolean remove(@RequestParam("key") String key);

    /**
     * 校验
     *
     * @param key  key
     * @param code code
     * @return Boolean
     */
    @RequestMapping("validate")
    Boolean validate(@RequestParam("key") String key,
                     @RequestParam("code") String code);
}
