package com.bamaster.wf.controller;


import com.bamaster.core.utils.Constant;
import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.core.utils.Result;
import com.bamaster.core.utils.TokenManagerUtils;
import com.bamaster.wf.pojo.*;
import com.bamaster.wf.service.*;
import com.sun.xml.internal.bind.v2.schemagen.xmlschema.TypeHost;
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
import javax.persistence.Id;
import java.util.Date;
import java.util.List;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 13:08
 *
 * @desc 节点管理相关的controller
 */
@Controller
@Scope(value = "prototype")
@RequestMapping("bamaster_st1")
@Api(value = "备案大师节点控制器ST1", description = "备案大师节点控制器ST1")
public class BaseNodeController {

    @Resource
    WfCurNodeService nodeService;
    @Resource
    WfCurMessageService messageService;
    @Resource
    CusResourceService resourceService;

    /**
     *
     * @param nodeid
     *
     * @param opreateid
     *
     * @return
     */
    @RequestMapping(value = "allocateNode", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "为节点分配操作人员", notes = "为节点分配操作人员", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeid", value = "节点id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "opreateid", value = "操作人员id", required = true, paramType = "query")
    })
    public Result getAllNodesByUserId(Integer nodeid,Integer opreateid) {
        LoggerUtils.debug(getClass(), "为节点:"+nodeid+"分配操作人员: " + opreateid);
        try {
            if (nodeid == null || opreateid == null){
                return Result.error("节点id或所要分配的操作人员不能为空");
            }
            WfCurNode node = new WfCurNode();
            node.setNodeid(nodeid);
            node.setOpreateid(opreateid);
            nodeService.allocateNode(node);
            return Result.ok();
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "bamaster_st1-getAllNodesByUserId出错", e);
            return Result.error();
        }
    }

    @RequestMapping(value = "updateStatus", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "设置节点状态", notes = "设置节点状态", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeid", value = "节点id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "status", value = "节点状态", required = true, paramType = "query"),
            @ApiImplicitParam(name = "nextnodeid", value = "下一节点id", required = true, paramType = "query"),
    })
    public Result updateNodeStatus(Integer nodeid,Integer status ,Integer nextnodeid) {
        LoggerUtils.debug(getClass(), "为节点:"+nodeid+"设置节点状态: " + status);
        try {
            if (nodeid == null || status == null){
                return Result.error("节点id或设置节点状态不能为空");
            }

            WfCurNode node = new WfCurNode();
            node.setNodeid(nodeid);
            node.setStatus(status);
            node.setExamineid(TokenManagerUtils.getUserId());

            nodeService.updateStatus(node);

            return Result.ok();
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "bamaster_st1-updateNodeStatus出错", e);
            return Result.error();
        }
    }

    /**
     * 通知下一节点准备
     *
     * @return
     */
    @RequestMapping(value = "noticeNextNodeOperator", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "通知下一节点的操作人员", notes = "通知下一节点的操作人员", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeid", value = "节点id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "nextnodeid", value = "下一节点id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "message", value = "通知信息", required = true, paramType = "query"),
    })
    public Result noticeNextNodeOperator(Integer nodeid,Integer nextnodeid ,String message) {

        LoggerUtils.debug(getClass(), "当前节点为: "+nodeid+"下一节点为: "+nextnodeid);

        try {

            WfCurNode node = nodeService.getNodeByNodeid(nodeid);

            if (!node.getStatus().equals(Constant.COMPLETED)){
                return Result.error("当前节点未完成,不能通知下一节点操作人员!");
            }
            WfCurNode nextNode = nodeService.getNodeByNodeid(nextnodeid);
            WfCurMessage notice = new WfCurMessage();
            notice.setUserid(nextNode.getOpreateid());
            notice.setCreatetime(new Date());
            notice.setNodeid(nextnodeid);

            if(StringUtils.isNotBlank(message)){
                notice.setMsg(message);
            }else{
                notice.setMsg("上一节点已经完成,你可以准备当前工作了!");
            }
            messageService.insertMessage(notice);
            return Result.ok();

        } catch (Exception e) {
            LoggerUtils.error(getClass(), "bamaster_st1-noticeNextNodeOperator出错", e);
            return Result.error();
        }

    }

    @RequestMapping(value = "getAllFileList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询当前节点id所有的文件", notes = "当前节点上客户上传的全部文件信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeid", value = "节点id", required = true, paramType = "query")
    })
    public Result getAllFileList(Integer nodeid) {
        LoggerUtils.debug(getClass(), "查询节点:"+nodeid+"上客户上传的全部文件信息");
        try {
            if (nodeid == null){
                return Result.error("节点id不能为空");
            }

          List<CusResource> resourceList =  resourceService.getAllFileListByNodeid(nodeid);

            return Result.ok().put("resources",resourceList);
        } catch (Exception e) {
            LoggerUtils.error(getClass(), "bamaster_st1-getAllFileList出错", e);
            return Result.error();
        }
    }

}
