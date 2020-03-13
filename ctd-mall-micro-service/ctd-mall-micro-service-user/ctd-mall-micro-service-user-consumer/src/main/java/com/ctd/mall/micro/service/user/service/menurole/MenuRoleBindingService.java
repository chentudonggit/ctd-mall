package com.ctd.mall.micro.service.user.service.menurole;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ctd.mall.micro.service.user.common.vo.binding.menurole.MenuRoleBindingVO;


/**
 * MenuRoleBindingService
 *
 * @author chentudong
 * @date 2020/3/13 9:50 上午
 * @since 1.0
 */
@RequestMapping("menuRoleBindingService")
public interface MenuRoleBindingService
{

    /**
     * 保存
     *
     * @param id     id
     * @param menuId menuId
     * @param roleId roleId
     * @return MenuRoleBindingVO
     */
    @RequestMapping("save")
    MenuRoleBindingVO save(@RequestParam("id") String id,
                           @RequestParam("menuId") String menuId,
                           @RequestParam("roleId") String roleId);


    /**
     * 删除所有
     *
     * @param ids ids
     * @return Boolean
     */
    @RequestMapping("deleteAllByIds")
    Boolean deleteAllByIds(@RequestParam("ids") String[] ids);
}
