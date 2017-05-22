package com.aek56.microservice.auth.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.aek56.microservice.auth.dao.CrudDao;
import com.aek56.microservice.auth.entity.SysRole;

/**
 * 角色DAO接口
 *
 * @author zj@aek56.com
 */
@Mapper
public interface SysRoleMapper extends CrudDao<SysRole> {

    /**
     * 查询用户角色列表
     */
    List<SysRole> findListByUser(@Param("userId")Long userId, @Param("tenantId")Long tenantId);

    Boolean isOrgAdmin(@Param("userId")Long userId, @Param("tenantId")Long tenantId);

	Boolean isSubOrg(@Param("parentTenantId")Long parentTenantId, @Param("subTenantId")Long subTenantId);
	
	List<Map<String,String>> getOrgModule(@Param("tenantId")Long tenantId);

	List<Map<String,String>> getOrgModuleByRole(@Param("userId")Long userId, @Param("tenantId")Long tenantId);
	
	List<Map<String,String>> getOrg(@Param("tenantId")Long tenantId, @Param("parentTenantIds")String parentTenantIds);
	
	List<Map<String,String>> getOrgByRole(@Param("userId")Long userId);

	Map<String, String> getTenant(@Param("tenantId")Long tenantId);
}
