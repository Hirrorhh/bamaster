package com.bamaster.wf.service.impl;/**
 * Created by Hirror on 2017/12/18.
 */

import com.bamaster.core.utils.Constant;
import com.bamaster.core.utils.NodeUtil;
import com.bamaster.wf.dao.WfCurInstanceMapper;
import com.bamaster.wf.dao.WfCurNodeMapper;
import com.bamaster.wf.dao.WfHistInstanceMapper;
import com.bamaster.wf.dao.WfHistNodeMapper;
import com.bamaster.wf.pojo.WfCurInstance;
import com.bamaster.wf.pojo.WfCurNode;
import com.bamaster.wf.pojo.WfHistInstance;
import com.bamaster.wf.pojo.WfHistNode;
import com.bamaster.wf.service.WfCurNodeService;
import com.github.abel533.entity.Example;
import com.github.abel533.entity.Example.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.jnlp.IntegrationService;
import javax.rmi.CORBA.Util;
import java.nio.file.NotDirectoryException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 10:54
 *
 * @desc 当前节点service实现
 **/
@Service
@Transactional
public class WfCurNodeServiceImpl implements WfCurNodeService {


    @Autowired
    WfCurNodeMapper nodeMapper;
    @Autowired
    WfHistNodeMapper histNodeMapper;
    @Autowired
    WfCurInstanceMapper instanMapper;
    @Autowired
    WfHistInstanceMapper histInstanceMapper;

    @Override
    public WfCurNode getNodeByInstanceId(Integer instanceId) {
        WfCurNode node = new WfCurNode();
        node.setNodeid(NodeUtil.getNodeIdByInstanceIdAndOffset(instanceId,0));
        WfCurNode wfCurNode = nodeMapper.selectByPrimaryKey(node);

        return wfCurNode== null? null: wfCurNode;
    }

    @Override
    public void addNodeAfterConfirm(Integer instanceId, Integer completeSize) {
        for (Integer i = 0; i < completeSize - 1; i++) {
            WfCurNode wfCurNode = new WfCurNode();
            wfCurNode.setNodeid(NodeUtil.getNodeIdByInstanceIdAndOffset(instanceId,i));
            wfCurNode.setNextnodeid(NodeUtil.getNodeIdByInstanceIdAndOffset(instanceId,i+1));
            wfCurNode.setCreatetime(new Date());
            wfCurNode.setStatus(Constant.ENABLE);
            nodeMapper.insertSelective(wfCurNode);
        }
        WfCurNode wfCurNode = new WfCurNode();
        wfCurNode.setNodeid(NodeUtil.getNodeIdByInstanceIdAndOffset(instanceId,completeSize-1));
        wfCurNode.setNextnodeid(Constant.NODE_END_ST);
        wfCurNode.setCreatetime(new Date());
        wfCurNode.setStatus(Constant.ENABLE);
        nodeMapper.insertSelective(wfCurNode);
    }

    @Override
    public void allocateNode(WfCurNode node) {
        nodeMapper.updateByPrimaryKeySelective(node);
    }

    @Override
    public void updateStatus(WfCurNode node) {
        nodeMapper.updateByPrimaryKeySelective(node);
        Integer nodeid = node.getNodeid();
        WfCurNode curNode = nodeMapper.selectByPrimaryKey(nodeid);
        if (curNode.getNextnodeid().equals(Constant.NODE_END_ST)){
            String ids = NodeUtil.getAllNodeIdbyLastNodeId(nodeid);
            String[] id_arr = ids.split(",");


            Example example = new Example(WfCurNode.class);
            Criteria criteria = example.createCriteria();
            Criteria criteria1 = criteria.andIn("nodeid",Arrays.asList(id_arr));
            List<WfCurNode> wfCurNodes = nodeMapper.selectByExample(example);
            //历史节点表中插入数据
            for (WfCurNode wfCurNode : wfCurNodes) {
                WfHistNode wfHistNode = new WfHistNode();
                wfHistNode.setNodeid(wfCurNode.getNodeid());
                wfHistNode.setCreatetime(wfCurNode.getCreatetime());
                wfHistNode.setExamineid(wfCurNode.getExamineid());
                wfHistNode.setOpreateid(wfCurNode.getOpreateid());
                wfHistNode.setNextnodeid(wfCurNode.getNextnodeid());
                histNodeMapper.insertSelective(wfHistNode);
            }
            //当前表中删除数据
            nodeMapper.deleteByExample(example);
            //查询出当前的实例
            WfCurInstance wfCurInstance = instanMapper.selectByPrimaryKey(NodeUtil.getInstanceIdbyLastNodeId(nodeid));
            WfHistInstance wfHistInstance = new WfHistInstance();
            wfHistInstance.setInstanceid(wfCurInstance.getInstanceid());
            wfHistInstance.setCreatetime(wfCurInstance.getCreatetime());
            wfHistInstance.setCreateuserid(wfCurInstance.getCreateuserid());
            wfHistInstance.setExpiretime(wfCurInstance.getExpiretime());
            //将当前实例插入历史实例表中
            histInstanceMapper.insertSelective(wfHistInstance);
            //将当前实例表中数据删除(物理删除)
            instanMapper.deleteByPrimaryKey(NodeUtil.getInstanceIdbyLastNodeId(nodeid));
        }

    }

    @Override
    public WfCurNode getNodeByNodeid(Integer nodeid) {
        return nodeMapper.selectByPrimaryKey(nodeid);
    }

    @Override
    public List<WfCurNode> findAllValidNodesByOperateId(WfCurNode curNode) {
        return nodeMapper.select(curNode);
    }
}
