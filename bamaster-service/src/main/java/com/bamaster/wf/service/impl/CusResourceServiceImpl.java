package com.bamaster.wf.service.impl;

import com.bamaster.wf.dao.CusResourceMapper;
import com.bamaster.wf.pojo.CusResource;
import com.bamaster.wf.service.CusResourceService;
import com.github.abel533.entity.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-19
 *
 * @create 2017-12-19 17:17
 *
 * @desc
 **/
@Service
public class CusResourceServiceImpl implements CusResourceService {

    @Autowired
    CusResourceMapper resourceMapper;
    @Override
    public void saveResourceDetail(CusResource cusResource) {
            resourceMapper.insertSelective(cusResource);
    }

    @Override
    public void updateResourceDetail(CusResource cusResource) {
            resourceMapper.updateByPrimaryKeySelective(cusResource);
    }

    @Override
    public List<CusResource> getAllFileListByNodeid(Integer nodeid) {

        CusResource cusResource = new CusResource();
        cusResource.setNodeid(nodeid);
        List<CusResource> resourceList = resourceMapper.select(cusResource);
        return resourceList;

    }
}
