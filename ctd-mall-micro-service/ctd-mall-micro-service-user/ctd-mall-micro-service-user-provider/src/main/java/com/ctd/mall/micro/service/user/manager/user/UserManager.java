package com.ctd.mall.micro.service.user.manager.user;

import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.utils.param.ParamUtils;
import com.ctd.mall.framework.common.core.vo.page.PageVO;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.framework.data.jpa.utils.JpaSqlUtils;
import com.ctd.mall.micro.service.user.domain.user.User;
import com.ctd.mall.micro.service.user.manager.password.PassWordManager;
import com.ctd.mall.micro.service.user.repository.user.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;


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
     * @return UserVO
     */
    public UserVO findByOpenId(String openId)
    {
        AssertUtils.isNull(openId, "openId 不能为空");
        User user = userRepository.findByOpenId(openId);
        if (AssertUtils.isNull(user))
        {
            return null;
        }
        return BeanHelper.convert(user, UserVO.class);
    }

    /**
     * findNonNullByOpenId
     *
     * @param openId openId
     * @return UserVO
     */
    public UserVO findNonNullByOpenId(String openId)
    {
        UserVO loginUser = findByOpenId(openId);
        AssertUtils.isNull(loginUser, "openId = %s , 用户不存在，请核对。", openId);
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
        user.setUsername(username);
        user.setNickname(ParamUtils.returnParam(user.getNickname(), nickName));
        user.setSex(ParamUtils.returnParam(user.getSex(), sex));
        user.setType(ParamUtils.returnParam(user.getType(), type));
        user.setOpenId(ParamUtils.returnParam(user.getOpenId(), openId));
        user.setHeadImgUrl(ParamUtils.returnParam(user.getHeadImgUrl(), headImgUrl));
        user.setMobile(mobile);
        user.setEnabled(ParamUtils.returnParam(user.getEnabled(), enabled));
        user.setUpdateTime(new Date());
        return userRepository.save(user);
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
        existence(id, username, findByName(username));
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
            AssertUtils.msgUser("%s 已被占用，请重新输入。", msg);
        }
    }

    /**
     * 名称获取用户
     *
     * @param username username
     * @return User
     */
    public User findByName(String username)
    {
        AssertUtils.isNullToUser(username, "请输入用户名");
        return userRepository.findByUsername(username);
    }

    /**
     * findByMobile
     *
     * @param mobile mobile
     * @return User
     */
    public User findByMobile(String mobile)
    {
        AssertUtils.isNullToUser(mobile, "请输入手机号");
        return userRepository.findByMobile(mobile);
    }

    /**
     * findDetailsByUserId
     *
     * @param userId userId
     * @return User
     */
    public User findByUserId(String userId)
    {
        AssertUtils.isNull(userId, "userId 不能为空");
        return userRepository.findById(userId).orElse(null);
    }

    /**
     * findDetailsByUserId
     *
     * @param userId userId
     * @return User
     */
    public User findNonNullByUserId(String userId)
    {
        User user = findByUserId(userId);
        AssertUtils.isNull(user, "userId = %s 用户不存在, 请核对.", userId);
        return user;
    }

    /**
     * 查询所有
     *
     * @param username username
     * @param nickname nickname
     * @param mobile   mobile
     * @param sex      sex
     * @param enabled  enabled
     * @param type     type
     * @param page     page
     * @param size     size
     * @return PageVO<UserVO>
     */
    public PageVO<UserVO> findAll(String username, String nickname, String mobile, Integer sex, Boolean enabled,
                                  String type, Integer page, Integer size)
    {
        return JpaSqlUtils.convert(userRepository.findAll(specification(username, nickname, mobile, sex, enabled, type),
                JpaSqlUtils.initPageable(page, size, Sort.Direction.DESC, JpaSqlUtils.UPDATE_TIME)), UserVO.class);
    }


    /**
     * 多条件查询
     *
     * @param username username
     * @param nickname nickname
     * @param mobile   mobile
     * @param sex      sex
     * @param enabled  enabled
     * @param type     type
     * @return Specification<User>
     */
    public Specification<User> specification(String username, String nickname, String mobile, Integer sex, Boolean enabled, String type)
    {
        return (Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
        {
            //创建封装条件的集合
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(nickname))
            {
                predicates.add(criteriaBuilder.equal(root.get("nickname").as(String.class), JpaSqlUtils.appendSqlLike(nickname)));
            }
            if (StringUtils.isNotBlank(username))
            {
                predicates.add(criteriaBuilder.like(root.get("username").as(String.class), JpaSqlUtils.appendSqlLike(username)));
            }
            if (StringUtils.isNotBlank(mobile))
            {
                predicates.add(criteriaBuilder.like(root.get("mobile").as(String.class), JpaSqlUtils.appendSqlLike(mobile)));
            }
            if (Objects.nonNull(sex))
            {
                predicates.add(criteriaBuilder.equal(root.get("sex").as(Integer.class), sex));
            }
            if (Objects.nonNull(enabled))
            {
                predicates.add(criteriaBuilder.equal(root.get("enabled").as(Boolean.class), enabled));
            }
            if (StringUtils.isNotBlank(type))
            {
                predicates.add(criteriaBuilder.equal(root.get("type").as(String.class), type));
            }
            // 将所有条件用 and 联合起来
            if (!predicates.isEmpty())
            {
                criteriaQuery.where(criteriaBuilder.and(predicates.<Predicate>toArray(new Predicate[0])));
            }
            return criteriaQuery.getRestriction();
        };
    }
}
