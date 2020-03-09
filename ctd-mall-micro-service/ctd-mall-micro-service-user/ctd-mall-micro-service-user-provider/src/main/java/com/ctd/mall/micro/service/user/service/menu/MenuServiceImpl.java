package com.ctd.mall.micro.service.user.service.menu;

import com.ctd.mall.framework.common.core.enums.method.MethodEnum;
import com.ctd.mall.framework.common.core.vo.menu.MenuVO;
import org.springframework.web.bind.annotation.RestController;

/**
 * MenuServiceImpl
 *
 * @author chentudong
 * @date 2020/3/9 9:19
 * @since 1.0
 */
@RestController
public class MenuServiceImpl implements MenuService
{
    /**
     * save
     *
     * @param id       id
     * @param parentId parentId
     * @param name     name
     * @param url      url
     * @param path     path
     * @param method   method
     * @param tenantId tenantId
     * @param hidden   hidden
     * @param type     type
     * @param sort     sort
     * @return MenuVO
     */
    @Override
    public MenuVO save(String id, String parentId, String name, String url, String path, MethodEnum method,
                       String tenantId, Boolean hidden, Integer type, Integer sort)
    {
        return null;
    }
}
