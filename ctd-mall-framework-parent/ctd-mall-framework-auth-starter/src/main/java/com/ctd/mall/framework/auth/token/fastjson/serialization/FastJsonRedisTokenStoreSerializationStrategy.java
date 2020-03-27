package com.ctd.mall.framework.auth.token.fastjson.serialization;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.util.IOUtils;
import com.alibaba.fastjson.util.TypeUtils;
import com.ctd.mall.framework.auth.serializer.oauth.OAuth2AuthenticationSerializer;
import com.ctd.mall.framework.auth.serializer.oauth.token.DefaultOauth2RefreshTokenSerializer;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.oauth2.common.DefaultOAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStoreSerializationStrategy;

import java.util.Objects;

/**
 * 使用fastJson
 *
 * @author chentudong
 * @date 2020/3/27 17:15
 * @since 1.0
 */
public class FastJsonRedisTokenStoreSerializationStrategy implements RedisTokenStoreSerializationStrategy
{
    private final static ParserConfig DEFAULT_REDIS_CONFIG = new ParserConfig();

    static
    {
        DEFAULT_REDIS_CONFIG.setAutoTypeSupport(true);
        init();
    }

    static
    {
        //设置fast json Json自动转换为Java对象
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);
    }

    protected static void init() {
        //自定义oauth2序列化：DefaultOAuth2RefreshToken 没有setValue方法，会导致JSON序列化为null
        DEFAULT_REDIS_CONFIG.setAutoTypeSupport(true);
        DEFAULT_REDIS_CONFIG.putDeserializer(DefaultOAuth2RefreshToken.class, new DefaultOauth2RefreshTokenSerializer());
        DEFAULT_REDIS_CONFIG.putDeserializer(OAuth2Authentication.class, new OAuth2AuthenticationSerializer());
        DEFAULT_REDIS_CONFIG.addAccept("org.springframework.security.oauth2.provider.");
        DEFAULT_REDIS_CONFIG.addAccept("org.springframework.security.oauth2.provider.client");

        TypeUtils.addMapping("org.springframework.security.oauth2.provider.OAuth2Authentication",
                OAuth2Authentication.class);
        TypeUtils.addMapping("org.springframework.security.oauth2.provider.client.BaseClientDetails",
                BaseClientDetails.class);
    }

    /**
     * 序列化对象， 使用 json 明文
     *
     * @param bytes bytes
     * @param clazz clazz
     * @param <T>   <T>
     * @return T
     */
    @Override
    public <T> T deserialize(byte[] bytes, Class<T> clazz)
    {
        AssertUtils.isNull(clazz, "clazz 不能为空");
        if (!AssertUtils.isNull(bytes))
        {
            try
            {
                return JSON.parseObject(new String(bytes, IOUtils.UTF8), clazz, DEFAULT_REDIS_CONFIG);
            } catch (Exception e)
            {
                e.printStackTrace();
                AssertUtils.msgDevelopment("redis 序列化 %s 对象出错.", clazz);
            }
        }
        return null;
    }

    /**
     * deserializeString
     *
     * @param bytes bytes
     * @return String
     */
    @Override
    public String deserializeString(byte[] bytes)
    {
        if (AssertUtils.isNull(bytes))
        {
            return null;
        }
        return new String(bytes, IOUtils.UTF8);
    }

    @Override
    public byte[] serialize(Object object)
    {
        if (Objects.nonNull(object))
        {
            try
            {
                return JSON.toJSONBytes(object, SerializerFeature.WriteClassName,
                        SerializerFeature.DisableCircularReferenceDetect);
            } catch (Exception ex)
            {
                ex.printStackTrace();
                AssertUtils.msgDevelopment("redis 序列化 %s 对象出错.", object);
            }
        }
        return new byte[0];
    }

    /**
     * serialize
     *
     * @param data data
     * @return byte[]
     */
    @Override
    public byte[] serialize(String data)
    {
        if (StringUtils.isNotBlank(data))
        {
            return data.getBytes(IOUtils.UTF8);
        }
        return new byte[0];
    }
}
