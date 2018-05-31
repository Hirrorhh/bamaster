package com.bamaster.wf.controller;


import com.bamaster.common.controller.TokenManagerUtils;
import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.core.utils.UserManagerUtils;
import com.bamaster.shiro.pojo.UUser;
import com.bamaster.user.service.UUserService;
import com.bamaster.wf.pojo.WfTemplate;
import com.bamaster.wf.service.WfTemplateService;
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
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 16:00
 *
 * @desc 模板管理相关的controller
 */

@Controller
@Scope(value = "prototype")
@RequestMapping("template")
@Api(value = "模板管理控制器", description = "模板管理控制器")
public class WfTemplateController{

    @Resource
    WfTemplateService templateService;

    /**
     * 通用页面跳转
     *
     * @param page
     *
     * @return
     */
    @RequestMapping(value = "{page}", method = RequestMethod.GET)
    @ApiOperation(value = "用户相关信息通用页面跳转", notes = "用户相关信息通用页面跳转", httpMethod = "GET")
    public ModelAndView toPage(@PathVariable("page") String page) {
        return new ModelAndView(String.format("template/%s", page));
    }


    /**
     *
     * @param entity
     *
     * @return
     */
    @RequestMapping(value="addTemplate",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "添加模板", notes = "添加模板", httpMethod = "POST")
    public Result subRegister(@RequestBody WfTemplate entity) {
        LoggerUtils.debug(getClass(), "添加模板" + entity);
        try {
            boolean result = templateService.insert(entity);
            if (result){
                return Result.ok();
            }
            return Result.error("添加失败!");
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    /**
     * 删除模板
     *
     * @return
     */
    @RequestMapping(value = "deleteTemplate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除模板", notes = "删除模板", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "templateIds", value = "模板id", required = true, paramType = "query"),
    })
    public Result deleteTemplateByIds(String templateIds) {
        LoggerUtils.debug(getClass(), "删除模板 id: " + templateIds);

        try {
            templateService.deleteTemplateByIds(templateIds);
            return Result.ok();

        } catch (Exception e) {
            LoggerUtils.error(getClass(), "template-deleteTemplateByIds出错", e);
            return Result.error();
        }

    }

    /**
     * 获取所有有效模板
     *
     * @return
     */
    @RequestMapping(value = "getAllTemplate", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取有效模板", notes = "获取有效模板", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result getAllTemplateValid(Integer pageNo, Integer pageSize) {

        LoggerUtils.debug(getClass(), "获取所有有效模板信息,当前页: " + pageNo + "查询条数: " + pageSize);

        try {

            Page templateS = templateService.getAllTemplateValid(pageNo, pageSize);

            return Result.ok().put("templateS" ,templateS);

        } catch (Exception e) {
            LoggerUtils.error(getClass(), "template-getAllTemplate出错", e);
            return Result.error();
        }

    }


}
