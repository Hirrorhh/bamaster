package com.bamaster.wf.service;

import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.wf.pojo.WfCurInstance;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 16:53
 *
 * @desc
 **/
public interface WfInstanceService {
    boolean createInstance(WfCurInstance entity);

    void deleteInstanceByIds(String instanceIds);

    Page<WfCurInstance> getInatanceByParameters(WfCurInstance entity, Integer pageNo, Integer pageSize);

    Integer getTemeplateId(Integer instanceId);

    WfCurInstance getInstanceByCidAndTid(WfCurInstance entity);

    WfCurInstance getInstanceByInstanceId(Integer instanceId);
}
