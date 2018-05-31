package com.bamaster.shiro.dao;



import com.bamaster.shiro.pojo.UUserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UUserRoleMapper {
    int insert(UUserRole record);

    int insertSelective(UUserRole record);

	int deleteByUserId(Integer id);

	int deleteRoleByUserIds(@Param("userIds")String userIds);

	List<Integer> findUserIdByRoleId(Integer id);
}