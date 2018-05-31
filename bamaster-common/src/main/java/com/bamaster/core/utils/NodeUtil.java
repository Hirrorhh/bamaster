package com.bamaster.core.utils;


import com.sun.org.apache.xpath.internal.operations.Mod;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 11:01
 *
 * @desc 节点的相关操作工具类
 **/
public class NodeUtil {

    public static Integer getNodeIdByInstanceIdAndOffset(Integer instanceId, Integer offsetSize) {

        return instanceId * 20 + offsetSize;

    }

    public static Integer getInstanceIdByNodeIdAndOffset(Integer nodeId, Integer offsetSize) {

        return (nodeId - offsetSize) / 20;

    }
    public static String getAllNodeIdbyLastNodeId(Integer nodeId) {

       String allNodeId=nodeId+"";
        int i ;
        for (i = nodeId-1; (i%20)!=0; i--) {
                allNodeId=allNodeId+","+i;
        }

        return allNodeId+","+i;

    }
    public static Integer getInstanceIdbyLastNodeId(Integer nodeId) {


        return nodeId/20;

    }
}
