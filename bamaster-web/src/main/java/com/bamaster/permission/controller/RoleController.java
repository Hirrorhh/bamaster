package com.bamaster.permission.controller;


import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.core.utils.UserManagerUtils;
import com.bamaster.shiro.pojo.URole;
import com.bamaster.shiro.service.RoleService;
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
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


/**
 * @author jf_hirror
 * @version basic 1.0 2017-11-27
 * @create 2017-11-28 10:50
 * @desc 角色管理控制器
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("role")
@Api(value = "角色管理控制器", description = "角色管理控制器")
public class RoleController {

    @Autowired
    RoleService roleService;

    /**
     * 角色列表
     *
     * @return
     */
    @RequestMapping(value = "findAllRoles")
    @ResponseBody
    @ApiOperation(value = "查询所有的角色并分页展示", notes = "查询所有的角色并分页展示", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result findAllRoles(Model model, Integer pageNo, Integer pageSize) {
        LoggerUtils.debug(getClass(), "查询所有的角色,当前页: " + pageNo + "查询条数: " + pageSize);

        Page<URole> roles = null;
        try {
            roles = roleService.findAllRoles(pageNo, pageSize);
            return Result.ok().put("roles", roles);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "permission-findAllRoles查询出错", e);
            return Result.error();
        }
    }

    /**
     * 角色添加
     *
     * @param role
     * @return
     */
    @RequestMapping(value = "addRole", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "角色添加", notes = "角色添加", httpMethod = "POST")
    public Result addRole(URole role) {
        LoggerUtils.debug(getClass(), "添加角色: " );
        try {
            int count = roleService.insertSelective(role);
            return Result.ok().put("addCount", count);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "添加角色报错。source[%s]", role.toString());
            return Result.error("添加失败，请刷新后再试！");
        }

    }

    /**
     * 删除角色，根据ID，但是删除角色的时候，需要查询是否有赋予给用户，如果有用户在使用，那么就不能删除。
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "deleteRoleById", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除角色(根据Ids,如果有用户在使用，那么就不能删除)", notes = "删除角色", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "ids", value = "ids", required = true),
    })
    public Map<String, Object> deleteRoleById(String ids) {
        LoggerUtils.debug(getClass(), "删除角色(根据Ids,如果有用户在使用，那么就不能删除): " + ids);
        try {
            String result = roleService.deleteRoleById(ids);
            return Result.ok(result);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除角色出错。source[%s]");
            return Result.error("删除角色失败，请刷新后再试！");
        }
    }

    /**
     * 我的权限页面
     *
     * @return
     */
    @ApiOperation(value = "我的权限页面跳转", notes = "我的权限页面跳转", httpMethod = "POST")
    @RequestMapping(value = "mypermission", method = RequestMethod.POST)
    public ModelAndView mypermission() {
        return new ModelAndView("permission/mypermission");
    }

    /**
     * 我的权限 bootstrap tree data
     *
     * @return
     */
    @RequestMapping(value = "getPermissionTree", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询当前用户的权限(需先登录)", notes = "查询当前用户的权限(需先登录)", httpMethod = "POST")
    public Result getPermissionTree() {
        LoggerUtils.debug(getClass(), "查询当前用户的权限(需先登录) ");
        //查询我所有的角色 ---> 权限
        List<Map<String, Object>> data = null;
        try {
            List<URole> roles = roleService.findNowAllPermission();
            //把查询出来的roles 转换成bootstarp 的 tree数据
            data = UserManagerUtils.toTreeData(roles);
            return Result.ok().put("myPermissionData",data);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "删除角色出错。source[%s]");
            return Result.error();
        }
    }
}
