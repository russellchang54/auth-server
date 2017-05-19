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
public class SysUser extends DataEntity
{
    
    /**
     * 超级管理用户ID
     */
    public static final String ADMIN_USER_ID = "1";
    
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
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    
    /**
     * 姓名
     */
    private String name;
    
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
     * 是否可用
     */
    private Boolean enable;
    
    /**
     * 备注
     */
    private String remarks;
    
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
    
    public SysUser()
    {
        super();
    }
    
    public SysUser(String id)
    {
        super(id);
    }
    
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
    
    @Length(min = 1, max = 100)
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
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
    
    @Length(min = 0, max = 255)
    public String getRemarks()
    {
        return remarks;
    }
    
    public void setRemarks(String remarks)
    {
        this.remarks = remarks;
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
    
    public Integer getTenantId()
    {
        return tenantId;
    }
    
    public void setTenantId(Integer tenantId)
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
    
}