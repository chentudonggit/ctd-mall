package com.ctd.mall.micro.service.user.domain.menu;

import com.ctd.mall.framework.common.core.enums.method.MethodEnum;
import com.ctd.mall.framework.common.core.enums.status.StatusEnum;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Menu
 *
 * @author chentudong
 * @date 2020/3/8 22:51
 * @since 1.0
 */
@Entity
@Table(name = "tbl_menu")
@DynamicUpdate
@DynamicInsert
@Where(clause = "status = 'Enable'")
public class Menu implements Serializable
{
    private static final long serialVersionUID = -9208261293231161370L;
    /**
     * id
     */
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(length = 60)
    private String id;

    /**
     * 父级id
     */
    @Column(name = "parent_id")
    private String parentId;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;

    /**
     * url
     */
    @Column(name = "url")
    private String url;

    /**
     * path
     */
    @Column(name = "path")
    private String path;

    /**
     * method
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "method", columnDefinition = "varchar(15) default 'Get' ")
    private MethodEnum method;

    /**
     * tenantId
     */
    @Column(name = "tenant_id")
    private String tenantId;

    /**
     *hidden
     */
    @Column(name = "hidden", columnDefinition = "int(2) default '0' ")
    private Boolean hidden;

    /**
     * type
     */
    @Column(name = "type")
    private Integer type;

    /**
     * sort
     */
    @Column(name = "sort")
    private Integer sort;

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

    public MethodEnum getMethod()
    {
        return method;
    }

    public void setMethod(MethodEnum method)
    {
        this.method = method;
    }

    public String getTenantId()
    {
        return tenantId;
    }

    public void setTenantId(String tenantId)
    {
        this.tenantId = tenantId;
    }

    public Boolean getHidden()
    {
        return hidden;
    }

    public void setHidden(Boolean hidden)
    {
        this.hidden = hidden;
    }

    public Integer getType()
    {
        return type;
    }

    public void setType(Integer type)
    {
        this.type = type;
    }

    public Integer getSort()
    {
        return sort;
    }

    public void setSort(Integer sort)
    {
        this.sort = sort;
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
