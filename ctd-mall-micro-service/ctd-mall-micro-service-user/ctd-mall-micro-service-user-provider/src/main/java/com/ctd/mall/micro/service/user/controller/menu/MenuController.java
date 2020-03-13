package com.ctd.mall.micro.service.user.controller.menu;

import com.ctd.mall.framework.common.core.vo.menu.MenuVO;
import com.ctd.mall.framework.common.core.vo.response.ResponseVO;
import com.ctd.mall.micro.service.user.service.menu.MenuService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MenuController
 *
 * @author chentudong
 * @date 2020/3/14 0:27
 * @since 1.0
 */
@RestController
@RequestMapping("menu")
public class MenuController
{

    private final MenuService menuService;

    public MenuController(MenuService menuService)
    {
        this.menuService = menuService;
    }

    /**
     * save
     *
     * @param m m
     * @return ResponseVO
     */
    @PostMapping("save")
    public ResponseVO save(@RequestBody MenuVO m)
    {
        return ResponseVO.data(menuService.save(m.getId(), m.getParentId(), m.getName(), m.getUrl(), m.getPath(),
                m.getMethod(), m.getTenantId(), m.getHidden(), m.getType(), m.getSort()));
    }
}
