package com.ctd.mall.micro.service.user.domain.binding.userrole;

import com.ctd.mall.framework.common.core.enums.status.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * UserRoleBinding
 *
 * @author chentudong
 * @date 2020/3/8 22:40
 * @since 1.0
 */
@Entity
@Table(name = "tbl_user_role_binding")
@DynamicUpdate
@DynamicInsert
public class UserRoleBinding implements Serializable
{
    private static final long serialVersionUID = 2132084825956667214L;

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    private String userId;

    /**
     * 角色id
     */
    @Column(name = "role_id")
    private String roleId;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar(15) default 'Enable' ")
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

    public String getUserId()
    {
        return userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
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
