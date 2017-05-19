package com.aek56.microservice.auth.redis;

import java.util.Date;
import java.util.List;

import com.aek56.microservice.auth.entity.SysTenant;

/**
 * 
 * <一句话功能简述>设备缓存实体
 * <功能详细描述>
 * 
 * @author  Shuangwf
 * @version  1.0, 2017年5月16日
 */
public class DeviceRedis
{
    private String token;
    
    private Long tanentId;/*当前租户*/
    
    private Long inTanentId;/*所属租户*/
    
    private List<SysTenant> sysTenants; /*可用租户列表*/
       
    private String deviceType;
    
    public Long getInTanentId() {
		return inTanentId;
	}

	public void setInTanentId(Long inTanentId) {
		this.inTanentId = inTanentId;
	}

	public List<SysTenant> getSysTenants() {
		return sysTenants;
	}

	public void setSysTenants(List<SysTenant> sysTenants) {
		this.sysTenants = sysTenants;
	}

	private Date loginTime;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public Long getTanentId()
    {
        return tanentId;
    }
    
    public void setTanentId(Long tanentId)
    {
        this.tanentId = tanentId;
    }
    
    public String getDeviceType()
    {
        return deviceType;
    }
    
    public void setDeviceType(String deviceType)
    {
        this.deviceType = deviceType;
    }
    
    public Date getLoginTime()
    {
        return loginTime;
    }
    
    public void setLoginTime(Date loginTime)
    {
        this.loginTime = loginTime;
    }
    
}
