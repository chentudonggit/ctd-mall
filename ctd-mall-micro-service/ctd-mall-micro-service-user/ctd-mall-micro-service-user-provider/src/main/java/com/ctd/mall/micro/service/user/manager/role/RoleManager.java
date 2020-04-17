package com.ctd.mall.micro.service.user.manager.role;

import com.ctd.mall.micro.service.user.domain.role.Role;
import com.ctd.mall.micro.service.user.repository.role.RoleRepository;
import com.ctd.springboot.common.core.utils.asserts.AssertUtils;
import com.ctd.springboot.common.core.vo.page.PageVO;
import com.ctd.springboot.common.core.vo.role.RoleVO;
import com.ctd.springboot.data.jpa.utils.JpaSqlUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
        AssertUtils.isNull(role, "id = %s 角色不存在，请核对。", id);
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
        existence(id, roleRepository.findByCodeAndName(code, name), "code = %s , name = %s 已占用，请修改。", code, name);
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
        existence(id, roleRepository.findByName(name), "name = %s 已占用，请修改。", name);
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
        existence(id, roleRepository.findByCode(code), "code = %s 已占用，请修改。", code);
    }

    /**
     * existence
     *
     * @param id   id
     * @param role role
     * @param msg  msg
     */
    private void existence(String id, Role role, String msg, Object... args)
    {
        if (Objects.nonNull(role) && (!role.getId().equals(id)))
        {
            AssertUtils.msgUser(msg, args);
        }
    }

    /**
     * 查询所有
     *
     * @param code code
     * @param name name
     * @param page page
     * @param size size
     * @return PageVO<RoleVO>
     */
    public PageVO<RoleVO> findAll(String code, String name, Integer page, Integer size)
    {
        Specification<Role> specification = (Root<Role> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) ->
        {
            //创建封装条件的集合
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(code))
            {
                predicates.add(criteriaBuilder.equal(root.get("code").as(String.class), code));
            }
            if (StringUtils.isNotBlank(name))
            {
                predicates.add(criteriaBuilder.like(root.get("name").as(String.class), JpaSqlUtils.appendSqlLike(name)));
            }
            // 将所有条件用 and 联合起来
            if (!predicates.isEmpty())
            {
                criteriaQuery.where(criteriaBuilder.and(predicates.<Predicate>toArray(new Predicate[0])));
            }
            return criteriaQuery.getRestriction();
        };
        return JpaSqlUtils.convert(roleRepository.findAll(specification,
                JpaSqlUtils.initPageable(page, size, Sort.Direction.DESC, JpaSqlUtils.UPDATE_TIME)), RoleVO.class);
    }
}
