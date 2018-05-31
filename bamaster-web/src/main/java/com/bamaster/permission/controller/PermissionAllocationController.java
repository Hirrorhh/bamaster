package com.bamaster.permission.controller;


import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.shiro.bo.RolePermissionAllocationBo;
import com.bamaster.shiro.bo.UPermissionBo;
import com.bamaster.shiro.service.PermissionService;
import com.bamaster.shiro.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-28 10:50
 *
 * @desc 用户权限分配的controller
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("permission")
@Api(value = "用户权限分配控制器", description = "用户权限分配控制器")
public class PermissionAllocationController {

    @Autowired
    PermissionService permissionService;
    @Autowired
    RoleService roleService;


    /**
     *
     * @param pageNo
     * @param pageSize
     * @author jf_hirror
     * @create 9:08--2017/11/28
     * @desc 分页查询所有权限
     */
    @ResponseBody
    @RequestMapping(value = "allocation")
    @ApiOperation(value = "查询所有权限并分页展示", notes = "查询所有权限并分页展示", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize",value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result allocation(Integer pageNo, Integer pageSize) {

        LoggerUtils.debug(getClass(),"分页查询所有权限,当前页: "+ pageNo+"查询条数: " +pageSize);
        try {
            Page<RolePermissionAllocationBo> roleAndPermissionList = roleService.findRoleAndPermissionPage(pageNo, pageSize);
            return Result.ok().put("roleAndPermissionList", roleAndPermissionList);
        } catch (Exception e) {
            e.printStackTrace();
            LoggerUtils.error(getClass(),"permission-allocation查询出错",e);
            return Result.error();
        }

    }

    /**
     * 根据角色ID查询权限
     * @param id
     * @return
     */
    @RequestMapping(value = "selectPermissionByRoleId")
    @ResponseBody
    @ApiOperation(value = "根据角色ID查询权限", notes = "查询所有权限并分页展示", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "当前需要查询权限的用户id", required = true, paramType = "query")
    })
    public Result selectPermissionById(Integer id) {
        LoggerUtils.debug(getClass(),"当前需要查询权限的用户id: "+ id);
        List<UPermissionBo> permissionBos = null;
        try {
            permissionBos = permissionService.selectPermissionByRoleId(id);
            return Result.ok().put("permissionBos",permissionBos);
        } catch (Exception e) {
            LoggerUtils.error(getClass(),"permission-selectPermissionById查询出错",e);
            return Result.error("查询id为" +id+ "的用户出错");
        }

    }

    /**
     * 操作角色的权限
     *
     * @param roleId 角色ID
     * @param ids    权限ID，以‘,’间隔
     * @return
     */
    @RequestMapping(value = "addPermission2Role")
    @ResponseBody
    @ApiOperation(value = "为角色分配权限", notes = "为角色分配权限",  httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleId",value = "当前需要分配权限的角色id", required = true,paramType = "query"),
            @ApiImplicitParam(name = "ids",value = "当前分配给角色的权限id", required = true,paramType = "query")
    })
    public Result addPermission2Role(Integer roleId, String ids) {
        LoggerUtils.debug(getClass(),"为角色分配权限,当前需要分配权限的角色id: "+ roleId+"分配给角色的权限id: " +ids);
        try {
            Integer count = permissionService.addPermission2Role(roleId, ids);
            return Result.ok().put("successcount",count);
        } catch (Exception e) {
            LoggerUtils.error(getClass(),"permission-addPermission2Role出错",e);
            return Result.error("为角色分配权限,当前需要分配权限的角色id: "+ roleId+"分配给角色的权限id: " +ids+"---出错!");
        }

    }

    /**
     * 根据角色id清空权限
     *
     * @param roleIds 角色ID ，以‘,’间隔
     * @return
     */
    @RequestMapping(value = "clearPermissionByRoleIds")
    @ResponseBody
    @ApiOperation(value = "清空角色的权限", notes = "清空角色的权限",httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleIds",value = "当前需要清空权限的角色id", required = true, paramType = "query")
    })
    public Map<String, Object> clearPermissionByRoleIds(String roleIds) {
        LoggerUtils.debug(getClass(),"当前需要清空权限的用户id: "+ roleIds);
        try {
            Integer count = permissionService.deleteByRids(roleIds);
            return Result.ok().put("clearcount",count);
        } catch (Exception e) {
            LoggerUtils.error(getClass(),"permission-addPermission2Role出错",e);
            return Result.error("清空角色:" +roleIds+ "的权限---出错!");
        }
    }
}
