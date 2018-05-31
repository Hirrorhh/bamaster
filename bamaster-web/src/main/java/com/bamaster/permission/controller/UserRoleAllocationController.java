package com.bamaster.permission.controller;


import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.shiro.bo.URoleBo;
import com.bamaster.shiro.bo.UserRoleAllocationBo;
import com.bamaster.shiro.service.PermissionService;
import com.bamaster.user.service.UUserService;
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

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-28 10:50
 *
 * @desc 用户角色分配
 */

@Controller
@Scope(value = "prototype")
@RequestMapping("role")
@Api(value = "用户角色分配控制器", description = "用户角色分配控制器")
public class UserRoleAllocationController  {

    @Autowired
    UUserService userService;
    @Autowired
    PermissionService permissionService;

    /**
     * 用户角色权限分配
     *
     * @param pageNo
     * @return
     */
    @RequestMapping(value = "allocation")
    @ApiOperation(value = "分页查询所有用户和权限", notes = "分页查询所有权限并分页展示", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result allocation(Integer pageNo, Integer pageSize) {
        LoggerUtils.debug(getClass(), "分页查询所有用户和权限,当前页: " + pageNo + "查询条数: " + pageSize);
        try {
            Page<UserRoleAllocationBo> userAndRoleList = userService.findUserAndRole(pageNo, pageSize);

            return Result.ok().put("userAndRoleList", userAndRoleList);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "role-allocation查询出错", e);
            return Result.error();
        }

    }

    /**
     * 根据用户ID查询角色
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "selectRoleByUserId")
    @ResponseBody
    @ApiOperation(value = "根据用户ID查询角色", notes = "根据用户ID查询角色", httpMethod = "GET")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "当前需要查询角色的用户id", required = true, paramType = "query")
    })
    public Result selectRoleByUserId(Integer id) {
        LoggerUtils.debug(getClass(), "当前需要查询角色的用户id: " + id);
        List<URoleBo> uRoleBos = null;
        try {
            uRoleBos = userService.selectRoleByUserId(id);
            return Result.ok().put("uRoleBos", uRoleBos);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "role-selectRoleByUserId查询出错", e);
            return Result.error();
        }

    }

    /**
     * 操作用户的角色
     *
     * @param userId 用户ID
     * @param ids    角色ID，以‘,’间隔
     * @return
     */
    @RequestMapping(value = "addRole2User")
    @ResponseBody
    @ApiOperation(value = "为用户分配角色", notes = "为用户分配角色", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "当前需要分配角色的用户id", required = true),
            @ApiImplicitParam(name = "ids", value = "当前分配给该用户的的角色ids(id按照','拼接)", required = true, paramType = "query")
    })
    public Result addRole2User(Integer userId, String ids) {
        LoggerUtils.debug(getClass(), "为用户" + userId + "分配角色" + ids);
        try {
            Integer count = userService.addRole2User(userId, ids);
            return Result.ok("成功分匹配角色输" + count);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "addRole2User", e);
            return Result.error();
        }

    }

    /**
     * 根据用户id清空角色。
     *
     * @param userIds 用户ID ，以‘,’间隔
     * @return
     */
    @RequestMapping(value = "clearRoleByUserIds")
    @ResponseBody
    @ApiOperation(value = "根据用户id清空角色", notes = "根据用户id清空角色", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userIds", value = "当前需要清空角色的用户ids(id按照','分割)", required = true),
    })
    public Result clearRoleByUserIds(String userIds) {
        LoggerUtils.debug(getClass(), "根据用户id清空角色" + userIds);
        try {
            Integer count = userService.deleteRoleByUserIds(userIds);
            return Result.ok("成功清空用户角色数" + count);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "role-clearRoleByUserIds出错", e);
            return Result.error();
        }

    }
}
