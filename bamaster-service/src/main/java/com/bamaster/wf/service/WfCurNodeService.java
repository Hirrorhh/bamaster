package com.bamaster.wf.service;/**
 * Created by Hirror on 2017/12/18.
 */

import com.bamaster.wf.pojo.WfCurNode;

import java.util.List;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 10:54
 *
 * @desc 当前节点service接口
 **/
public interface WfCurNodeService {
    WfCurNode getNodeByInstanceId(Integer instanceId);

    void addNodeAfterConfirm(Integer instanceId, Integer completeSize);

    void allocateNode(WfCurNode node);

    void updateStatus(WfCurNode node);

    WfCurNode getNodeByNodeid(Integer nodeid);

    List<WfCurNode> findAllValidNodesByOperateId(WfCurNode curNode);
}
