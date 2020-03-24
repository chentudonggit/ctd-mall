package com.ctd.mall.micro.service.user.controller.binding.menurole;

import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.micro.service.user.common.vo.binding.menurole.MenuRoleBindingVO;
import com.ctd.mall.micro.service.user.service.menurole.MenuRoleBindingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MenuRoleBindingController
 *
 * @author chentudong
 * @date 2020/3/24 22:44
 * @since 1.0
 */
@RestController
@RequestMapping("menu-role-binding")
@Api(tags = "菜单角色绑定")
public class MenuRoleBindingController
{
    private final MenuRoleBindingService menuRoleBindingService;

    public MenuRoleBindingController(MenuRoleBindingService menuRoleBindingService)
    {
        this.menuRoleBindingService = menuRoleBindingService;
    }

    /**
     * save
     *
     * @param binding binding
     * @return ResponseVO
     */
    @ApiOperation("保存/更新")
    @PostMapping("save")
    public ResponseVO save(@RequestBody MenuRoleBindingVO binding)
    {
        return ResponseVO.data(menuRoleBindingService.save(binding.getId(), binding.getMenuId(), binding.getRoleId()));
    }
}
