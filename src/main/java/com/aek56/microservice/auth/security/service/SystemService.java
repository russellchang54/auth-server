package com.aek56.microservice.auth.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.aek56.microservice.auth.entity.SysMenu;
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

	// User

	// @Override
	public SysUser getUserByLoginName(String loginName) {
		SysUser user = sysUserMapper.getByLoginName(loginName);
		if (user == null) {
			return null;
		}
		user.setLoginName(loginName);
		Long userId = user.getId();
		user.setRoles(sysRoleMapper.findListByUserId(userId));

		List<SysMenu> menuList;
		// 超级管理员
		if (user.getAdminFlag()) {
			menuList = sysMenuMapper.findAllList();
		} else {
			menuList = sysMenuMapper.findListByUserId(userId);
		}

		user.setMenus(menuList);
		boolean admin = this.isOrgAdmin(Long.valueOf(userId), Long.valueOf(user.getTenantId()));
		List<Map<String, String>> modules = null;
		if (admin) {// 显示机构下所有的模块
			modules = this.sysRoleMapper.getOrgModule(Long.valueOf(user.getTenantId()));
		} else {// 根据角色找模块
			modules = this.sysRoleMapper.getOrgModuleByRole(Long.valueOf(userId), Long.valueOf(user.getTenantId()));
		}
		user.setModules(modules);
		// boolean subOrg = this.isSubOrg(37L, 50L);
		return user;
	}

	public SysUser getUserById(Long userId) {
		SysUser user = sysUserMapper.get(userId);
		if (user != null) {
			user.setRoles(sysRoleMapper.findListByUserId(userId));
		}
		return sysUserMapper.get(userId);
	}

	// Menu

	public List<SysMenu> getMenuNav(Long userId) {
		return makeTree(getMenuListByUserId(userId), true);
	}

	public List<SysMenu> getMenuTree(Long userId) {
		return makeTree(getMenuListByUserId(userId), false);
	}

	public List<SysMenu> getMenuList(String userId) {
		List<SysMenu> resultList = new ArrayList<>();
		// 按父子顺序排列菜单列表
		return resultList;
	}

	/**
	 * 获得用户菜单列表，超级管理员可以查看所有菜单
	 *
	 * @param userId
	 *            用户ID
	 * @return 菜单列表
	 */
	private List<SysMenu> getMenuListByUserId(Long userId) {
		List<SysMenu> menuList;
		menuList = sysMenuMapper.findAllList();
		menuList = sysMenuMapper.findListByUserId(userId);
		return menuList;
	}

	/**
	 * 构建树形结构
	 *
	 * @param originals
	 *            原始数据
	 * @param useShow
	 *            是否使用开关控制菜单显示隐藏
	 * @return 菜单列表
	 */
	private List<SysMenu> makeTree(List<SysMenu> originals, boolean useShow) {

		// 构建一个Map,把所有原始数据的ID作为Key,原始数据对象作为VALUE
		Map<Long, SysMenu> dtoMap = new HashMap<>();
		for (SysMenu node : originals) {
			// 原始数据对象为Node，放入dtoMap中。
			Long id = node.getId();
			if (node.getShow() || !useShow) {
				dtoMap.put(id, node);
			}
		}

		List<SysMenu> result = new ArrayList<>();
		for (Map.Entry<Long, SysMenu> entry : dtoMap.entrySet()) {
			SysMenu node = entry.getValue();
			String parentId = node.getParentId();
			if (dtoMap.get(parentId) == null) {
				// 如果是顶层节点，直接添加到结果集合中
				result.add(node);
			} else {
				// 如果不是顶层节点，有父节点，然后添加到父节点的子节点中
				SysMenu parent = dtoMap.get(parentId);
				parent.addChild(node);
				parent.setLeaf(false);
			}
		}

		return result;
	}

	@Transactional(readOnly = false)
	public void deleteMenuById(String menuId) {
		sysMenuMapper.deleteById(menuId);
	}

	public SysMenu getMenuById(Long menuId) {
		return sysMenuMapper.get(menuId);
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
