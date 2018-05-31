package com.bamaster.user.service.impl;


import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.shiro.bo.URoleBo;
import com.bamaster.shiro.bo.UserRoleAllocationBo;
import com.bamaster.shiro.dao.UUserMapper;
import com.bamaster.shiro.dao.UUserRoleMapper;
import com.bamaster.shiro.pojo.URole;
import com.bamaster.shiro.pojo.UUser;
import com.bamaster.shiro.pojo.UUserRole;
import com.bamaster.user.service.UUserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hirror on 2017/11/24.
 */
@Service
public class UUserServiceImpl implements UUserService {

    @Autowired
    UUserMapper userMapper;
    @Autowired
    UUserRoleMapper userRoleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UUser insert(UUser entity) {
        userMapper.insert(entity);
        return entity;
    }

    @Override
    public UUser insertSelective(UUser entity) {
        userMapper.insertSelective(entity);
        return entity;
    }

    @Override
    public UUser selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UUser entity) {
        return userMapper.updateByPrimaryKey(entity);
    }

    @Override
    public int updateByPrimaryKeySelective(UUser entity) {
        return userMapper.updateByPrimaryKeySelective(entity);
    }

    @Override
    public UUser login(String username ,String pswd) {
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("username", username);
        map.put("pswd", pswd);
        UUser user = userMapper.login(map);
        return user;
    }

    @Override
    public UUser findUserByUserName(String username) {
        return userMapper.findUserByUserName(username);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<UUser> findByPage(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<UUser> uUsers = userMapper.finAll();
        Page<UUser> pages = PageHelper.endPage();
        return pages;
    }

    @Override
    public Map<String, Object> deleteUserByIds(String ids) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            int count=0;
            String[] idArray = new String[]{};
            if(StringUtils.contains(ids, ",")){
                idArray = ids.split(",");
            }else{
                idArray = new String[]{ids};
            }

            for (String id : idArray) {
                count+=this.deleteByPrimaryKey(new Integer(id));
            }
            resultMap.put("status", 200);
            resultMap.put("count", count);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
            resultMap.put("status", 500);
            resultMap.put("message", "删除出现错误，请刷新后再试！");
        }
        return resultMap;
    }


    @Override
    public Map<String, Object> updateForbidUserById(Integer id, Integer status) {
        Map<String,Object> resultMap = new HashMap<String,Object>();
        try {
            UUser user = selectByPrimaryKey(id);
            user.setStatus(status);
            updateByPrimaryKeySelective(user);

            //如果当前用户在线，需要标记并且踢出

            resultMap.put("status", 200);
        } catch (Exception e) {
            resultMap.put("status", 500);
            resultMap.put("message", "操作失败，请刷新再试！");
            LoggerUtils.fmtError(getClass(), "禁止或者激活用户登录失败，id[%s],status[%s]", id,status);
        }
        return resultMap;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<UserRoleAllocationBo> findUserAndRole(Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<UserRoleAllocationBo> uUsers = userMapper.findUserAndRole();
        Page<UserRoleAllocationBo> pages = PageHelper.endPage();
        return pages;
    }

    @Override
    public List<URoleBo> selectRoleByUserId(Integer id) {
        return userMapper.selectRoleByUserId(id);
    }

    @Override
    public Integer addRole2User(Integer userId, String ids) {

        int count = 0;

            //先删除原有的。
            userRoleMapper.deleteByUserId(userId);
            //如果ids,role 的id 有值，那么就添加。没值象征着：把这个用户（userId）所有角色取消。
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = null;

                if(StringUtils.contains(ids, ",")){
                    idArray = ids.split(",");
                }else{
                    idArray = new String[]{ids};
                }
                //添加新的。
                for (String rid : idArray) {
                    //这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的}
                    if(StringUtils.isNotBlank(rid)){
                        UUserRole entity = new UUserRole(userId,new Integer(rid));
                        count += userRoleMapper.insertSelective(entity);
                    }
                }
            }


        //清空用户的权限，迫使再次获取权限的时候，得重新加载
        //无redis缓存 不能手动实现

        return count;
    }

    @Override
    public Integer deleteRoleByUserIds(String userIds) {

        int count = userRoleMapper.deleteRoleByUserIds(userIds);

        return count;

    }



}
