package com.ctd.mall.micro.service.user.service.user;

import com.ctd.mall.framework.common.core.vo.user.login.LoginUserVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * UserService
 *
 * @author chentudong
 * @date 2020/3/8 12:08
 * @since 1.0
 */
@RequestMapping("userService")
public interface UserService
{
    /**
     * findByOpenId
     *
     * @param openId openId
     * @return LoginUserVO
     */
    @RequestMapping("findByOpenId")
    LoginUserVO findByOpenId(@RequestParam("openId") String openId);

    /**
     * findByUserName
     *
     * @param userName userName
     * @return UserVO
     */
    @RequestMapping("findByUserName")
    LoginUserVO findByUserName(@RequestParam("userName") String userName);

    /**
     * @param mobile mobile
     * @return LoginUserVO
     */
    @RequestMapping("findByMobile")
    LoginUserVO findByMobile(@RequestParam("mobile") String mobile);
}
