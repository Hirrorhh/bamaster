package com.bamaster.wf.controller;


import com.bamaster.core.utils.*;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.wf.dao.WfCurInstanceMapper;
import com.bamaster.wf.pojo.WfCurInstance;
import com.bamaster.wf.pojo.WfCurMessage;
import com.bamaster.wf.pojo.WfCurNode;
import com.bamaster.wf.service.WfCurMessageService;
import com.bamaster.wf.service.WfCurNodeService;
import com.bamaster.wf.service.WfInstanceService;
import com.bamaster.wf.service.WfTemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
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
@RequestMapping("usernode")
@Api(value = "用户相关信息查询控制器", description = "用户相关信息查询控制器")
public class UserNodeController {

    @Resource
    WfCurNodeService nodeService;
    @Resource
    WfInstanceService instanceService;
    @Resource
    WfTemplateService templateService;
    @Resource
    WfCurMessageService messageService;



    /**
     *
     * @param entity
     *
     * @return
     */
    @RequestMapping(value="getAllNodesByUserId",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户某个服务的所有节点", notes = "获取用户某个服务的所有节点", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true, paramType = "query"),
            @ApiImplicitParam(name = "temeplateId", value = "模板id", required = true, paramType = "query")
    })
    public Result getAllNodesByUserId(@RequestBody WfCurInstance entity) {
        LoggerUtils.debug(getClass(), "获取用户某个服务的所有节点" + entity);
        try {
            WfCurInstance findInstance = instanceService.getInstanceByCidAndTid(entity);
            if (findInstance == null ){
                return Result.error("当前用户未创建实例");
            }
            Integer instanceid = findInstance.getInstanceid();
            Integer completeSize = templateService.getTemplateCompleteSizeByid(findInstance.getTemplateid());
            HashMap<Integer, Object> nodes = new HashMap<>();

            for (Integer i = 0; i < completeSize; i++) {

                Integer nodeId = NodeUtil.getNodeIdByInstanceIdAndOffset(instanceid, i);
                WfCurNode findNode = nodeService.getNodeByNodeid(nodeId);
                nodes.put(i+1,findNode);
            }

            return Result.ok().put("nodes",nodes);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


    /**
     * @param pageNo
     * @param pageSize
     * @return
     */
    @RequestMapping(value="getMessageByUserId",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户的所有消息(用户登录之后不需要传递用户id)", notes = "获取用户的所有消息", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo", value = "跳转到的页数", required = true, paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页展示的记录数", required = true, paramType = "query")
    })
    public Result getMessageByUserId(Integer pageNo, Integer pageSize) {
        LoggerUtils.debug(getClass(), "获取用户的所有消息,当前页: " + pageNo + "查询条数: " + pageSize);
        try {
            Integer userId = TokenManagerUtils.getUserId();
            LoggerUtils.debug(getClass(),"获取userId为: "+userId+"的全部消息!");
            WfCurMessage wfCurMessage = new WfCurMessage();
            wfCurMessage.setUserid(userId);
            Page messages = messageService.findMessageByUserId(wfCurMessage, pageNo, pageSize);

            return Result.ok().put("messages",messages);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }

    @RequestMapping(value="getAllWfByOperateId",method= RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value = "获取用户所有未完成的已分配节点和所属实例", notes = "获取用户所有未完成的已分配节点和所属实例", httpMethod = "POST")
    public Result getAllWfByOperateId() {
        LoggerUtils.debug(getClass(), "获取用户所有未完成的已分配节点和所属实例");
        try {
            Integer userId = TokenManagerUtils.getUserId();
            LoggerUtils.debug(getClass(),"获取userId为: "+userId+"的所有未完成的已分配节点和所属实例!");
            WfCurNode curNode = new WfCurNode();
            curNode.setStatus(Constant.ING);
            curNode.setOpreateid(userId);

            List<WfCurNode> nodes = nodeService.findAllValidNodesByOperateId(curNode);
            if (nodes !=null && nodes.isEmpty()){
                return Result.error("当前用户未被分配任何节点");
            }
            //遍历节点得到实例 hp去重复
            HashMap<Integer,WfCurInstance> instances = new HashMap<>();
            for (WfCurNode node : nodes) {
                instances.put(NodeUtil.getInstanceIdbyLastNodeId(node.getNodeid()),
                        instanceService.getInstanceByInstanceId(NodeUtil.getInstanceIdbyLastNodeId(node.getNodeid())));
            }
            return Result.ok().put("nodes",nodes).put("instances",instances);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.error();
        }
    }


}
