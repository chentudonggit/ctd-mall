package com.ctd.mall.micro.service.user.domain.role;

import com.ctd.mall.framework.common.core.enums.status.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Role
 *
 * @author chentudong
 * @date 2020/3/8 21:53
 * @since 1.0
 */
@Entity
@Table(name = "tbl_role")
@DynamicUpdate
@DynamicInsert
public class Role implements Serializable
{
    private static final long serialVersionUID = 4353836008107490132L;

    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    /**
     * code
     */
    @Column(name = "code")
    private String code;

    /**
     * name
     */
    @Column(name = "name")
    private String name;

    /**
     * tenantId
     */
    @Column(name = "tenant_id")
    private String tenantId;

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

    public String getCode()
    {
        return code;
    }

    public void setCode(String code)
    {
        this.code = code;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
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
