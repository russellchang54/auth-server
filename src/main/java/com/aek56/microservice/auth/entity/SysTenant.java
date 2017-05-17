package com.aek56.microservice.auth.entity;

import java.util.Date;
import com.aek56.microservice.auth.common.DataEntity;

/*import com.aek.common.core.base.BaseModel;
import com.aek.ebey.sys.model.custom.HplTenant;
import com.aek.ebey.sys.model.custom.SuperviseTenant;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;*/

/**
 * <p>
 * 医院机构表
 * </p>
 *
 * @author aek
 * @since 2017-05-06
 */

public class SysTenant extends DataEntity {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getTenantType() {
		return tenantType;
	}
	public void setTenantType(Integer tenantType) {
		this.tenantType = tenantType;
	}
	public Integer getCommercialUse() {
		return commercialUse;
	}
	public void setCommercialUse(Integer commercialUse) {
		this.commercialUse = commercialUse;
	}
	public Integer getTrial() {
		return trial;
	}
	public void setTrial(Integer trial) {
		this.trial = trial;
	}
	public Integer getSubTenantLimit() {
		return subTenantLimit;
	}
	public void setSubTenantLimit(Integer subTenantLimit) {
		this.subTenantLimit = subTenantLimit;
	}
	public Integer getSubTenant() {
		return subTenant;
	}
	public void setSubTenant(Integer subTenant) {
		this.subTenant = subTenant;
	}
	public Integer getUserLimit() {
		return userLimit;
	}
	public void setUserLimit(Integer userLimit) {
		this.userLimit = userLimit;
	}
	public Date getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	public Integer getOrigin() {
		return origin;
	}
	public void setOrigin(Integer origin) {
		this.origin = origin;
	}
	public Integer getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Long getAdminId() {
		return adminId;
	}
	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}
	private static final long serialVersionUID = 1L;

	/**
	 * 名称
	 */
	//@ApiModelProperty(value = "机构名称")
	private String name;

	/**
	 * 租户类型[1=医疗机构,2=监管机构,3=设备供应商,4=设备维修商,5=配件供应商]
	 */
	//@ApiModelProperty(value = "租户类型[1=医疗机构,2=监管机构,3=设备供应商,4=设备维修商,5=配件供应商]")
	//@TableField(value = "tenant_type")
	private Integer tenantType;

	/**
	 * 是否试用[0=试用,1=正式]
	 */
	//@ApiModelProperty(value = "是否试用[0=试用,1=正式]")
	//@TableField(value = "commercial_use")
	private Integer commercialUse;

	/**
	 * 是否测试
	 */
	//@ApiModelProperty(value = "是否为测试[0=是,1=不是]")
	private Integer trial;

	/**
	 * 创建子机构数量限制(0,不可创建下级机构)
	 */
	//@ApiModelProperty(value = "允许创建下级机构数量(0 不可创建下级机构)")
	//@TableField(value = "sub_tenant_limit")
	private Integer subTenantLimit;
	/**
	 * 已创建下级机构数量
	 */
	//@ApiModelProperty(value = "已创建下级机构数量")
	//@TableField(value = "sub_tenant")
	private Integer subTenant;
	/**
	 * 创建用户数量限制(0,不可创建用户)
	 */
	//@TableField(value = "user_limit")
	private Integer userLimit;
	/**
	 * 租户到期时间
	 */
	//@ApiModelProperty(value = "机构有效时间(值为null,永久有效)")
	//@TableField(value = "expire_time")
	private Date expireTime;

	/**
	 * 机构来源(后台创建，前台注册，渠道商创建)
	 */
	//@ApiModelProperty(value = "1后台创建，2前台注册")
	private Integer origin;
	/**
	 * 审核状态
	 */
	//@ApiModelProperty(value = "审核状态[1待审核,2自动通过,3人工通过,4已拒绝]")
	//@TableField(value = "audit_status")
	private Integer auditStatus;
	/**
	 * 管理员ID
	 */
	//@TableField(value = "admin_id")
	private Long adminId;	

}
