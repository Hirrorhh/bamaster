package com.bamaster.shiro.dao;



import com.bamaster.shiro.bo.RolePermissionAllocationBo;
import com.bamaster.shiro.pojo.URole;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface URoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(URole record);

    int insertSelective(URole record);

    URole selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(URole record);

    int updateByPrimaryKey(URole record);

	Set<String> findRoleByUserId(Integer id);

	List<URole> findNowAllPermission(Map<String, Object> map);

    List<URole> findAll();

    List<RolePermissionAllocationBo> findRoleAndPermission();
}