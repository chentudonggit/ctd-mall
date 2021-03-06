package com.ctd.mall.micro.service.user.controller.user;

import com.ctd.springboot.common.core.annotation.login.LoginUser;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.utils.param.ParamUtils;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.springboot.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.user.service.user.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
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
@Api("用户")
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
    @ApiOperation("保存/更新")
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
                u.getNickname(),
                u.getHeadImgUrl(),
                u.getMobile(),
                u.getSex(),
                u.getEnabled(),
                u.getType(),
                u.getOpenId(),
                arrRoleIds,
                u.getOldPassword()));
    }

    /**
     * 获取登录用户的信息
     *
     * @param user user
     * @return ResponseVO
     */
    @ApiOperation("获取登录用户的信息")
    @PostMapping("loginUserInfo")
    public ResponseVO loginUserInfo(@LoginUser(allInfo = true) UserVO user)
    {
        return ResponseVO.data(user);
    }

    /**
     * findAll
     *
     * @param map map
     * @return ResponseVO
     */
    @ApiOperation("获取所有用户")
    @PostMapping("findAll")
    public ResponseVO findAll(@RequestBody ModelMap map)
    {
        return ResponseVO.data(userService.findAll(ParamUtils.getParam(map, "username"),
                ParamUtils.getParam(map, "nick_name"),
                ParamUtils.getParam(map, "mobile"),
                ParamUtils.getParam(map, "sex"),
                ParamUtils.getParam(map, "enabled"),
                ParamUtils.getParam(map, "type"),
                ParamUtils.getParam(map, "page"),
                ParamUtils.getParam(map, "size")));
    }
}
