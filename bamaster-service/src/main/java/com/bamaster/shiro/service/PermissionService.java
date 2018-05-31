package com.bamaster.shiro.service;



import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.shiro.bo.UPermissionBo;
import com.bamaster.shiro.pojo.UPermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface PermissionService {

	int deleteByPrimaryKey(Integer id);

	UPermission insert(UPermission record);

    UPermission insertSelective(UPermission record);

    UPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UPermission record);

    int updateByPrimaryKey(UPermission record);

	String deletePermissionById(String ids);

	Page<UPermission> findPage( Integer pageNo,Integer pageSize);

	List<UPermissionBo> selectPermissionByRoleId(Integer id);

	Integer addPermission2Role(Integer roleId, String ids);

	Integer deleteByRids(String roleIds);

	//根据用户ID查询权限（permission），放入到Authorization里。
	Set<String> findPermissionByUserId(Integer userId);
}
