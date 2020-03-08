package com.ctd.mall.micro.service.user.client.user;

import com.ctd.mall.framework.common.core.constants.eureka.server.type.EurekaServerType;
import com.ctd.mall.micro.service.user.service.user.UserService;
import org.springframework.cloud.openfeign.FeignClient;

/**
 * UserClient
 *
 * @author chentudong
 * @date 2020/3/8 12:54
 * @since 1.0
 */
@FeignClient(EurekaServerType.CENTRAL_PLATFORM_USER_SERVER)
public interface UserClient extends UserService
{
}
