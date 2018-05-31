package com.bamaster.wf.service.impl;

import com.bamaster.wf.dao.WfHistInstanceMapper;
import com.bamaster.wf.service.WfHistInstancesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 15:28
 *
 * @desc
 **/
@Service
public class WfHistInstancesServiceImpl implements WfHistInstancesService {

    @Autowired
    private WfHistInstanceMapper histInstanceMapper;
}
