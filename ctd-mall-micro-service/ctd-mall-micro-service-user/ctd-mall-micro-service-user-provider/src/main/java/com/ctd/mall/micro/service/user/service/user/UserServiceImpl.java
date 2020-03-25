package com.ctd.mall.micro.service.user.service.user;

import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.user.domain.user.User;
import com.ctd.mall.micro.service.user.manager.user.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * UserServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 12:28
 * @since 1.0
 */
@ApiIgnore
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
    @Transactional(rollbackFor = Exception.class)
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
     * @return UserVO
     */
    @Override
    public UserVO findByOpenId(String openId)
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
    public UserVO findByUserName(String userName)
    {
        return BeanHelper.convert(userManager.findByName(userName), UserVO.class);
    }

    /**
     * @param mobile mobile
     * @return UserVO
     */
    @Override
    public UserVO findByMobile(String mobile)
    {
        return BeanHelper.convert(userManager.findByMobile(mobile), UserVO.class);
    }
}
