package com.ctd.mall.micro.service.user.controller.role;

import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.framework.common.core.vo.role.RoleVO;
import com.ctd.mall.micro.service.user.service.role.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RoleController
 *
 * @author chentudong
 * @date 2020/3/14 16:49
 * @since 1.0
 */
@RestController
@RequestMapping("role")
@Api(tags = "角色")
public class RoleController
{
    private final RoleService roleService;

    public RoleController(RoleService roleService)
    {
        this.roleService = roleService;
    }

    /**
     * 保存
     *
     * @param r r
     * @return ResponseVO
     */
    @ApiOperation("保存/更新")
    @PostMapping("save")
    public ResponseVO save(@RequestBody RoleVO r)
    {
        return ResponseVO.data(roleService.save(r.getId(), r.getCode(), r.getName()));
    }
}
