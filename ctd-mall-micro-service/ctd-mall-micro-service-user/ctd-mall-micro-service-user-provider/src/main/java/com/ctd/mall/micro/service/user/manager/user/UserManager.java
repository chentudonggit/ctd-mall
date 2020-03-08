package com.ctd.mall.micro.service.user.manager.user;

import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.user.login.LoginUserVO;
import com.ctd.mall.micro.service.user.domain.user.User;
import com.ctd.mall.micro.service.user.repository.user.UserRepository;
import org.springframework.stereotype.Component;


/**
 * UserManager
 *
 * @author chentudong
 * @date 2020/3/8 12:59
 * @since 1.0
 */
@Component
public class UserManager
{
    private final UserRepository userRepository;

    public UserManager(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    /**
     * 获取用户
     *
     * @param openId openId
     * @return LoginUserVO
     */
    public LoginUserVO findByOpenId(String openId)
    {
        AssertUtils.isNull(openId, "openId 不能为空");
        User user = userRepository.findByOpenId(openId);
        if (AssertUtils.isNull(user))
        {
            return null;
        }
        return BeanHelper.convert(user, LoginUserVO.class);
    }

    /**
     * findNonNullByOpenId
     * @param openId openId
     * @return LoginUserVO
     */
    public LoginUserVO findNonNullByOpenId(String openId)
    {
        LoginUserVO loginUser = findByOpenId(openId);
        AssertUtils.isNull(loginUser, "openId = " + openId + ", 用户不存在，请核对。");
        //用户权限
        return loginUser;
    }
}
