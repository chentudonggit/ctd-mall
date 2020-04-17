package com.ctd.mall.micro.service.user.common.vo.binding.menurole;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.springboot.common.core.enums.status.StatusEnum;

import java.io.Serializable;

/**
 * MenuRoleBindingVO
 *
 * @author chentudong
 * @date 2020/3/13 9:55 上午
 * @since 1.0
 */
public class MenuRoleBindingVO implements Serializable
{
    private static final long serialVersionUID = -6653573873535833081L;

    /**
     * id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * 菜单id
     */
    @JSONField(name = "menu_id")
    private String menuId;

    /**
     * 角色id
     */
    @JSONField(name = "role_id")
    private String roleId;

    /**
     * 状态
     */
    @JSONField(name = "status")
    private StatusEnum status;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getMenuId()
    {
        return menuId;
    }

    public void setMenuId(String menuId)
    {
        this.menuId = menuId;
    }

    public String getRoleId()
    {
        return roleId;
    }

    public void setRoleId(String roleId)
    {
        this.roleId = roleId;
    }

    public StatusEnum getStatus()
    {
        return status;
    }

    public void setStatus(StatusEnum status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "MenuRoleBindingVO{" +
                "id='" + id + '\'' +
                ", menuId='" + menuId + '\'' +
                ", roleId='" + roleId + '\'' +
                ", status=" + status +
                '}';
    }
}
