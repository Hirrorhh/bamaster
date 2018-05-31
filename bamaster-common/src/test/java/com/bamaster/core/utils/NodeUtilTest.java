package com.bamaster.core.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Hirror on 2017/12/19.
 */
public class NodeUtilTest {

    @org.junit.Test
    public void getNodeIdByInstanceIdAndOffset() throws Exception {
    }

    @org.junit.Test
    public void getInstanceIdByNodeIdAndOffset() throws Exception {
    }

    @org.junit.Test
    public void getAllNodeIdbyLastNodeId() throws Exception {
        System.out.println(NodeUtil.getAllNodeIdbyLastNodeId(23));
        System.out.println(NodeUtil.getInstanceIdbyLastNodeId(23));
    }
    @Test
    public void test() {
        System.out.println(new StringBuffer("15921179097").replace(3, 7, "XXXX"));
    }


}