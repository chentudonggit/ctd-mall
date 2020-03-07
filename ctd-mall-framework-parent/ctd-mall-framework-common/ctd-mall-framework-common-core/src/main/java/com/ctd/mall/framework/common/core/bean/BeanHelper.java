package com.ctd.mall.framework.common.core.bean;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.dozer.DozerBeanMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * bean 转换
 *
 * @author chentudong
 * @date 2020/3/7 10:31
 * @since 1.0
 */
public class BeanHelper
{
    private static DozerBeanMapper dozerBeanMapper;

    static
    {
        dozerBeanMapper = new DozerBeanMapper();
    }

    private BeanHelper()
    {
        throw new IllegalStateException("Utility class");
    }

    /**
     * 实体转换
     *
     * @param s      源
     * @param tClass 目标
     * @param <T>    T
     * @param <S>    S
     * @return T
     */
    public static <T, S> T convert(S s, Class<T> tClass)
    {
        if (Objects.isNull(s))
        {
            return null;
        }
        return dozerBeanMapper.map(s, tClass);
    }

    /**
     * Convert list.
     *
     * @param <T>     the type parameter
     * @param <S>     the type parameter
     * @param sources the sources
     * @param clazz   the clazz
     * @return the list
     */
    public static <T, S> List<T> convert(List<S> sources, Class<T> clazz)
    {
        List<T> result = new ArrayList<>();
        for (S s : sources)
        {
            if (!AssertUtils.isNull(s))
            {
                result.add(dozerBeanMapper.map(s, clazz));
            }
        }
        return result;
    }

    /**
     * 获取参数
     *
     * @param map map
     * @param key key
     * @param <T> T
     * @return T
     */
    public static <T> T getParams(Map map, String key)
    {
        if (AssertUtils.isNull(map) || AssertUtils.isNull(key))
        {
            return null;
        }
        return (T) map.get(key);
    }
}
