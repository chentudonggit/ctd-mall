package com.ctd.mall.framework.common.core.enums.status;

import java.util.Objects;

/**
 * StatusEnum
 *
 * @author chentudong
 * @date 2020/3/8 9:18
 * @since 1.0
 */
public enum StatusEnum
{
    /**
     * 启用
     */
    Enable(0, "启用"),

    /**
     * 禁用
     */
    Disable(1, "禁用");

    /**
     * code
     */
    private Integer code;

    /**
     * message
     */
    private String message;


    StatusEnum(int code, String message)
    {
        this.code = code;
        this.message = message;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
    {
        this.code = code;
    }

    public String getMessage()
    {
        return message;
    }

    public void setMessage(String message)
    {
        this.message = message;
    }

    public static StatusEnum value(Integer key)
    {
        if (Objects.isNull(key))
        {
            return null;
        }
        switch (key)
        {
            case 1:
                return StatusEnum.Enable;
            case 0:
                return StatusEnum.Disable;
            default:
                return null;
        }
    }
}
