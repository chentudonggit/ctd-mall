package com.ctd.mall.framework.common.core.vo.user;

import com.alibaba.fastjson.annotation.JSONField;
import com.ctd.mall.framework.common.core.vo.role.RoleVO;

import java.io.Serializable;
import java.util.List;

/**
 * UserVO
 *
 * @author chentudong
 * @date 2020/3/7 16:22
 * @since 1.0
 */
public class UserVO implements Serializable
{
    private static final long serialVersionUID = 5178238928560769099L;

    /**
     * id
     */
    @JSONField(name = "id")
    private String id;

    /**
     * username
     */
    @JSONField(name = "user_name")
    private String username;

    /**
     *
     */
    @JSONField(name = "pass_word")
    private String password;

    /**
     *
     */
    @JSONField(name = "nick_name")
    private String nickName;

    /**
     * headImgUrl
     */
    @JSONField(name = "head_img_url")
    private String headImgUrl;

    /**
     * mobile
     */
    @JSONField(name = "mobile")
    private String mobile;

    /**
     * sex
     */
    @JSONField(name = "sex")
    private Integer sex;

    /**
     * enabled
     */
    @JSONField(name = "enabled")
    private Boolean enabled;

    /**
     * type
     */
    @JSONField(name = "type")
    private String type;

    /**
     * openId
     */
    @JSONField(name = "open_id")
    private String openId;

    /**
     * roles
     */
    @JSONField(name = "roles")
    private List<RoleVO> roles;

    /**
     * roleId
     */
    @JSONField(name = "role_ids")
    private List<String> roleIds;

    /**
     * oldPassword
     */
    @JSONField(name = "old_pass_word")
    private String oldPassword;

    /**
     * newPassword
     */
    @JSONField(name = "new_pass_word")
    private String newPassword;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl)
    {
        this.headImgUrl = headImgUrl;
    }

    public String getMobile()
    {
        return mobile;
    }

    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }

    public Integer getSex()
    {
        return sex;
    }

    public void setSex(Integer sex)
    {
        this.sex = sex;
    }

    public Boolean getEnabled()
    {
        return enabled;
    }

    public void setEnabled(Boolean enabled)
    {
        this.enabled = enabled;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getOpenId()
    {
        return openId;
    }

    public void setOpenId(String openId)
    {
        this.openId = openId;
    }

    public List<RoleVO> getRoles()
    {
        return roles;
    }

    public void setRoles(List<RoleVO> roles)
    {
        this.roles = roles;
    }

    public List<String> getRoleIds()
    {
        return roleIds;
    }

    public void setRoleIds(List<String> roleIds)
    {
        this.roleIds = roleIds;
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    @Override
    public String toString()
    {
        return "UserVO{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", nickName='" + nickName + '\'' +
                ", headImgUrl='" + headImgUrl + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex=" + sex +
                ", enabled=" + enabled +
                ", type='" + type + '\'' +
                ", openId='" + openId + '\'' +
                ", roles=" + roles +
                ", roleIds='" + roleIds + '\'' +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }
}
