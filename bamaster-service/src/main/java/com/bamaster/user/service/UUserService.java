package com.bamaster.user.service;



import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.shiro.bo.URoleBo;
import com.bamaster.shiro.bo.UserRoleAllocationBo;
import com.bamaster.shiro.pojo.UUser;
import com.github.abel533.mapper.Mapper;

import java.util.List;
import java.util.Map;

public interface UUserService{

	int deleteByPrimaryKey(Integer id);

	UUser insert(UUser user);

    UUser insertSelective(UUser user);

    UUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UUser record);

    int updateByPrimaryKey(UUser record);
    
    UUser login(String username, String pswd);

	UUser findUserByUserName(String username);

	Page<UUser> findByPage(Integer pageNo, Integer pageSize);

	Map<String, Object> deleteUserByIds(String ids);

	Map<String, Object> updateForbidUserById(Integer id, Integer status);

	Page<UserRoleAllocationBo> findUserAndRole(Integer pageNo, Integer pageSize);

	List<URoleBo> selectRoleByUserId(Integer id);

	Integer addRole2User(Integer userId, String ids);

	Integer deleteRoleByUserIds(String userIds);
}
