package com.ctd.mall.framework.common.core.vo.menu;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.mall.framework.common.core.enums.method.MethodEnum;

import java.io.Serializable;
import java.util.Set;

/**
 * 菜单
 *
 * @author chentudong
 * @date 2020/3/9 9:03
 * @since 1.0
 */
public class MenuVO implements Serializable
{
    private static final long serialVersionUID = -5335524456445365347L;

    /**
     * id
     */
    private String id;

    /**
     * parentId
     */
    @JSONField(name = "parent_id")
    private String parentId;

    /**
     * name
     */
    @JSONField(name = "name")
    private String name;

    /**
     *
     */
    @JSONField(name = "url")
    private String url;

    /**
     * path
     */
    @JSONField(name = "path")
    private String path;

    /**
     * sort
     */
    @JSONField(name = "sort")
    private Integer sort;

    /**
     * type
     */
    @JSONField(name = "type")
    private Integer type;

    /**
     * hidden
     */
    @JSONField(name = "hidden")
    private Boolean hidden;

    /**
     * method
     */
    @JSONField(name = "method")
    private MethodEnum method;

    /**
     * roleIds
     */
    @JSONField(name = "role_ids")
    private Set<String> roleIds;

    /**
     * menuIds
     */
    @JSONField(name = "menu_ids")
    private Set<String> menuIds;

    /**
     * tenantId
     */
    @JSONField(name = "tenant_id")
    private String tenantId;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getPath()
    {
        return path;
    }

    public void setPath(String path)
    {
        this.path = path;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Boolean getHidden()
    {
        return hidden;
    }

    public void setHidden(Boolean hidden)
    {
        this.hidden = hidden;
    }

    public MethodEnum getMethod()
    {
        return method;
    }

    public void setMethod(MethodEnum method)
    {
        this.method = method;
    }

    public Set<String> getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(Set<String> roleIds)
    {
        this.roleIds = roleIds;
    }

    public Set<String> getMenuIds()
    {
        return menuIds;
    }

    public void setMenuIds(Set<String> menuIds)
    {
        this.menuIds = menuIds;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    @Override
    public String toString()
    {
        return "MenuVO{" +
                "id='" + id + '\'' +
                ", parentId='" + parentId + '\'' +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", path='" + path + '\'' +
                ", sort=" + sort +
                ", type=" + type +
                ", hidden=" + hidden +
                ", method=" + method +
                ", roleIds=" + roleIds +
                ", menuIds=" + menuIds +
                ", tenantId='" + tenantId + '\'' +
                '}';
    }
}
