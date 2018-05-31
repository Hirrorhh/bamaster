package com.bamaster.shiro.dao;



import com.bamaster.shiro.bo.UPermissionBo;
import com.bamaster.shiro.pojo.UPermission;

import java.util.List;
import java.util.Set;

public interface UPermissionMapper {

    int deleteByPrimaryKey(Integer id);

    int insert(UPermission record);

    int insertSelective(UPermission record);

    UPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UPermission record);

    int updateByPrimaryKey(UPermission record);

	List<UPermissionBo> selectPermissionByRoleId(Integer id);
	//根据用户ID获取权限的Set集合
	Set<String> findPermissionByUserId(Integer id);

    List<UPermission> findAll();
}