package com.aek56.microservice.auth.model.security;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Security User
 *
 * @author zj@aek56.com
 */
public class AuthUser extends AbstractAuthUser {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * id
     */
    private Long id;
    
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
    private Long tenantId;
    
    /**
     * 密码
     */
    private String password;
	/**
	 * 所属租户名称
	 */
	private String tenantName;
	/**
	 * 真实姓名
	 */
	private String realName;
	/**
	 * 归属部门ID
	 */
	private Long deptId;
	/**
	 * 归属部门名称
	 */
	private String deptName;
    
    /**
     * 邮箱
     */
    private String email;
    
    /**
     * 手机
     */
    private String mobile;
    
    /**
     * 权限
     */
    private Collection<SimpleGrantedAuthority> authorities;
    
    /**
     * 模块列表
     */
    private List<Map<String, String>> modules;
    
    /**
     * 机构列表
     */
    private List<Map<String, String>> orgs;
    
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
    
    public AuthUser(Long id)
    {
        this.id = id;
    }
    
    public Long getId()
    {
        return id;
    }
    
    public void setId(Long id)
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
    public String getEmail()
    {
        return email;
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
    
    public Long getTenantId()
    {
        return tenantId;
    }
    
    public void setTenantId(Long tenantId)
    {
        this.tenantId = tenantId;
    }
    
    public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public List<Map<String, String>> getModules() {
		return modules;
	}

	public void setModules(List<Map<String, String>> modules) {
		this.modules = modules;
	}

	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public Long getDeptId() {
		return deptId;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public List<Map<String, String>> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Map<String, String>> orgs) {
		this.orgs = orgs;
	}
    
}
