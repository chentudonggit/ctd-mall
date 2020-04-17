package com.ctd.mall.micro.service.user.service.binding.menurole;

import com.ctd.springboot.common.core.bean.BeanHelper;
import com.ctd.mall.micro.service.user.common.vo.binding.menurole.MenuRoleBindingVO;
import com.ctd.mall.micro.service.user.manager.binding.menurole.MenuRoleBindingManager;
import com.ctd.mall.micro.service.user.service.menurole.MenuRoleBindingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;


/**
 * MenuRoleBindingServiceImpl
 *
 * @author chentudong
 * @date 2020/3/13 10:08 上午
 * @since 1.0
 */
@ApiIgnore
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
        return BeanHelper.convert(menuRoleBindingManager.save(id, menuId, roleId), MenuRoleBindingVO.class);
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
