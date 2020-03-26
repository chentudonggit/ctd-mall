package com.ctd.mall.micro.service.user.resolver.token;

import com.ctd.mall.framework.common.core.annotation.login.LoginUser;
import com.ctd.mall.framework.common.core.constant.security.SecurityConstants;
import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import com.ctd.mall.framework.common.core.vo.user.UserVO;
import com.ctd.mall.micro.service.user.service.user.UserService;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 处理
 *
 * @author chentudong
 * @date 2020/3/27 0:18
 * @since 1.0
 */
public class TokenArgumentResolver implements HandlerMethodArgumentResolver
{
    private final UserService userService;

    public TokenArgumentResolver(UserService userService)
    {
        this.userService = userService;
    }

    @Override
    public boolean supportsParameter(MethodParameter methodParameter)
    {
        return methodParameter.hasParameterAnnotation(LoginUser.class) && methodParameter.getParameterType().equals(UserVO.class);
    }

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
                    user = userService.findByUserName(username);
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
