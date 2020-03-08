package com.ctd.mall.micro.service.user.controller.user;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.user.service.user.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * UserController
 *
 * @author chentudong
 * @date 2020/3/8 18:59
 * @since 1.0
 */
@RestController
@RequestMapping("user")
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    /**
     * @param u u
     * @return ResponseVO
     */
    @PostMapping("save")
    public ResponseVO save(@RequestBody UserVO u)
    {
        List<String> roleIds = u.getRoleIds();
        String[] arrRoleIds = null;
        if (AssertUtils.nonNull(roleIds))
        {
            arrRoleIds = roleIds.toArray(new String[0]);
        }
        return ResponseVO.data(userService.save(u.getId(),
                u.getUsername(),
                u.getPassword(),
                u.getNickName(),
                u.getHeadImgUrl(),
                u.getMobile(),
                u.getSex(),
                u.getEnabled(),
                u.getType(),
                u.getOpenId(),
                arrRoleIds,
                u.getOldPassword()));
    }
}
