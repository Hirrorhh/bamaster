package com.bamaster.shiro.service.impl;


import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.shiro.bo.RolePermissionAllocationBo;
import com.bamaster.shiro.dao.URoleMapper;
import com.bamaster.shiro.dao.URolePermissionMapper;
import com.bamaster.shiro.dao.UUserMapper;
import com.bamaster.shiro.pojo.URole;
import com.bamaster.shiro.pojo.UUser;
import com.bamaster.shiro.service.RoleService;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Hirror on 2017/11/24.
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    URoleMapper roleMapper;
    @Autowired
    UUserMapper userMapper;
    @Autowired
    URolePermissionMapper rolePermissionMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(URole record) {
        return roleMapper.insert(record);
    }

    @Override
    public int insertSelective(URole record) {
        return roleMapper.insertSelective(record);
    }

    @Override
    public URole selectByPrimaryKey(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(URole record) {
        return roleMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(URole record) {
        return roleMapper.updateByPrimaryKeySelective(record);
    }


    @Override
    public Page<URole> findAllRoles(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<URole> uRoles = roleMapper.findAll();
        Page<URole> pages = PageHelper.endPage();
        return pages;
    }

    @Override
    public Page<RolePermissionAllocationBo> findRoleAndPermissionPage(
            Integer pageNo, Integer pageSize) {

        PageHelper.startPage(pageNo, pageSize);
        List<RolePermissionAllocationBo> uRoles = roleMapper.findRoleAndPermission();
        Page<RolePermissionAllocationBo> pages = PageHelper.endPage();
        return pages;

    }

    @Override
    public String deleteRoleById(String ids) {


            int count = 0;
            String resultMsg = "删除成功。";
            String[] idArray = new String[]{};
            if (StringUtils.contains(ids, ",")) {
                idArray = ids.split(",");
            } else {
                idArray = new String[]{ids};
            }

            c:
            for (String idx : idArray) {
                Integer id = new Integer(idx);
                if (new Integer(1).equals(id)) {
                    resultMsg = "操作成功，But'系统管理员不能删除。";
                    continue c;
                } else {
                    count += this.deleteByPrimaryKey(id);
                }
            }
        return resultMsg;
    }

    @Override
    public Set<String> findRoleByUserId(Integer userId) {
        return roleMapper.findRoleByUserId(userId);
    }

    @Override
    public List<URole> findNowAllPermission() {
        Map<String, Object> map = new HashMap<String, Object>();
        UUser token = (UUser) SecurityUtils.getSubject().getPrincipal();
        map.put("userId", token == null ? null : token.getId());
        return roleMapper.findNowAllPermission(map);
    }

}

