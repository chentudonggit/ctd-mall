package com.ctd.mall.micro.service.user.manager.binding.menurole;

import com.ctd.mall.micro.service.user.repository.binding.menurole.MenuRoleBindingRepository;
import org.springframework.stereotype.Component;

/**
 * MenuRoleBindingManager
 *
 * @author chentudong
 * @date 2020/3/13 10:10 上午
 * @since 1.0
 */
@Component
public class MenuRoleBindingManager
{
    private final MenuRoleBindingRepository roleBindingRepository;

    public MenuRoleBindingManager(MenuRoleBindingRepository roleBindingRepository)
    {
        this.roleBindingRepository = roleBindingRepository;
    }
}
