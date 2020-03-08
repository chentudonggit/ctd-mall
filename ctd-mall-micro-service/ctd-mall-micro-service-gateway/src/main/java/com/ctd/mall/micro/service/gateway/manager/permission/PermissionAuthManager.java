package com.ctd.mall.micro.service.gateway.manager.permission;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

/**
 * PermissionAuthManager
 *
 * @author chentudong
 * @date 2020/3/8 14:28
 * @since 1.0
 */
@Component
public class PermissionAuthManager implements ReactiveAuthorizationManager<AuthorizationContext>
{
    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map(auth -> {
            return new AuthorizationDecision(true);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

}
