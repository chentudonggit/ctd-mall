package com.ctd.mall.framework.auth.store.jwt;

import com.ctd.mall.framework.auth.converter.authentication.CustomUserAuthenticationConverter;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import org.springframework.cloud.bootstrap.encrypt.KeyProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.security.KeyPair;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT RSA 非对称加密令牌
 *
 * @author chentudong
 * @date 2020/3/7 16:15
 * @since 1.0
 */
@Component
public class AuthJwtTokenStore
{
    @Bean("keyProp")
    public KeyProperties keyProperties()
    {
        return new KeyProperties();
    }

    @Resource(name = "keyProp")
    private KeyProperties keyProperties;

    public TokenStore tokenStore(JwtAccessTokenConverter jwtAccessTokenConverter)
    {
        return new JwtTokenStore(jwtAccessTokenConverter);
    }

    @Bean
    public JwtAccessTokenConverter jwtAccessTokenConverter()
    {
        final JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory
                (keyProperties.getKeyStore().getLocation(), keyProperties.getKeyStore().getSecret().toCharArray())
                .getKeyPair(keyProperties.getKeyStore().getAlias());
        converter.setKeyPair(keyPair);
        DefaultAccessTokenConverter tokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        tokenConverter.setUserTokenConverter(new CustomUserAuthenticationConverter());
        return converter;
    }

    /**
     * jwt 生成token 定制化处理
     *
     * @return TokenEnhancer
     */
    @Bean
    public TokenEnhancer tokenEnhancer()
    {
        return (accessToken, authentication) -> {
            final Map<String, Object> additionalInfo = new HashMap<>(1);
            Object principal = authentication.getPrincipal();
            //增加id参数
            if (principal instanceof UserVO)
            {
                UserVO user = (UserVO) principal;
                additionalInfo.put("id", user.getId());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
