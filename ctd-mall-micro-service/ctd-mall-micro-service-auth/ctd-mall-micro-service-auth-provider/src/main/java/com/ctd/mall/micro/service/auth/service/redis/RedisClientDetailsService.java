package com.ctd.mall.micro.service.auth.service.redis;

import com.alibaba.fastjson.JSON;
import com.ctd.springboot.common.core.constant.security.SecurityConstants;
import com.ctd.springboot.redis.repository.RedisRepository;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.util.CollectionUtils;

import javax.sql.DataSource;
import java.util.List;
import java.util.Objects;

/**
 * RedisClientDetailsService
 *
 * @author chentudong
 * @date 2020/3/14 13:30
 * @since 1.0
 */
public class RedisClientDetailsService extends JdbcClientDetailsService
{
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisClientDetailsService.class);

    private final RedisRepository redisRepository;

    public RedisClientDetailsService(DataSource dataSource, RedisRepository redisRepository)
    {
        super(dataSource);
        this.redisRepository = redisRepository;
    }


    @Override
    public ClientDetails loadClientByClientId(String clientId)
    {
        // 先从redis获取
        ClientDetails clientDetails = null;
        String value = redisRepository.get(clientRedisKey(clientId));
        if (StringUtils.isNotBlank(value))
        {
            clientDetails = JSON.parseObject(value, BaseClientDetails.class);
        }
        if (Objects.isNull(clientDetails))
        {
            clientDetails = cacheAndGetClient(clientId);
        }
        return clientDetails;
    }

    /**
     * 缓存client并返回client
     *
     * @param clientId clientId
     * @return ClientDetails
     */
    private ClientDetails cacheAndGetClient(String clientId)
    {
        // 从数据库读取
        ClientDetails clientDetails = null;
        try
        {
            clientDetails = super.loadClientByClientId(clientId);
            if (Objects.nonNull(clientDetails))
            {
                // 写入redis缓存
                redisRepository.setExpire(clientRedisKey(clientId), clientDetails, 1800);
                LOGGER.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        } catch (NoSuchClientException e)
        {
            e.printStackTrace();
            LOGGER.error("clientId:{},{}", clientId, clientId);
        } catch (InvalidClientException e)
        {
            e.printStackTrace();
            LOGGER.error("cacheAndGetClient-invalidClient:{}", clientId, e);
        }
        return clientDetails;
    }

    @Override
    public void updateClientDetails(ClientDetails clientDetails)
    {
        super.updateClientDetails(clientDetails);
        cacheAndGetClient(clientDetails.getClientId());
    }

    @Override
    public void updateClientSecret(String clientId, String secret)
    {
        super.updateClientSecret(clientId, secret);
        cacheAndGetClient(clientId);
    }

    @Override
    public void removeClientDetails(String clientId)
    {
        super.removeClientDetails(clientId);
        removeRedisCache(clientId);
    }

    /**
     * 删除redis缓存
     *
     * @param clientId clientId
     */
    private void removeRedisCache(String clientId)
    {
        redisRepository.del(clientRedisKey(clientId));
    }

    /**
     * 将oauth_client_details全表刷入redis
     */
    public void loadAllClientToCache()
    {
        List<ClientDetails> list = super.listClientDetails();
        if (CollectionUtils.isEmpty(list))
        {
            LOGGER.error("oauth_client_details表数据为空，请检查");
            return;
        }

        list.parallelStream().forEach(client -> redisRepository.set(clientRedisKey(client.getClientId()), client));
    }

    /**
     * clientRedisKey
     *
     * @param clientId clientId
     * @return String
     */
    private String clientRedisKey(String clientId)
    {
        return SecurityConstants.CACHE_CLIENT_KEY + ":" + clientId;
    }
}
