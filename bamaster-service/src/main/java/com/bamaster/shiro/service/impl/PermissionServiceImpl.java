package com.bamaster.shiro.service.impl;


import com.bamaster.core.utils.Constant;
import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.shiro.bo.UPermissionBo;
import com.bamaster.shiro.dao.UPermissionMapper;
import com.bamaster.shiro.dao.URolePermissionMapper;
import com.bamaster.shiro.dao.UUserMapper;
import com.bamaster.shiro.dao.UUserRoleMapper;
import com.bamaster.shiro.pojo.UPermission;
import com.bamaster.shiro.pojo.URolePermission;
import com.bamaster.shiro.service.PermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:45
 *
 * @desc 权限操作服务实现类
 */

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    UPermissionMapper permissionMapper;
    @Autowired
    UUserMapper userMapper;
    @Autowired
    URolePermissionMapper rolePermissionMapper;
    @Autowired
    UUserRoleMapper userRoleMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public UPermission insert(UPermission record) {
        permissionMapper.insert(record);
        return record;
    }

    @Override
    public UPermission insertSelective(UPermission record) {
        //添加权限
        permissionMapper.insertSelective(record);
        //每添加一个权限，都往最底层的管理员里添加一次。保证系统管理员有最大的权限
        executePermission(new Integer(1), String.valueOf(record.getId()));
        return record;
    }

    @Override
    public UPermission selectByPrimaryKey(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKey(UPermission record) {
        return permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    public int updateByPrimaryKeySelective(UPermission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public String deletePermissionById(String ids) {


            int successCount=0,errorCount=0;
            String resultMsg ="删除%s条，失败%s条";
            String[] idArray = new String[]{};
            if(StringUtils.contains(ids, ",")){
                idArray = ids.split(",");
            }else{
                idArray = new String[]{ids};
            }

            for (String idx : idArray) {
                Integer id = new Integer(idx);

                List<URolePermission> rolePermissions= rolePermissionMapper.findRolePermissionByPid(id);
                if(null != rolePermissions && rolePermissions.size() > 0){
                    errorCount += rolePermissions.size();
                }else{
                    successCount+=this.deleteByPrimaryKey(id);
                }
            }

            //如果有成功的，也有失败的，提示清楚。
            if(errorCount > 0){
                resultMsg = String.format(resultMsg, successCount,errorCount);
            }else{
                resultMsg = "操作成功";
            }
            return resultMsg;


    }

    @SuppressWarnings("unchecked")
    @Override
    public Page<UPermission> findPage( Integer pageNo,Integer pageSize) {
        PageHelper.startPage(pageNo, pageSize);
        List<UPermission> permissions = permissionMapper.findAll();
        Page<UPermission> pages = PageHelper.endPage();
        return pages;
    }

    @Override
    public List<UPermissionBo> selectPermissionByRoleId(Integer id) {
        return permissionMapper.selectPermissionByRoleId(id);
    }

    @Override
    public Integer addPermission2Role(Integer roleId, String ids) {
        //先删除原有的。
        rolePermissionMapper.deleteByRid(roleId);
        return executePermission(roleId, ids);
    }
    /**
     * 处理权限
     * @param roleId
     * @param ids
     * @return
     */
    private Integer executePermission(Integer roleId, String ids){

        int count = 0;

            //如果ids,permission 的id 有值，那么就添加。没值象征着：把这个角色（roleId）所有权限取消。
            if(StringUtils.isNotBlank(ids)){
                String[] idArray = null;

                if(StringUtils.contains(ids, ",")){
                    idArray = ids.split(",");
                }else{
                    idArray = new String[]{ids};
                }
                //添加新的。
                for (String pid : idArray) {
                    //这里严谨点可以判断，也可以不判断。这个{@link StringUtils 我是重写了的}
                    if(StringUtils.isNotBlank(pid)){
                        URolePermission entity = new URolePermission(roleId,new Integer(pid));
                        count += rolePermissionMapper.insertSelective(entity);
                    }
                }
            }
        //清空拥有角色Id为：roleId 的用户权限已加载数据，让权限数据重新加载
        //List<Integer> userIds = userRoleMapper.findUserIdByRoleId(roleId);
        return count;

    }

    @Override
    public Integer deleteByRids(String roleIds) {

        int count = rolePermissionMapper.deleteByRids(roleIds);

        return count;
    }

    @Override
    public Set<String> findPermissionByUserId(Integer userId) {
        return permissionMapper.findPermissionByUserId(userId);
    }


}
