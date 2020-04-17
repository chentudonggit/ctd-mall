package com.ctd.mall.micro.service.user.domain.binding.menurole;

import com.ctd.springboot.common.core.enums.status.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * MenuRoleBinding
 *
 * @author chentudong
 * @date 2020/3/8 22:44
 * @since 1.0
 */
@Entity
@Table(name = "tbl_menu_role_binding")
@DynamicUpdate
@DynamicInsert
@Where(clause = "status = 'ENABLE'")
public class MenuRoleBinding implements Serializable
{
    private static final long serialVersionUID = -1216947445867703013L;

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    /**
     * 菜单id
     */
    @Column(name = "menu_id")
    private String menuId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(15) default 'ENABLE' ")
    private StatusEnum status;

    /**
     * createTime
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * updateTime
     */
    @Column(name = "update_time")
    private Date updateTime;

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

    public Date getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }

    public Date getUpdateTime()
    {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
}
