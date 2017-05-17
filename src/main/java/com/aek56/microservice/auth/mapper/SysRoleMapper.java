package com.aek56.microservice.auth.mapper;


import com.aek56.microservice.auth.dao.CrudDao;
import com.aek56.microservice.auth.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 角色DAO接口
 *
 * @author zj@aek56.com
 */
@Mapper
public interface SysRoleMapper extends CrudDao<SysRole> {

    /**
     * 查询用户角色列表
     *
     * @param userId the user id
     * @return the list
     */
    List<SysRole> findListByUserId(String userId);

    /**
     * 删除角色菜单
     *
     * @param role the role
     * @return the int
     */
    int deleteRoleMenu(SysRole role);

    /**
     * 插入角色菜单
     *
     * @param role the role
     * @return the int
     */
    int insertRoleMenu(SysRole role);
}
