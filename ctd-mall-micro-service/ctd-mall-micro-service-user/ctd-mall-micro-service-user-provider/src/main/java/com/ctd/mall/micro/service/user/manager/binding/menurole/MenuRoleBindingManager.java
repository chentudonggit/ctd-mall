package com.ctd.mall.micro.service.user.manager.binding.menurole;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.micro.service.user.domain.binding.menurole.MenuRoleBinding;
import com.ctd.mall.micro.service.user.manager.menu.MenuManager;
import com.ctd.mall.micro.service.user.manager.role.RoleManager;
import com.ctd.mall.micro.service.user.repository.binding.menurole.MenuRoleBindingRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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
    @Autowired
    private MenuManager menuManager;
    @Autowired
    private RoleManager roleManager;

    private final MenuRoleBindingRepository menuRoleBindingRepository;

    public MenuRoleBindingManager(MenuRoleBindingRepository menuRoleBindingRepository)
    {
        this.menuRoleBindingRepository = menuRoleBindingRepository;
    }

    /**
     * 保存
     *
     * @param id     id
     * @param menuId menuId
     * @param roleId roleId
     * @return MenuRoleBinding
     */
    @Transactional(rollbackFor = Exception.class)
    public MenuRoleBinding save(String id, String menuId, String roleId)
    {
        menuManager.findNonNullById(menuId);
        roleManager.findNonNullById(roleId);
        MenuRoleBinding binding;
        if (StringUtils.isNotBlank(id))
        {
            binding = findNonNullById(id);
        } else
        {
            binding = new MenuRoleBinding();
            binding.setCreateTime(new Date());
        }
        binding.setMenuId(menuId);
        binding.setRoleId(roleId);
        binding.setUpdateTime(new Date());
        menuRoleBindingRepository.save(binding);
        return binding;
    }

    /**
     * findById
     *
     * @param id id
     * @return MenuRoleBinding
     */
    public MenuRoleBinding findById(String id)
    {
        AssertUtils.isNull(id, "id 不能为空");
        return menuRoleBindingRepository.findById(id).orElse(null);
    }

    /**
     * findNonNullById
     *
     * @param id id
     * @return MenuRoleBinding
     */
    public MenuRoleBinding findNonNullById(String id)
    {
        MenuRoleBinding roleBinding = findById(id);
        AssertUtils.isNull(roleBinding, "id =" + id + " 记录不存在，请核对。");
        return roleBinding;
    }
}
