package com.aek56.microservice.auth.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;

import com.aek56.microservice.auth.common.DataEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户Entity
 *
 * @author zj@aek56.com
 */
public class SysUser extends DataEntity {
    
	private static final long serialVersionUID = 1L;

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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
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
     * 是否可用
     */
    private Boolean enable;
	/**
	 * 是否管理员(0:否;1:是)
	 */
	private Boolean adminFlag;
    
    
    /**
     * 角色列表
     */
    private List<SysRole> roles = new ArrayList<>();
    
    /**
     * 菜单列表
     */
    private List<SysMenu> menus = new ArrayList<>();
    
    /**
     * 模块列表
     */
    private List<Map<String, String>> modules = new ArrayList<Map<String, String>>();
    
    /**
     * 机构列表
     */
    private List<Map<String, String>> orgs = new ArrayList<Map<String, String>>();
    
    @Length(min = 1, max = 100)
    public String getLoginName()
    {
        return loginName;
    }
    
    public void setLoginName(String loginName)
    {
        this.loginName = loginName;
    }
    
    @Length(min = 1, max = 50)
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    @Email
    @Length(max = 200)
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @Length(min = 0, max = 200)
    public String getMobile()
    {
        return mobile;
    }
    
    public void setMobile(String mobile)
    {
        this.mobile = mobile;
    }
    
    public Boolean getEnable()
    {
        return enable;
    }
    
    public void setEnable(Boolean enable)
    {
        this.enable = enable;
    }
    
    public List<SysRole> getRoles()
    {
        return roles;
    }
    
    public void setRoles(List<SysRole> roles)
    {
        this.roles = roles;
    }
    
    @JsonIgnore
    public List<SysMenu> getMenus()
    {
        return menus;
    }
    
    public void setMenus(List<SysMenu> menus)
    {
        this.menus = menus;
    }
    
    public Long getTenantId()
    {
        return tenantId;
    }
    
    public void setTenantId(Long tenantId)
    {
        this.tenantId = tenantId;
    }
    
    public String getDeviceId()
    {
        return deviceId;
    }
    
    public void setDeviceId(String deviceId)
    {
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

	public Boolean getAdminFlag() {
		return adminFlag;
	}

	public void setAdminFlag(Boolean adminFlag) {
		this.adminFlag = adminFlag;
	}

	public List<Map<String, String>> getOrgs() {
		return orgs;
	}

	public void setOrgs(List<Map<String, String>> orgs) {
		this.orgs = orgs;
	}
}