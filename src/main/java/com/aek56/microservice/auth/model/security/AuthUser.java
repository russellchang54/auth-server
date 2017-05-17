package com.aek56.microservice.auth.model.security;

import java.util.Collection;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.aek56.microservice.auth.model.security.AbstractAuthUser;
import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Security User
 *
 * @author zj@aek56.com
 */
public class AuthUser extends AbstractAuthUser
{
    
    /**
     * id
     */
    private String id;
    
    /**
     * 设备Id
     */
    
    private String deviceId;
    
   

	/**
     * 登录名
     */
    private String loginName;
    
    /**
     * 机构Id
     */
    private Integer tenantId;
    
    /**
     * 姓名
     */
    private String name;
    
    /**
     * 密码
     */
    private String password;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 电话
     */
    private String phone;
    
    /**
     * 手机
     */
    private String mobile;
    
    /**
     * 权限
     */
    private Collection<SimpleGrantedAuthority> authorities;
    
    /**
     * 锁定
     */
    private boolean enabled;
    
    public AuthUser(String id)
    {
        this.id = id;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @Override
    public String getUsername()
    {
        return loginName;
    }
    
    public String getLoginName()
    {
        return loginName;
    }
    
    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    @JsonIgnore
    @Override
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    public void setAuthorities(Collection<SimpleGrantedAuthority> authorities)
    {
        this.authorities = authorities;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public String getMobile()
    {
        return mobile;
    }
    
    @Override
    public Collection<SimpleGrantedAuthority> getAuthorities()
    {
        return authorities;
    }
    
    @Override
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public Integer getTenantId()
    {
        return tenantId;
    }
    
    public void setTenantId(Integer tenantId)
    {
        this.tenantId = tenantId;
    }
    
    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
    
}
