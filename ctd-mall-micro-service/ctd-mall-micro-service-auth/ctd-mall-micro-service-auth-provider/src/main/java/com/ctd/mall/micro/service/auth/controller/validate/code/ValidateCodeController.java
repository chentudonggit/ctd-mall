package com.ctd.mall.micro.service.auth.controller.validate.code;

import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.mall.micro.service.auth.service.validate.code.ValidateCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ValidateCodeController
 *
 * @author chentudong
 * @date 2020/3/25 18:57
 * @since 1.0
 */
@Api(tags = "验证码")
@RestController
@RequestMapping("validate/code")
public class ValidateCodeController
{
    /**
     * profile production
     */
    private static final String PROFILES_PRODUCTION = "production";

    private final ValidateCodeService validateCodeService;

    public ValidateCodeController(ValidateCodeService validateCodeService)
    {
        this.validateCodeService = validateCodeService;
    }

    /**
     * 发送验证码
     *
     * @param mobile mobile
     * @return ResponseVO
     */
    @ApiOperation("发送验证码")
    @GetMapping("sendSmsCode/{mobile}")
    public ResponseVO sendSmsCode(@PathVariable String mobile)
    {
        return ResponseVO.data(validateCodeService.sendSmsCode(mobile));
    }
}
