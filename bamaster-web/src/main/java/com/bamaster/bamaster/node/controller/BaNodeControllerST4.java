package com.bamaster.bamaster.node.controller;

import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.Result;
import com.bamaster.wf.controller.BaseNodeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 15:53
 *
 * @desc 备案大师节点2控制器
 **/
@Controller
@Scope(value = "prototype")
@RequestMapping("bamaster_st4")
@Api(value = "备案大师节点控制器ST4", description = "备案大师节点控制器ST4")
public class BaNodeControllerST4 extends BaseNodeController{


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
    public Result getAllNodesByUserId(Integer nodeid, Integer opreateid) {
        LoggerUtils.debug(getClass(), "为节点:"+nodeid+"分配操作人员: " + opreateid);
        Result result = super.getAllNodesByUserId(nodeid, opreateid);
        return result;
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
        Result result = super.updateNodeStatus(nodeid, status, nextnodeid);
        return result;
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

        Result result = super.noticeNextNodeOperator(nodeid, nextnodeid, message);
        return result;

    }

    @RequestMapping(value = "getAllFileList", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "查询当前节点id所有的文件", notes = "当前节点上客户上传的全部文件信息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nodeid", value = "节点id", required = true, paramType = "query")
    })
    public Result getAllFileList(Integer nodeid) {
        LoggerUtils.debug(getClass(), "查询节点:"+nodeid+"上客户上传的全部文件信息");
        Result result = super.getAllFileList(nodeid);
        return result;
    }
}
