package com.ctd.mall.micro.service.user.service.binding.menurole;

import com.ctd.mall.micro.service.user.manager.binding.menurole.MenuRoleBindingManager;
import com.ctd.mall.micro.service.user.service.menurole.MenuRoleBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.ctd.mall.micro.service.user.common.vo.binding.menurole.MenuRoleBindingVO;


/**
 * MenuRoleBindingServiceImpl
 *
 * @author chentudong
 * @date 2020/3/13 10:08 上午
 * @since 1.0
 */
@RestController
public class MenuRoleBindingServiceImpl implements MenuRoleBindingService
{
    @Autowired
    private MenuRoleBindingManager menuRoleBindingManager;

    /**
     * 保存
     *
     * @param id     id
     * @param menuId menuId
     * @param roleId roleId
     * @return MenuRoleBindingVO
     */
    @Override
    public MenuRoleBindingVO save(String id, String menuId, String roleId)
    {
        return null;
    }

    /**
     * 删除所有
     *
     * @param ids ids
     * @return Boolean
     */
    @Override
    public Boolean deleteAllByIds(String[] ids)
    {
        return null;
    }
}
