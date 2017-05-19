package com.aek56.microservice.auth.model.security;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.aek56.microservice.auth.common.StringHelper;
import com.aek56.microservice.auth.entity.SysMenu;
import com.aek56.microservice.auth.entity.SysRole;
import com.aek56.microservice.auth.entity.SysUser;

/**
 * The type Auth user factory.
 *
 * @author zj@aek56.com
 */
public final class AuthUserFactory
{
    
    private AuthUserFactory()
    {
    }
    
    /**
     * Create auth user.
     *
     * @param user the user
     * @return the auth user
     */
    public static AuthUser create(SysUser user)
    {
        AuthUser authUser = new AuthUser(user.getId());
        authUser.setLoginName(user.getLoginName());
        authUser.setName(user.getName());
        authUser.setEmail(user.getEmail());
        authUser.setPhone(user.getPhone());
        authUser.setMobile(user.getMobile());
        authUser.setPassword(user.getPassword());
        authUser.setEnabled(user.getEnable());
        authUser.setTenantId(user.getTenantId());
        authUser.setDeviceId(user.getDeviceId());
        authUser.setLast_authority_mod(user.getLast_authority_mod());
        authUser.setLast_authority_load(new Date());
        authUser.setAuthorities(mapToGrantedAuthorities(user.getRoles(), user.getMenus()));
        return authUser;
    }
    
    /**
     * 权限转换
     *
     * @param sysRoles 角色列表
     * @param sysMenus 菜单列表
     * @return 权限列表
     */
    private static List<SimpleGrantedAuthority> mapToGrantedAuthorities(List<SysRole> sysRoles, List<SysMenu> sysMenus)
    {
        
        List<SimpleGrantedAuthority> authorities = sysRoles.stream()
                .filter(SysRole::getEnable)
                .map(sysRole -> new SimpleGrantedAuthority(sysRole.getName()))
                .collect(Collectors.toList());
        
        // 添加基于Permission的权限信息
        sysMenus.stream().filter(menu -> StringHelper.isNotBlank(menu.getPermission())).forEach(menu -> {
            // 添加基于Permission的权限信息
            for (String permission : StringHelper.split(menu.getPermission(), ","))
            {
                authorities.add(new SimpleGrantedAuthority(permission));
            }
        });
        
        return authorities;
    }
    
}
