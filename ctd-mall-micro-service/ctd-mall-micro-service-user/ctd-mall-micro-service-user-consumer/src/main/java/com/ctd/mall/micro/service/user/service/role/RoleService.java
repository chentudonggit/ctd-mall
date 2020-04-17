package com.ctd.mall.micro.service.user.service.role;

import com.ctd.springboot.common.core.vo.page.PageVO;
import com.ctd.springboot.common.core.vo.role.RoleVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * RoleService
 *
 * @author chentudong
 * @date 2020/3/14 16:15
 * @since 1.0
 */
@RequestMapping("roleService")
public interface RoleService
{
    /**
     * 保存
     *
     * @param id   id
     * @param code code
     * @param name name
     * @return RoleVO
     */
    @RequestMapping("save")
    RoleVO save(@RequestParam("id") String id,
                @RequestParam("code") String code,
                @RequestParam("name") String name);

    /**
     * findAll
     *
     * @param code code
     * @param name name
     * @param page page
     * @param size size
     * @return PageVO<RoleVO>
     */
    @RequestMapping("findAll")
    PageVO<RoleVO> findAll(@RequestParam("code") String code,
                           @RequestParam("name") String name,
                           @RequestParam("page") Integer page,
                           @RequestParam("size") Integer size);
}
