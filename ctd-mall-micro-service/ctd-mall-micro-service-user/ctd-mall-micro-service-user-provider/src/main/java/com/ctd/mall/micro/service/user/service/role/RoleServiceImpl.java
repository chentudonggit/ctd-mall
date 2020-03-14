package com.ctd.mall.micro.service.user.service.role;

import com.ctd.mall.framework.common.core.bean.BeanHelper;
import com.ctd.mall.framework.common.core.vo.role.RoleVO;
import com.ctd.mall.micro.service.user.manager.role.RoleManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * RoleServiceImpl
 *
 * @author chentudong
 * @date 2020/3/14 16:19
 * @since 1.0
 */
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
}
