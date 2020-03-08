package com.ctd.mall.micro.service.user.service.user;

import com.ctd.mall.framework.common.core.vo.user.login.LoginUserVO;
import com.ctd.mall.micro.service.user.manager.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 12:28
 * @since 1.0
 */

@RestController
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserManager userManager;

    /**
     * findByOpenId
     *
     * @param openId openId
     * @return LoginUserVO
     */
    @Override
    public LoginUserVO findByOpenId(String openId)
    {
        return userManager.findNonNullByOpenId(openId);
    }

    /**
     * findByUserName
     *
     * @param userName userName
     * @return UserVO
     */
    @Override
    public LoginUserVO findByUserName(String userName)
    {
        return null;
    }

    /**
     * @param mobile mobile
     * @return LoginUserVO
     */
    @Override
    public LoginUserVO findByMobile(String mobile)
    {
        return null;
    }
}
