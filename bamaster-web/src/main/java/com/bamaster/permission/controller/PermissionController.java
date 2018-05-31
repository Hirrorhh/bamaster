package com.bamaster.permission.controller;


import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.shiro.pojo.UPermission;
import com.bamaster.shiro.service.PermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-28 10:50
 *
 * @desc 权限管理控制器
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("permission")
@Api(value = "权限管理控制器", description = "权限管理控制器")
public class PermissionController{

    @Autowired
    PermissionService permissionService;

    /**
     * @param pageNo   跳转到的页数
     * @param pageSize 每页展示的记录数
     * @return
     */
    @RequestMapping(value = "findAllPermissions")
    @ResponseBody
    @ApiOperation(value = "查询所有的单个权限并分页展示", notes = "查询所有的单个权限并分页展示", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result findAllPermissions(Integer pageNo, Integer pageSize) {
        LoggerUtils.debug(getClass(), "查询所有的单个权限并分页展示,当前页: " + pageNo + "查询条数: " + pageSize);
        Page<UPermission> permissions = null;
        try {
            permissions = permissionService.findPage(pageNo, pageSize);
            return Result.ok().put("permissions", permissions);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "permission-findAllPermissions查询出错", e);
            return Result.error();
        }
    }

    /**
     * 权限添加
     *
     * @param psermission
     * @return
     */
    @ApiOperation(value = "权限添加", notes = "权限添加", httpMethod = "POST")
    @RequestMapping(value = "addPermission", method = RequestMethod.POST)
    @ResponseBody
    public Result addPermission(Model model, UPermission psermission) {
        LoggerUtils.debug(getClass(), "权限添加");
        try {
            UPermission entity = permissionService.insertSelective(psermission);
            return Result.ok().put("entity", entity);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "添加权限报错。source[%s]", psermission.toString());
            return Result.error("权限添加出错!");
        }

    }

    /**
     * 删除权限，根据ID，但是删除权限的时候，需要查询是否有赋予给角色，如果有角色在使用，那么就不能删除。
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deletePermissionById", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "根据ids删除权限(如果有角色在使用，那么就不能删除)", notes = "根据ids删除权限", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", required = true,paramType = "query"),
    })
    public Result deleteRoleById(String ids) {
        LoggerUtils.debug(getClass(), "根据ids删除权限(如果有角色在使用，那么就不能删除),删除的ids为: " + ids);
        try {
            String message = permissionService.deletePermissionById(ids);
            return Result.ok(message + "---有角色在使用则不能删除");
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除权限报错!");
            return Result.error("删除权限id为" + ids + "权限报错!");
        }
    }
}
