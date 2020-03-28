package com.ctd.mall.framework.common.core.enums.method;

import org.apache.commons.lang3.StringUtils;

/**
 * MethodEnum
 *
 * @author chentudong
 * @date 2020/3/8 23:02
 * @since 1.0
 */
public enum MethodEnum
{
    /**
     *
     */
    Get,

    /**
     * Post
     */
    Post,

    /**
     * Put
     */
    Put,

    /**
     * Delete
     */
    Delete;

    public static MethodEnum value(String key)
    {
        if (StringUtils.isBlank(key))
        {
            return null;
        }

        switch (key)
        {
            case "Get":
            case "GET":
            case "get":
                return Get;
            case "Post":
            case "POST":
            case "post":
                return Post;
            case "Put":
            case "PUT":
            case "put":
                return Put;
            case "Delete":
            case "DELETE":
            case "delete":
                return Delete;
            default:
                return null;
        }
    }
}
