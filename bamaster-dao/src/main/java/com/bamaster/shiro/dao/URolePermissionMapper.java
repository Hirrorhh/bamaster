package com.bamaster.shiro.dao;



import com.bamaster.shiro.pojo.URolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface URolePermissionMapper {
    int insert(URolePermission record);

    int insertSelective(URolePermission record);

	List<URolePermission> findRolePermissionByPid(Integer id);
	
	List<URolePermission> findRolePermissionByRid(Integer id);
	
	List<URolePermission> find(URolePermission entity);
	
	int deleteByPid(Integer id);
	int deleteByRid(Integer id);
	int delete(URolePermission entity);

	int deleteByRids(@Param("roleIds")String roleIds);
}