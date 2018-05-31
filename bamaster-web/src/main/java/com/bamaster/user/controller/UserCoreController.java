package com.bamaster.user.controller;


import com.bamaster.common.controller.TokenManagerUtils;
import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.Result;
import com.bamaster.core.utils.UserManagerUtils;
import com.bamaster.shiro.pojo.UUser;
import com.bamaster.user.service.UUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-28 10:50
 *
 * @desc 用户管理相关的controller
 */

@Controller
@Scope(value = "prototype")
@RequestMapping("user")
@Api(value = "用户相关信息管理控制器", description = "用户相关信息管理控制器")
public class UserCoreController{

    @Resource
    UUserService userService;

    /**
     * 偷懒一下，通用页面跳转
     *
     * @param page
     * @return
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    @ApiOperation(value = "用户相关信息通用页面跳转", notes = "用户相关信息通用页面跳转", httpMethod = "GET")
    public ModelAndView toPage(@PathVariable("page") String page) {
        return new ModelAndView(String.format("user/%s", page));
    }


    /**
     * 个人资料
     *
     * @return
     */
    @RequestMapping(value = "userInfo", method = RequestMethod.GET)
    @ApiOperation(value = "用户个人资料页面跳转", notes = "用户个人资料页面跳转", httpMethod = "GET")
    public ModelAndView userIndex() {

        return new ModelAndView("user/userInfo");
    }

    /**
     * 密码修改
     *
     * @return
     */
    @RequestMapping(value = "updatePswd", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户个人密码修改", notes = "用户个人密码修改", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pswd", value = "老密码", required = true, paramType = "query"),
            @ApiImplicitParam(name = "newPswd", value = "新密码", required = true, paramType = "query")
    })
    public Result updatePswd( String pswd,  String newPswd) {
        LoggerUtils.debug(getClass(), "用户个人密码修改更改旧密码" + pswd + "为新密码" + newPswd);
        //根据当前登录的用户帐号 + 老密码，查询。
        try {
            String username = TokenManagerUtils.getToken().getUsername();
            pswd = UserManagerUtils.md5Pswd(username, pswd);
            UUser user = userService.login(username, pswd);

            if ("admin".equals(username)) {
                return Result.error("300", "管理员不准修改密码");
            }

            if (null == user) {
                return Result.error("300", "密码不正确");
            } else {
                user.setPswd(newPswd);
                //加工密码
                user = UserManagerUtils.md5Pswd(user);
                //修改密码
                userService.updateByPrimaryKeySelective(user);
                //重新登录一次
                TokenManagerUtils.login(user, Boolean.TRUE);
                return Result.ok();
            }
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "user-updatePswd出错", e);
            return Result.error();
        }

    }

    /**
     * 个人资料修改
     *
     * @return
     */
    @RequestMapping(value = "updateUserInfo", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "用户个人资料修改", notes = "用户个人资料修改", httpMethod = "POST")
    public Result updateUserInfo(@RequestParam UUser entity) {
        LoggerUtils.debug(getClass(), "用户个人更改信息为" + entity.toString());
        try {
            userService.updateByPrimaryKeySelective(entity);
            return Result.ok();
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "修改个人资料出错。[%s]", JSONObject.fromObject(entity).toString());
            return Result.error();
        }

    }
}
