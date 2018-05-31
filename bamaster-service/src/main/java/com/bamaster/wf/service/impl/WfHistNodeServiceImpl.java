package com.bamaster.wf.service.impl;

import com.bamaster.wf.dao.WfHistNodeMapper;
import com.bamaster.wf.service.WfHistNodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 15:27
 *
 * @desc
 **/
@Service
public class WfHistNodeServiceImpl implements WfHistNodeService {

    @Autowired
    private WfHistNodeMapper histNodeMapper;
}
