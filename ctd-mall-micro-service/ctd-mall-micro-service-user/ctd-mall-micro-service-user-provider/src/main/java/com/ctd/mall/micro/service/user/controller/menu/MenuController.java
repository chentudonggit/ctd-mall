package com.ctd.mall.micro.service.user.controller.menu;

import com.ctd.springboot.common.core.enums.method.MethodEnum;
import com.ctd.springboot.common.core.utils.param.ParamUtils;
import com.ctd.springboot.common.core.vo.menu.MenuVO;
import com.ctd.springboot.common.core.vo.response.ResponseVO;
import com.ctd.mall.micro.service.user.service.menu.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

/**
 * MenuController
 *
 * @author chentudong
 * @date 2020/3/14 0:27
 * @since 1.0
 */
@RestController
@RequestMapping("menu")
@Api(tags = "菜单")
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
    @ApiOperation("保存/更新")
    @PostMapping("save")
    public ResponseVO save(@RequestBody MenuVO m)
    {
        return ResponseVO.data(menuService.save(m.getId(), m.getParentId(), m.getName(), m.getUrl(), m.getPath(),
                m.getMethod(), m.getTenantId(), m.getHidden(), m.getType(), m.getSort()));
    }

    /**
     * findAll
     *
     * @param map map
     * @return ResponseVO
     */
    @ApiOperation("查询所有")
    @PostMapping("findAll")
    public ResponseVO findAll(@RequestBody ModelMap map)
    {
        return ResponseVO.data(menuService.findAll(ParamUtils.getParam(map, "parentId"),
                ParamUtils.getParam(map, "name"),
                ParamUtils.getParam(map, "sort"),
                ParamUtils.getParam(map, "type"),
                ParamUtils.getParam(map, "hidden"),
                MethodEnum.value(ParamUtils.getParam(map, "method")),
                ParamUtils.getParam(map, "tenantId"),
                ParamUtils.getParam(map, "page"),
                ParamUtils.getParam(map, "size")));
    }

    /**
     * 获取所有的菜单，并封装树状结构
     *
     * @param id id
     * @return ResponseVO
     */
    @ApiOperation("获取所有的菜单，并封装树状结构")
    @GetMapping("findAllTree")
    public ResponseVO findAllTree(String id)
    {
        return ResponseVO.data(menuService.findAllTree(id));
    }
}
