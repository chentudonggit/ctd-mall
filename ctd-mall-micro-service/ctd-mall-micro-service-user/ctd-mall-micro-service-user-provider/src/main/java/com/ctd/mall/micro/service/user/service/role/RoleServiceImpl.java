package com.ctd.mall.micro.service.user.service.role;

import com.ctd.springboot.common.core.bean.BeanHelper;
import com.ctd.springboot.common.core.vo.page.PageVO;
import com.ctd.springboot.common.core.vo.role.RoleVO;
import com.ctd.mall.micro.service.user.manager.role.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * RoleServiceImpl
 *
 * @author chentudong
 * @date 2020/3/14 16:19
 * @since 1.0
 */
@ApiIgnore
@RestController
public class RoleServiceImpl implements RoleService
{
    @Autowired
    private RoleManager roleManager;

    /**
     * 保存
     *
     * @param id   id
     * @param code code
     * @param name name
     * @return RoleVO
     */
    @Override
    public RoleVO save(String id, String code, String name)
    {
        return BeanHelper.convert(roleManager.save(id, code, name), RoleVO.class);
    }

    /**
     * findAll
     *
     * @param code code
     * @param name name
     * @param page page
     * @param size size
     * @return PageVO<RoleVO>
     */
    @Override
    public PageVO<RoleVO> findAll(String code, String name, Integer page, Integer size)
    {
        return roleManager.findAll(code, name, page, size);
    }
}
