package com.ctd.mall.micro.service.user.manager.menu;

import com.ctd.mall.framework.common.core.enums.method.MethodEnum;
import com.ctd.mall.micro.service.user.domain.menu.Menu;
import com.ctd.mall.micro.service.user.repository.menu.MenuRepository;
import org.springframework.stereotype.Component;

/**
 * MenuManager
 *
 * @author chentudong
 * @date 2020/3/9 9:21
 * @since 1.0
 */
@Component
public class MenuManager
{
    private final MenuRepository menuRepository;

    public MenuManager(MenuRepository menuRepository)
    {
        this.menuRepository = menuRepository;
    }

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
     * @return Menu
     */
    public Menu save(String id, String parentId, String name, String url, String path, MethodEnum method,
                     String tenantId, Boolean hidden, Integer type, Integer sort)
    {
        return null;
    }
}
