package com.aek56.microservice.auth.security.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek56.microservice.auth.entity.SysMenu;
import com.aek56.microservice.auth.entity.SysRole;
import com.aek56.microservice.auth.entity.SysUser;
//import com.aek.platform.system.api.service.ISystemService;
import com.aek56.microservice.auth.mapper.SysMenuMapper;
import com.aek56.microservice.auth.mapper.SysRoleMapper;
import com.aek56.microservice.auth.mapper.SysUserMapper;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 *
 * @author zj@aek56.com
 */
@Service
@Transactional(readOnly = true)
public class SystemService {

	/**
	 * 系统用户Mapper
	 */
	@Autowired
	private SysUserMapper sysUserMapper;

	/**
	 * 系统角色Mapper
	 */
	@Autowired
	private SysRoleMapper sysRoleMapper;

	/**
	 * 系统菜单Mapper
	 */
	@Autowired
	private SysMenuMapper sysMenuMapper;

	public SysUser getUserByLoginName(String loginName) {
		SysUser user = sysUserMapper.getByLoginName(loginName);
		if (user == null) {
			return null;
		}
		user.setLoginName(loginName);
		return user;
	}
	
	public SysUser getUserById(Long userId) {
		return sysUserMapper.get(userId);
	}

	/**
	 * 初始化权限
	 * @param user
	 * @param tenantId
	 */
	public void getPerms(SysUser user, Long tenantId) {
		List<SysRole> roles = sysRoleMapper.findListByUser(user.getId(), tenantId);
		processRole(roles);

		user.setRoles(roles);

		List<SysMenu> menuList = null;
		List<Map<String, String>> modules = null;
		List<Map<String, String>> orgs = null;
		boolean admin = this.isOrgAdmin(user.getId(), tenantId);
		if (user.getAdminFlag()||admin) {// 显示机构下所有的模块
			modules = this.sysRoleMapper.getOrgModule(tenantId);
			menuList =sysMenuMapper.findListByOrg(tenantId);
			Map<String, String> tenantMap = this.sysRoleMapper.getTenant(tenantId);
			String parentIds = tenantMap.get("parent_ids");
			if (StringUtils.isNotBlank(parentIds)) {
				orgs = this.sysRoleMapper.getOrg(tenantId, parentIds + "," + tenantId);
			}
		} else {// 根据角色找模块
			modules = this.sysRoleMapper.getOrgModuleByRole(user.getId(), tenantId);
			menuList =sysMenuMapper.findListByRole(user.getId(), tenantId);
			orgs = this.sysRoleMapper.getOrgByRole(user.getId());
		}
		processMenu(menuList);
		user.setModules(modules);
		user.setMenus(menuList);
		user.setOrgs(orgs);
	}

	/**
	 * 去除code为空的数据
	 * @param roleList
	 */
	private void processRole(List<SysRole> roleList) {
		if(roleList!=null&&!roleList.isEmpty()){
			for (Iterator<SysRole> iter = roleList.iterator();iter.hasNext();) {
				if(StringUtils.isBlank(iter.next().getCode())){
					iter.remove();
				}
			}
		}
	}
	
	/**
	 * 去除code为空的数据
	 * @param menuList
	 */
	private void processMenu(List<SysMenu> menuList) {
		if(menuList!=null&&!menuList.isEmpty()){
			for (Iterator<SysMenu> iter = menuList.iterator();iter.hasNext();) {
				if(StringUtils.isBlank(iter.next().getCode())){
					iter.remove();
				}
			}
		}
	}

	/**
	 * 是否当前机构管理员
	 * 
	 * @param userId
	 *            当前登录用户的id
	 * @param tenantId
	 *            要访问的租户id
	 */
	public boolean isOrgAdmin(Long userId, Long tenantId) {
		Boolean orgAdmin = this.sysRoleMapper.isOrgAdmin(userId, tenantId);
		if (orgAdmin == null) {
			return false;
		}
		return orgAdmin;
	}

	/**
	 * 是否子机构
	 * 
	 * @param parentTenantId
	 * @param subTenantId
	 * @return
	 */
	public boolean isSubOrg(Long parentTenantId, Long subTenantId) {
		Boolean flag = this.sysRoleMapper.isSubOrg(parentTenantId, subTenantId);
		if (flag == null) {
			return false;
		}
		return flag;
	}

	/**
	 * 取当前登录用户的角色
	 * 
	 * @param userId
	 *            当前登录用户的id
	 * @param tenantId
	 *            要访问的租户id
	 */
	public void getRoleByUser(Long userId, Long tenantId) {

	}
}
