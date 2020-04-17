package com.ctd.mall.micro.service.user.service.menu;

import com.ctd.springboot.common.core.enums.method.MethodEnum;
import com.ctd.springboot.common.core.vo.menu.MenuVO;
import com.ctd.springboot.common.core.vo.page.PageVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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

    /**
     * findAll
     *
     * @param parentId parentId
     * @param name     name
     * @param sort     sort
     * @param type     type
     * @param hidden   hidden
     * @param method   method
     * @param tenantId tenantId
     * @param page     page
     * @param size     size
     * @return PageVO<MenuVO>
     */
    @RequestMapping("findAll")
    PageVO<MenuVO> findAll(@RequestParam("parentId") String parentId,
                           @RequestParam("name") String name,
                           @RequestParam("sort") Integer sort,
                           @RequestParam("type") Integer type,
                           @RequestParam("hidden") Boolean hidden,
                           @RequestParam("method") MethodEnum method,
                           @RequestParam("tenantId") String tenantId,
                           @RequestParam("page") Integer page,
                           @RequestParam("size") Integer size);

    /**
     * findAllTree
     *
     * @param id id
     * @return List<MenuVO>
     */
    @RequestMapping("findAllTree")
    List<MenuVO> findAllTree(@RequestParam("id") String id);
}
