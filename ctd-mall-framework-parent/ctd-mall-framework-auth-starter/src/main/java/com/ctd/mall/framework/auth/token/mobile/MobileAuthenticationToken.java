package com.ctd.mall.framework.auth.token.mobile;

import com.ctd.mall.framework.common.core.utils.asserts.AssertUtils;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;

import java.util.Collection;

/**
 * MobileAuthenticationToken
 *
 * @author chentudong
 * @date 2020/3/7 20:14
 * @since 1.0
 */
public class MobileAuthenticationToken extends AbstractAuthenticationToken
{
    private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

    private final Object principal;
    private Object credentials;
    private boolean isSmsCode;

    public MobileAuthenticationToken(String mobile, String password, boolean isSmsCode)
    {
        super(null);
        this.principal = mobile;
        this.credentials = password;
        this.isSmsCode = isSmsCode;
        setAuthenticated(false);
    }

    public MobileAuthenticationToken(Object principal, Object credentials,
                                     Collection<? extends GrantedAuthority> authorities)
    {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials()
    {
        return this.credentials;
    }

    @Override
    public Object getPrincipal()
    {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated)
    {
        if (isAuthenticated)
        {
            AssertUtils.msgDevelopment(
                    "Cannot set this token to trusted - use constructor which takes a GrantedAuthority list instead");
        }
        super.setAuthenticated(false);
    }

    @Override
    public void eraseCredentials()
    {
        super.eraseCredentials();
    }

    public boolean isSmsCode()
    {
        return isSmsCode;
    }

    public void setSmsCode(boolean smsCode)
    {
        isSmsCode = smsCode;
    }
}
