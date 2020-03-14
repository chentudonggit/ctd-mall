package com.ctd.mall.micro.service.user.manager.role;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.micro.service.user.domain.role.Role;
import com.ctd.mall.micro.service.user.repository.role.RoleRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * RoleManager
 *
 * @author chentudong
 * @date 2020/3/14 16:20
 * @since 1.0
 */
@Component
public class RoleManager
{
    private final RoleRepository roleRepository;

    public RoleManager(RoleRepository roleRepository)
    {
        this.roleRepository = roleRepository;
    }

    /**
     * 保存角色
     *
     * @param id   id
     * @param code code
     * @param name name
     * @return Role
     */
    public Role save(String id, String code, String name)
    {
        existenceCode(id, code);
        existenceName(id, name);
        existenceCodeAndName(id, code, name);
        Role role;
        if (StringUtils.isNotBlank(id))
        {
            role = findNonNullById(id);
        } else
        {
            role = new Role();
            role.setCreateTime(new Date());
        }
        role.setCode(code);
        role.setName(name);
        role.setUpdateTime(new Date());
        roleRepository.save(role);
        return role;
    }

    /**
     * findById
     *
     * @param id id
     * @return Role
     */
    public Role findById(String id)
    {
        AssertUtils.isNull(id, "id 不能为空");
        return roleRepository.findById(id).orElse(null);
    }

    /**
     * findNonNullById
     *
     * @param id id
     * @return Role
     */
    public Role findNonNullById(String id)
    {
        Role role = findById(id);
        AssertUtils.isNull(role, "id = " + id + " 角色不存在，请核对。");
        return role;
    }


    /**
     * 判断名称和code 是否重复
     *
     * @param id   id
     * @param code code
     * @param name name
     */
    private void existenceCodeAndName(String id, String code, String name)
    {
        AssertUtils.isNull(code, "code 不能为空");
        AssertUtils.isNull(name, "name 不能为空");
        existence(id, roleRepository.findByCodeAndName(code, name), "code = " + code + ", name = " + name + " 已占用，请修改。");
    }

    /**
     * 判断名称是否重复
     *
     * @param id   id
     * @param name name
     */
    private void existenceName(String id, String name)
    {
        AssertUtils.isNull(name, "name 不能为空");
        existence(id, roleRepository.findByName(name), "name = " + name + " 已占用，请修改。");
    }

    /**
     * 判断code 是否重复
     *
     * @param id   id
     * @param code code
     */
    private void existenceCode(String id, String code)
    {
        AssertUtils.isNull(code, "code 不能为空");
        existence(id, roleRepository.findByCode(code), "code = " + code + " 已占用，请修改。");
    }

    /**
     * existence
     *
     * @param id   id
     * @param role role
     * @param msg  msg
     */
    private void existence(String id, Role role, String msg)
    {
        if (Objects.nonNull(role) && (!role.getId().equals(id)))
        {
            AssertUtils.msgUser(msg);
        }
    }
}
