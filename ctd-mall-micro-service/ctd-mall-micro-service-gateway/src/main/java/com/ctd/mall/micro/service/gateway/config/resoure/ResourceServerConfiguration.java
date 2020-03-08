package com.ctd.mall.micro.service.gateway.config.resoure;

import com.ctd.mall.framework.auth.properties.security.SecurityProperties;
import com.ctd.mall.micro.service.gateway.handler.json.JsonAccessDeniedHandler;
import com.ctd.mall.micro.service.gateway.handler.success.auth.Oauth2AuthSuccessHandler;
import com.ctd.mall.micro.service.gateway.manager.CustomAuthenticationManager;
import com.ctd.mall.micro.service.gateway.manager.permission.PermissionAuthManager;
import com.ctd.mall.micro.service.gateway.point.json.JsonAuthenticationEntryPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.web.server.ServerBearerTokenAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationEntryPointFailureHandler;

/**
 * ResourceServerConfiguration
 *
 * @author chentudong
 * @date 2020/3/8 14:02
 * @since 1.0
 */
@Configuration
public class ResourceServerConfiguration
{
    @Autowired
    private SecurityProperties securityProperties;

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PermissionAuthManager permissionAuthManager;

    @Bean
    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        //认证处理器
        ReactiveAuthenticationManager customAuthenticationManager = new CustomAuthenticationManager(tokenStore);
        JsonAuthenticationEntryPoint entryPoint = new JsonAuthenticationEntryPoint();
        //token转换器
        ServerBearerTokenAuthenticationConverter tokenAuthenticationConverter = new ServerBearerTokenAuthenticationConverter();
        tokenAuthenticationConverter.setAllowUriQueryParameter(true);
        //oauth2认证过滤器
        AuthenticationWebFilter oauth2Filter = new AuthenticationWebFilter(customAuthenticationManager);
        oauth2Filter.setServerAuthenticationConverter(tokenAuthenticationConverter);
        oauth2Filter.setAuthenticationFailureHandler(new ServerAuthenticationEntryPointFailureHandler(entryPoint));
        oauth2Filter.setAuthenticationSuccessHandler(new Oauth2AuthSuccessHandler());
        http.addFilterAt(oauth2Filter, SecurityWebFiltersOrder.AUTHENTICATION);

        ServerHttpSecurity.AuthorizeExchangeSpec authorizeExchange = http.authorizeExchange();
        if (securityProperties.getAuth().getUrls().length > 0) {
            authorizeExchange.pathMatchers(securityProperties.getAuth().getUrls()).authenticated();
        }
        if (securityProperties.getIgnore().getUrls().length > 0) {
            authorizeExchange.pathMatchers(securityProperties.getIgnore().getUrls()).permitAll();
        }
        authorizeExchange
                .pathMatchers(HttpMethod.OPTIONS).permitAll()
                .anyExchange()
                .access(permissionAuthManager)
                .and()
                .exceptionHandling()
                .accessDeniedHandler(new JsonAccessDeniedHandler())
                .authenticationEntryPoint(entryPoint)
                .and()
                .headers()
                .frameOptions()
                .disable()
                .and()
                .httpBasic().disable()
                .csrf().disable();
        return http.build();
    }
}
