package com.bamaster.shiro.service;



import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.shiro.bo.RolePermissionAllocationBo;
import com.bamaster.shiro.pojo.URole;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RoleService {

	int deleteByPrimaryKey(Integer id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);

	Page<URole> findAllRoles(Integer pageNo, Integer pageSize);

	String deleteRoleById(String ids);

	Page<RolePermissionAllocationBo> findRoleAndPermissionPage (Integer pageNo, Integer pageSize);
	//根据用户ID查询角色（role），放入到Authorization里。
	Set<String> findRoleByUserId(Integer userId);

	List<URole> findNowAllPermission();

}
