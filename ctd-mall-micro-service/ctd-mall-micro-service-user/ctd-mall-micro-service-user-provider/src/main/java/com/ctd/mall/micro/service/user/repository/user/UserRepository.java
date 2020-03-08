package com.ctd.mall.micro.service.user.repository.user;

import com.ctd.mall.micro.service.user.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository
 *
 * @author chentudong
 * @date 2020/3/8 12:05
 * @since 1.0
 */
public interface UserRepository extends JpaRepository<User, String>
{
    /**
     * 获取用户
     *
     * @param openId openId
     * @return User
     */
    User findByOpenId(String openId);
}
