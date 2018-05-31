package com.bamaster.wf.service.impl;

import com.bamaster.core.utils.Constant;
import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.wf.dao.WfCurInstanceMapper;
import com.bamaster.wf.dao.WfCurNodeMapper;
import com.bamaster.wf.pojo.WfCurInstance;
import com.bamaster.wf.service.WfInstanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jf_hirror
 * @version basic 1.0 2017-12-15
 * @create 2017-12-15 16:53
 * @desc
 **/
@Service
public class WfInstanceServiceImpl implements WfInstanceService {

    @Autowired
    private WfCurInstanceMapper instanceMapper;

    @Autowired
    private WfCurNodeMapper curNodeMapper;


    @Override
    public boolean createInstance(WfCurInstance entity) {
        int instanceId = instanceMapper.insert(entity);

        return instanceId > 0 ? true : false;
    }

    @Override
    public void deleteInstanceByIds(String instanceIds) {

        String[] ids = instanceIds.split(",");
        if(ids != null ){
            for (String id : ids) {
                WfCurInstance instance = new WfCurInstance();
                instance.setInstanceid(Integer.parseInt(id));
                instance.setStatus(Constant.DISABLE);
                instanceMapper.updateByPrimaryKeySelective(instance);

            }
        }

    }

    @Override
    public Page<WfCurInstance> getInatanceByParameters(WfCurInstance entity, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<WfCurInstance> wfCurInstances = instanceMapper.select(entity);
        Page instances = PageHelper.endPage();
        return instances;
    }

    @Override
    public Integer getTemeplateId(Integer instanceId) {
        WfCurInstance wfCurInstance =  new WfCurInstance();
        wfCurInstance.setInstanceid(instanceId);
        WfCurInstance instance = instanceMapper.selectByPrimaryKey(wfCurInstance);

        return instance==null? null:instance.getTemplateid();
    }

    @Override
    public WfCurInstance getInstanceByCidAndTid(WfCurInstance entity) {

        WfCurInstance wfCurInstance = instanceMapper.selectOne(entity);

        return wfCurInstance;
    }

    @Override
    public WfCurInstance getInstanceByInstanceId(Integer instanceId) {
        return instanceMapper.selectByPrimaryKey(instanceId);
    }
}
