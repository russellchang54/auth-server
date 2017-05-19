package com.aek56.microservice.auth.mapper;


import org.apache.ibatis.annotations.Mapper;

import com.aek56.microservice.auth.dao.CrudDao;
import com.aek56.microservice.auth.entity.SysUser;

/**
 * 用户DAO接口
 *
 * @author zj@aek56.com
 */
@Mapper
public interface SysUserMapper extends CrudDao<SysUser> {

    /**
     * 根据登录名称查询用户
     *
     * @param loginName 登录名
     * @return SysUser by login name
     */
    SysUser getByLoginName(String loginName);
}
