package com.ctd.mall.micro.service.auth.service.redis;

import com.ctd.mall.framework.auth.constant.security.SecurityConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.NoSuchClientException;
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
    private RedisTemplate<String, Object> redisTemplate;

    public RedisClientDetailsService(DataSource dataSource)
    {
        super(dataSource);
    }

    public RedisTemplate<String, Object> getRedisTemplate()
    {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate)
    {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId)
    {
        // 先从redis获取
        ClientDetails clientDetails = (ClientDetails) redisTemplate.opsForValue().get(clientRedisKey(clientId));
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
                redisTemplate.opsForValue().set(clientRedisKey(clientId), clientDetails);
                LOGGER.info("缓存clientId:{},{}", clientId, clientDetails);
            }
        } catch (NoSuchClientException e)
        {
            LOGGER.error("clientId:{},{}", clientId, clientId);
        } catch (InvalidClientException e)
        {
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
        redisTemplate.opsForValue().get(clientRedisKey(clientId));
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

        list.parallelStream().forEach(client -> redisTemplate.opsForValue().set(clientRedisKey(client.getClientId()), client));
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
