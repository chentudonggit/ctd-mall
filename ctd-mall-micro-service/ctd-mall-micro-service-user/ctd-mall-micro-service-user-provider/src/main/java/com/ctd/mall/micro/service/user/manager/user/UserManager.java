package com.ctd.mall.micro.service.user.manager.user;

import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.user.login.LoginUserVO;
import com.ctd.mall.micro.service.user.domain.user.User;
import com.ctd.mall.micro.service.user.manager.password.PassWordManager;
import com.ctd.mall.micro.service.user.repository.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;


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
    private static final Logger LOGGER = LoggerFactory.getLogger(UserManager.class);

    @Autowired
    private PassWordManager passWordManager;

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
     *
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

    /**
     * save
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
     * @param oldPassword oldPassword
     * @return User
     */
    public User save(String id, String username, String password, String nickName, String headImgUrl, String mobile,
                     Integer sex, Boolean enabled, String type, String openId, String oldPassword)
    {
        existenceMobile(id, mobile);
        existenceUsername(id, username);
        User user = getUser(id, password, oldPassword);
        if (StringUtils.isNotBlank(password))
        {
            user.setPassword(passWordManager.encode(password));
        }
        user.setUsername(returnParam(user.getUsername(), username));
        user.setNickname(returnParam(user.getNickname(), nickName));
        user.setSex(returnParam(user.getSex(), sex));
        user.setType(returnParam(user.getType(), type));
        user.setOpenId(returnParam(user.getOpenId(), openId));
        user.setHeadImgUrl(returnParam(user.getHeadImgUrl(), headImgUrl));
        user.setMobile(returnParam(user.getMobile(), mobile));
        user.setEnabled(returnParam(user.getEnabled(), enabled));
        user.setUpdateTime(new Date());
        return userRepository.save(user);
    }

    /**
     * 判断是否输入新值
     *
     * @param param    param
     * @param newParam newParam
     * @param <T>      <T>
     * @return T
     */
    private <T> T returnParam(T param, T newParam)
    {
        if (Objects.nonNull(newParam))
        {
            return newParam;
        } else
        {
            return param;
        }
    }

    /**
     * 获取用户
     *
     * @param id          id
     * @param password    password
     * @param oldPassword oldPassword
     * @return User
     */
    private User getUser(String id, String password, String oldPassword)
    {
        User user;
        if (StringUtils.isNotBlank(id))
        {
            //更新
            Optional<User> optional = userRepository.findById(id);
            if (!optional.isPresent())
            {
                LOGGER.error("id = {} 用户不存在，请核对。", id);
                AssertUtils.msgUser("无法获取该用户信息，请联系管理员。");
            }
            user = optional.get();
            if (StringUtils.isNotBlank(oldPassword))
            {
                AssertUtils.isNullToUser(password, "请输入新密码");
                passWordManager.matches(oldPassword, user.getPassword(), "旧密码不正确，请核对。");
            }
        } else
        {
            AssertUtils.isNullToUser(password, "请输入密码");
            user = new User();
            user.setCreateTime(new Date());
        }
        return user;
    }

    /**
     * 判断手机号是否占用
     *
     * @param id     id
     * @param mobile mobile
     */
    private void existenceMobile(String id, String mobile)
    {
        AssertUtils.isNullToUser(mobile, "请输入手机号");
        existence(id, mobile, userRepository.findByMobile(mobile));
    }

    /**
     * 判断用户名是否占用
     *
     * @param id       id
     * @param username username
     */
    private void existenceUsername(String id, String username)
    {
        AssertUtils.isNullToUser(username, "请输入用户名");
        existence(id, username, userRepository.findByUsername(username));
    }

    /**
     * 判断是否占用
     *
     * @param id   id
     * @param msg  msg
     * @param user user
     */
    private void existence(String id, String msg, User user)
    {
        if (Objects.nonNull(user) && (!user.getId().equals(id)))
        {
            AssertUtils.msgUser(msg + "已被占用，请重新输入。");
        }
    }
}
