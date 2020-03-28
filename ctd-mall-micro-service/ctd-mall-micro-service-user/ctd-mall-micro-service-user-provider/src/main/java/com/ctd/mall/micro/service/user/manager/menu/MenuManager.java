package com.ctd.mall.micro.service.user.manager.menu;

import com.ctd.mall.framework.common.core.enums.method.MethodEnum;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.utils.param.ParamUtils;
import com.ctd.mall.framework.common.core.vo.menu.MenuVO;
import com.ctd.mall.framework.common.core.vo.page.PageVO;
import com.ctd.mall.framework.data.jpa.utils.JpaSqlUtils;
import com.ctd.mall.micro.service.user.domain.menu.Menu;
import com.ctd.mall.micro.service.user.repository.menu.MenuRepository;
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
        menu.setParentId(ParamUtils.returnParam(menu.getParentId(), parentId));
        menu.setName(name);
        menu.setUrl(ParamUtils.returnParam(menu.getUrl(), url));
        menu.setPath(ParamUtils.returnParam(menu.getPath(), path));
        menu.setMethod(ParamUtils.returnParam(menu.getMethod(), method));
        menu.setTenantId(ParamUtils.returnParam(menu.getTenantId(), tenantId));
        menu.setHidden(ParamUtils.returnParam(menu.getHidden(), hidden));
        menu.setType(type);
        menu.setSort(sort);
        menu.setUpdateTime(new Date());
        menuRepository.save(menu);
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
        if (Objects.nonNull(menu) && (!(menu.getId().equals(id))))
        {
            AssertUtils.msgUser("%s 已占有，请修改。", name);
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
        AssertUtils.isNull(menu, "id = %s 菜单不存在，请核对。", id);
        return menu;
    }

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
    public PageVO<MenuVO> findAll(String parentId, String name, Integer sort, Integer type, Boolean hidden, MethodEnum method,
                                  String tenantId, Integer page, Integer size)
    {
        return JpaSqlUtils.convert(menuRepository.findAll(specification(parentId, name, sort, type, hidden, method,
                tenantId), JpaSqlUtils.initPageable(page, size, Sort.Direction.DESC, JpaSqlUtils.UPDATE_TIME)), MenuVO.class);
    }

    /**
     * 封装查询条件
     *
     * @param parentId parentId
     * @param name     name
     * @param sort     sort
     * @param type     type
     * @param hidden   hidden
     * @param method   method
     * @param tenantId tenantId
     * @return Specification<Menu>
     */
    public Specification<Menu> specification(String parentId, String name, Integer sort, Integer type, Boolean hidden, MethodEnum method,
                                             String tenantId)
    {
        return (Root<Menu> root, CriteriaQuery<?> criteriaQuery,
                CriteriaBuilder criteriaBuilder) ->
        {
            //创建封装条件的集合
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(parentId))
            {
                predicates.add(criteriaBuilder.equal(root.get("parentId").as(String.class), parentId));
            }
            if (StringUtils.isNotBlank(name))
            {
                predicates.add(criteriaBuilder.equal(root.get("name").as(String.class), JpaSqlUtils.appendSqlLike(name)));
            }
            if (StringUtils.isNotBlank(tenantId))
            {
                predicates.add(criteriaBuilder.equal(root.get("tenantId").as(String.class), tenantId));
            }
            if (Objects.nonNull(sort))
            {
                predicates.add(criteriaBuilder.equal(root.get("sort").as(Integer.class), sort));
            }
            if (Objects.nonNull(type))
            {
                predicates.add(criteriaBuilder.equal(root.get("type").as(Integer.class), type));
            }
            if (Objects.nonNull(hidden))
            {
                predicates.add(criteriaBuilder.equal(root.get("hidden").as(Boolean.class), hidden));
            }
            if (Objects.nonNull(method))
            {
                predicates.add(criteriaBuilder.in(root.<MethodEnum>get("method")).value(method));
            }
            // 将所有条件用 and 联合起来
            if (!predicates.isEmpty())
            {
                criteriaQuery.where(criteriaBuilder.and(predicates.<Predicate>toArray(new Predicate[0])));
            }
            return criteriaQuery.getRestriction();
        };
    }
}
