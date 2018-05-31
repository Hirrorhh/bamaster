package com.bamaster.wf.controller;


import com.bamaster.core.utils.Constant;
import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.wf.dao.WfCurInstanceMapper;
import com.bamaster.wf.dao.WfCurNodeMapper;
import com.bamaster.wf.pojo.WfCurInstance;
import com.bamaster.wf.pojo.WfCurNode;
import com.bamaster.wf.pojo.WfTemplate;
import com.bamaster.wf.service.WfCurNodeService;
import com.bamaster.wf.service.WfInstanceService;
import com.bamaster.wf.service.WfTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.print.attribute.standard.RequestingUserName;
import javax.xml.transform.Templates;
import java.awt.*;
import java.util.Date;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 15:00
 *
 * @desc 模板管理相关的controller
 */

@Controller
@Scope(value = "prototype")
@RequestMapping("instance")
@Api(value = "实例(订单)管理控制器", description = "实例(订单)管理控制器")
public class WfInstanceController {

    @Resource
    WfInstanceService instanceService;

    @Resource
    WfTemplateService templateService;

    @Resource
    WfCurNodeService nodeService;

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
        return new ModelAndView(String.format("instance/%s", page));
    }


    /**
     *创建实例
     *
     * @param entity
     *
     * @return
     */
    @RequestMapping(value="addInstance",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "创建实例", notes = "创建实例", httpMethod = "POST")
    public Result subRegister(@RequestBody WfCurInstance entity) {
        LoggerUtils.debug(getClass(), "创建实例" + entity);
        try {
            if(null ==entity.getCreateuserid() || null == entity.getTemplateid()){

                return Result.error("创建人id和模板id不能为空");
            }
            WfCurInstance findInstance = instanceService.getInstanceByCidAndTid(entity);
            if (findInstance != null ){
                return Result.error("当前用户已经创建了本服务的实例");
            }
            entity.setStatus(Constant.ENABLE);
            entity.setCreatetime(new Date());

            boolean result = instanceService.createInstance(entity);

            if (result){
                return Result.ok();
            }
            return Result.error("添加失败!");
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "instance-addInstance出错", e);
            return Result.error();
        }
    }

    /**
     * 删除模板
     *
     * @return
     */
    @RequestMapping(value = "deleteInstance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "删除实例", notes = "删除实例", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceIds", value = "模板id", required = true, paramType = "query"),
    })
    public Result deleteInstanceByIds(String instanceIds) {
        LoggerUtils.debug(getClass(), "删除模板 id: " + instanceIds);

        try {

            instanceService.deleteInstanceByIds(instanceIds);

            return Result.ok();

        } catch (Exception e) {
            LoggerUtils.error(getClass(), "instance-deleteInstanceByIds出错", e);
            return Result.error();
        }

    }

    /**
     * 按需查找实例
     *
     * @return
     */
    @RequestMapping(value = "getInatanceByParameters", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取有效模板(查询任何类型的实例便只需要传入相对应的方法)", notes = "获取有效模板", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result getInatanceByParameters(@RequestBody WfCurInstance entity, Integer pageNo, Integer pageSize) {
        LoggerUtils.debug(getClass(), "获取所有有效模板信息,当前页: " + pageNo + "查询条数: " + pageSize);

        try {
            Page<WfCurInstance> instances = instanceService.getInatanceByParameters(entity , pageNo, pageSize);

            return Result.ok().put("instances" ,instances);

        } catch (Exception e) {
            LoggerUtils.error(getClass(), "instance-getInatanceByParameters出错", e);
            return Result.error();
        }

    }
    /**
     * 确定客户实例
     *
     * @return
     */
    @RequestMapping(value = "confirmCustomerInatance", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "管理员确定客户实例(订单)", notes = "管理员确定客户实例(订单)", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "instanceId", value = "实例id", required = true, paramType = "query"),
    })
    public Result getInatanceByParameters(Integer instanceId) {
        LoggerUtils.debug(getClass(), "确定Id:" + instanceId+"的客户实例");

        try {
            Integer templateid =  instanceService.getTemeplateId(instanceId);
            if (templateid == null){
                return Result.error("数据库中无此实例!");
            }
            //查询当前实例的完成步数
            Integer completeSize = templateService.getTemplateCompleteSizeByid(templateid);
            //判断之前是否存在节点
            WfCurNode findNode = nodeService.getNodeByInstanceId(instanceId);

            if (findNode != null){
                return Result.error("当前实例已经存在节点");
            }
            //实例被管理员确定之后,添加节点
            nodeService.addNodeAfterConfirm(instanceId,completeSize);
            return Result.ok();

        } catch (Exception e) {
            LoggerUtils.error(getClass(), "instance-confirmCustomerInatance出错", e);
            return Result.error();
        }

    }


}
