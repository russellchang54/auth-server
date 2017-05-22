package com.aek56.microservice.auth.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.aek56.microservice.auth.security.service.SystemService;
import com.aek56.microservice.auth.entity.SysUser;
import com.aek56.microservice.auth.model.security.AuthUserFactory;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	/**
	 * 系统服务
	 */
	@Autowired
	private SystemService systemService;

	@Override
	public UserDetails loadUserByUsername(String loginName) {
		SysUser user = systemService.getUserByLoginName(loginName);

		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with username '%s'.", loginName));
		}

		Long tenantId = user.getTenantId();
		systemService.getPerms(user, tenantId);
		return AuthUserFactory.create(user);
	}
}
