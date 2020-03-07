package com.ctd.mall.framework.common.core.vo.role;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * RoleVO
 *
 * @author chentudong
 * @date 2020/3/7 16:23
 * @since 1.0
 */
public class RoleVO implements Serializable
{
    private static final long serialVersionUID = 6534130632366133165L;

    /**
     * code
     */
    @JSONField(name = "code")
    private String code;

    /**
     * name
     */
    @JSONField(name = "name")
    private String name;

    /**
     * userId
     */
    @JSONField(name = "user_id")
    private Long userId;

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

    public Long getUserId()
    {
        return userId;
    }

    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    @Override
    public String toString()
    {
        return "RoleVO{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", userId=" + userId +
                '}';
    }
}
