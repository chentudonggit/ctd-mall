package com.ctd.mall.micro.service.user.service.menu;

import com.ctd.mall.framework.common.core.enums.method.MethodEnum;
import com.ctd.mall.framework.common.core.vo.menu.MenuVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * MenuService
 *
 * @author chentudong
 * @date 2020/3/9 9:00
 * @since 1.0
 */
@RequestMapping("menuService")
public interface MenuService
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
    @RequestMapping("save")
    MenuVO save(@RequestParam("id") String id,
                @RequestParam("parentId") String parentId,
                @RequestParam("name") String name,
                @RequestParam("url") String url,
                @RequestParam("path") String path,
                @RequestParam("method") MethodEnum method,
                @RequestParam("tenantId") String tenantId,
                @RequestParam("hidden") Boolean hidden,
                @RequestParam("type") Integer type,
                @RequestParam("sort") Integer sort);
}
