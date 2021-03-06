package com.aek56.microservice.auth.model.security;

import java.util.Date;

public class TokenInfo {
	private String token;

	private Long tanentId;/* 当前租户 */

	private Long inTanentId;/* 所属租户 */

	private String deviceType;

	public Long getInTanentId() {
		return inTanentId;
	}

	public void setInTanentId(Long inTanentId) {
		this.inTanentId = inTanentId;
	}

	private Date loginTime;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Long getTanentId() {
		return tanentId;
	}

	public void setTanentId(Long tanentId) {
		this.tanentId = tanentId;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public Date getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

}
