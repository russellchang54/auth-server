package com.aek56.microservice.auth.model.security;

import java.util.Collection;
import java.util.Date;

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
    
    //管理员标识
    
    private boolean admin_flag;
    //上次权限修改时间
    
    public boolean isAdmin_flag() {
		return admin_flag;
	}

	public void setAdmin_flag(boolean admin_flag) {
		this.admin_flag = admin_flag;
	}

	public Date getLast_authority_mod() {
		return last_authority_mod;
	}

	public void setLast_authority_mod(Date last_authority_mod) {
		this.last_authority_mod = last_authority_mod;
	}

	public Date getLast_authority_load() {
		return last_authority_load;
	}

	public void setLast_authority_load(Date last_authority_load) {
		this.last_authority_load = last_authority_load;
	}

	private Date last_authority_mod;
    //上次权限加载时间
    private Date last_authority_load;
     
    
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
