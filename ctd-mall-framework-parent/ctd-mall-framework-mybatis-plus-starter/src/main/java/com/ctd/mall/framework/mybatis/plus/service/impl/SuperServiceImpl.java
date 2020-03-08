package com.ctd.mall.framework.mybatis.plus.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.ReflectionKit;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.lock.DistributedLock;
import com.ctd.mall.framework.mybatis.plus.service.SuperService;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * SuperServiceImpl
 *
 * @author chentudong
 * @date 2020/3/8 9:27
 * @since 1.0
 */
public class SuperServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements SuperService<T>
{
    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return Boolean
     */
    @Override
    public Boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg)
    {
        AssertUtils.isNull(lock, "lock 不能为空");
        AssertUtils.isNull(lockKey, "lockKey 不能为空");
        try
        {
            //加锁
            if (lock.lock(lockKey))
            {
                //判断记录是否已存在
                int count = super.count(countWrapper);
                if (count == 0)
                {
                    return super.save(entity);
                } else
                {
                    if (StringUtils.isBlank(msg))
                    {
                        msg = "已存在";
                    }
                    AssertUtils.msgDevelopment(msg);
                }
            } else
            {
                AssertUtils.msgDevelopment("锁等待超时");
            }
        } finally
        {
            lock.releaseLock(lockKey);
        }
        return true;
    }

    /**
     * 幂等性新增记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return Boolean
     */
    @Override
    public Boolean saveIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper)
    {
        return saveIdempotency(entity, lock, lockKey, countWrapper, null);
    }

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @param msg          对象已存在提示信息
     * @return Boolean
     */
    @Override
    public Boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper, String msg)
    {
        if (Objects.nonNull(entity))
        {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            if (Objects.nonNull(tableInfo) && StringUtils.isNotBlank(tableInfo.getKeyProperty()))
            {
                Object idVal = ReflectionKit.getMethodValue(cls, entity, tableInfo.getKeyProperty());
                if (com.baomidou.mybatisplus.core.toolkit.StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal)))
                {
                    if (StringUtils.isBlank(msg))
                    {
                        msg = "已存在";
                    }
                    return this.saveIdempotency(entity, lock, lockKey, countWrapper, msg);
                } else
                {
                    return updateById(entity);
                }
            } else
            {
                throw ExceptionUtils.mpe("Error:  Can not execute. Could not find @TableId.");
            }
        }
        return false;
    }

    /**
     * 幂等性新增或更新记录
     *
     * @param entity       实体对象
     * @param lock         锁实例
     * @param lockKey      锁的key
     * @param countWrapper 判断是否存在的条件
     * @return Boolean
     */
    @Override
    public Boolean saveOrUpdateIdempotency(T entity, DistributedLock lock, String lockKey, Wrapper<T> countWrapper)
    {
        return this.saveOrUpdateIdempotency(entity, lock, lockKey, countWrapper, null);
    }
}
