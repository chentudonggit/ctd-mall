package com.ctd.mall.micro.service.user.manager.menu;

import com.ctd.mall.framework.common.core.enums.method.MethodEnum;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.micro.service.user.domain.menu.Menu;
import com.ctd.mall.micro.service.user.repository.menu.MenuRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

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
        AssertUtils.isNull(type, "type 不能为空");
        AssertUtils.isNull(sort, "sort 不能为空");
        AssertUtils.isNull(path, "path 不能为空");
        existenceName(id, name);
        existenceParent(parentId);
        Menu menu;
        if (StringUtils.isNotBlank(id))
        {
            menu = findNonNullById(id);
        } else
        {
            menu = new Menu();
            menu.setCreateTime(new Date());
        }
        menu.setParentId(parentId);
        menu.setName(name);
        menu.setUrl(url);
        menu.setPath(path);
        menu.setMethod(method);
        menu.setTenantId(tenantId);
        menu.setHidden(hidden);
        menu.setType(type);
        menu.setSort(sort);
        menu.setUpdateTime(new Date());
        return menu;
    }

    /**
     * 父级是否存在
     *
     * @param parentId parentId
     */
    private void existenceParent(String parentId)
    {
        if (StringUtils.isNotBlank(parentId))
        {
            findNonNullById(parentId);
        }
    }

    /**
     * 名称是否重复
     *
     * @param id   id
     * @param name name
     */
    private void existenceName(String id, String name)
    {
        AssertUtils.isNull(name, "name 不能为空");
        Menu menu = menuRepository.findByName(name);
        if (Objects.nonNull(menu) && menu.getId().equals(id))
        {
            AssertUtils.msgUser(name + " 已占有，请修改。");
        }
    }

    /**
     * findById
     *
     * @param id id
     * @return Menu
     */
    public Menu findById(String id)
    {
        AssertUtils.isNull(id, "id 不能为空");
        return menuRepository.findById(id).orElse(null);
    }

    /**
     * @param id id
     * @return Menu
     */
    public Menu findNonNullById(String id)
    {
        Menu menu = findById(id);
        AssertUtils.isNull(menu, "id = " + id + " 菜单不存在，请核对。");
        return menu;
    }
}
