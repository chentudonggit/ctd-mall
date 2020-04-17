package com.ctd.mall.micro.service.user.service.user;

import com.ctd.springboot.common.core.vo.page.PageVO;
import com.ctd.springboot.common.core.vo.user.UserVO;
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
    @RequestMapping("save")
    UserVO save(@RequestParam("id") String id,
                @RequestParam("username") String username,
                @RequestParam("password") String password,
                @RequestParam("nickName") String nickName,
                @RequestParam("headImgUrl") String headImgUrl,
                @RequestParam("mobile") String mobile,
                @RequestParam("sex") Integer sex,
                @RequestParam("enabled") Boolean enabled,
                @RequestParam("type") String type,
                @RequestParam("openId") String openId,
                @RequestParam("roleIds") String[] roleIds,
                @RequestParam("oldPassword") String oldPassword);

    /**
     * findByOpenId
     *
     * @param openId openId
     * @return UserVO
     */
    @RequestMapping("findByOpenId")
    UserVO findByOpenId(@RequestParam("openId") String openId);

    /**
     * findByUserName
     *
     * @param userName userName
     * @return UserVO
     */
    @RequestMapping("findByUserName")
    UserVO findByUserName(@RequestParam("userName") String userName);

    /**
     * findByMobile
     *
     * @param mobile mobile
     * @return UserVO
     */
    @RequestMapping("findByMobile")
    UserVO findByMobile(@RequestParam("mobile") String mobile);

    /**
     * findDetailsByUserId
     *
     * @param userId userId
     * @return UserVO
     */
    @RequestMapping("findDetailsByUserId")
    UserVO findDetailsByUserId(@RequestParam("userId") String userId);

    /**
     * findAll
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
    @RequestMapping("findAll")
    PageVO<UserVO> findAll(@RequestParam("username") String username,
                           @RequestParam("nickname") String nickname,
                           @RequestParam("mobile") String mobile,
                           @RequestParam("sex") Integer sex,
                           @RequestParam("enabled") Boolean enabled,
                           @RequestParam("type") String type,
                           @RequestParam("page") Integer page,
                           @RequestParam("size") Integer size);
}
