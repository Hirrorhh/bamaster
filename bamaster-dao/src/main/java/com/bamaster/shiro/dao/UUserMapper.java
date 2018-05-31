package com.bamaster.shiro.dao;



import com.bamaster.shiro.bo.URoleBo;
import com.bamaster.shiro.bo.UserRoleAllocationBo;
import com.bamaster.shiro.pojo.UUser;

import java.util.List;
import java.util.Map;

public interface UUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UUser record);

    int insertSelective(UUser record);

    UUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);

	UUser login(Map<String, Object> map);

	UUser findUserByUserName(String username);

	List<URoleBo> selectRoleByUserId(Integer id);

    List<UUser> finAll();

    List<UserRoleAllocationBo> findUserAndRole();
}