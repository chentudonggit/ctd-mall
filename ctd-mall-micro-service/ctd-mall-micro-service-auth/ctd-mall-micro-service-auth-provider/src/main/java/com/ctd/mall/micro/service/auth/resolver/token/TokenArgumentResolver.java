package com.ctd.mall.micro.service.auth.resolver.token;

import com.ctd.mall.framework.auth.constant.security.SecurityConstants;
import com.ctd.mall.framework.common.core.annotation.login.LoginUser;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.user.client.user.UserClient;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * Token to User
 *
 * @author chentudong
 * @date 2020/3/26 0:17
 * @since 1.0
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver
{
    private final UserClient userClient;

    public TokenArgumentResolver(UserClient userClient)
    {
        this.userClient = userClient;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter)
    {
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(UserVO.class);
    }

    /**
     * 解析token
     *
     * @param methodParameter       methodParameter
     * @param modelAndViewContainer modelAndViewContainer
     * @param nativeWebRequest      nativeWebRequest
     * @param webDataBinderFactory  webDataBinderFactory
     * @return Object
     * @throws Exception Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception
    {
        LoginUser loginUser = methodParameter.getParameterAnnotation(LoginUser.class);
        if (Objects.nonNull(loginUser))
        {
            HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
            if (Objects.nonNull(request))
            {
                String userId = request.getHeader(SecurityConstants.USER_ID_HEADER);
                String username = request.getHeader(SecurityConstants.USER_HEADER);
                String roles = request.getHeader(SecurityConstants.ROLE_HEADER);
                AssertUtils.isNullToUser(username, "服务器异常，请联系管理员。");
                UserVO user;
                if (loginUser.allInfo())
                {
                    //详细信息
                    user = userClient.findByUserName(username);
                } else
                {
                    //简略信息
                    user = new UserVO();
                    user.setId(userId);
                    user.setUsername(username);
                }
                return user;
            }
        }
        return null;
    }
}
