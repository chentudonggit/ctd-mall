package com.ctd.mall.framework.common.core.utils.asserts;

import com.ctd.mall.framework.common.core.exception.InternalException;
import com.ctd.mall.framework.common.core.exception.UnifiedException;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;

/**
 * 校验
 *
 * @author chentudong
 * @date 2020/3/7 10:46
 * @since 1.0
 */
public final class AssertUtils
{
    private AssertUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 判空抛异常给开发者
     *
     * @param obj obj
     * @param msg msg
     */
    public static void isNull(Object obj, String msg)
    {
        if (isNull(obj))
        {
            msgDevelopment(msg);
        }
    }

    /**
     * isNullToUser
     *
     * @param obj obj
     * @param msg msg
     */
    public static void isNullToUser(Object obj, String msg)
    {
        if (isNull(obj))
        {
            msgUser(msg);
        }
    }

    /**
     * boolean isNull
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean isNull(Object obj)
    {
        return !nonNull(obj);
    }

    /**
     * 判断是否为空 size = 0 视为空
     *
     * @param obj obj
     * @return boolean
     */
    public static boolean nonNull(Object obj)
    {
        if (Objects.nonNull(obj))
        {
            if (obj instanceof String)
            {
                return StringUtils.isNotBlank(obj.toString());
            } else if (obj instanceof Collection)
            {
                return !((Collection) obj).isEmpty();
            } else if (obj instanceof Map)
            {
                return !((Map) obj).isEmpty();
            } else if (obj.getClass().isArray())
            {
                return Array.getLength(obj) > 0;
            }
            return true;
        }
        return false;
    }

    /**
     * 为空， 抛出RuntimeException
     *
     * @param obj obj
     * @param e   e
     */
    public static void isNull(Object obj, RuntimeException e)
    {
        if (isNull(obj))
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * 判断为空,返回 null
     *
     * @param obj obj
     * @param <T> <T>
     * @return T
     */
    public static <T> T isNullReturnNull(Object obj)
    {
        return isNull(obj) ? null : (T) obj;
    }

    /**
     * msgDevelopment
     *
     * @param msg msg
     */
    public static void msgDevelopment(String msg)
    {
        throw new InternalException(msg(msg));
    }

    /**
     * msgUser
     *
     * @param msg msg
     */
    public static void msgUser(String msg)
    {
        throw new UnifiedException(msg(msg));
    }

    /**
     * msg
     *
     * @param msg msg
     * @return String
     */
    public static String msg(String msg)
    {
        return StringUtils.isBlank(msg) ? "网络异常，请稍后重试！" : msg;
    }
}
