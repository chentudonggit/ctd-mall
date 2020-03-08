package com.ctd.mall.micro.service.user.service.user;

import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.framework.common.core.vo.user.login.LoginUserVO;
import com.ctd.mall.micro.service.user.domain.user.User;
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
     * 保存用户
     *
     * @param id          id
     * @param username    username
     * @param password    password
     * @param nickName    nickName
     * @param headImgUrl  headImgUrl
     * @param mobile      mobile
     * @param sex         sex
     * @param enabled     enabled
     * @param type        type
     * @param openId      openId
     * @param roleIds     roleIds
     * @param oldPassword oldPassword
     * @return UserVO
     */
    @Override
    public UserVO save(String id, String username, String password, String nickName, String headImgUrl, String mobile,
                       Integer sex, Boolean enabled, String type, String openId, String[] roleIds, String oldPassword)
    {
        User user = userManager.save(id, username, password, nickName, headImgUrl, mobile, sex, enabled, type, openId, oldPassword);
        return BeanHelper.convert(user, UserVO.class);
    }

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
