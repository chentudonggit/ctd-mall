package com.ctd.mall.framework.common.core.utils;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * ParamUtils
 *
 * @author chentudong
 * @date 2020/3/7 17:08
 * @since 1.0
 */
public final class ParamUtils
{
    private ParamUtils()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 获取参数
     *
     * @param map map
     * @param key key
     * @param <T> <T>
     * @return T
     */
    public static <T> T getParam(Map map, String key)
    {
        if (AssertUtils.nonNull(map) && StringUtils.isNotBlank(key))
        {
            return (T) map.get(key);
        }
        return null;
    }
}
