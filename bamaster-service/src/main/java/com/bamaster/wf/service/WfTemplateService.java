package com.bamaster.wf.service;

import com.bamaster.core.utils.PageHelper;
import com.bamaster.wf.pojo.WfTemplate;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-15
 *
 * @create 2017-12-15 14:22
 *
 * @desc
 **/
public interface WfTemplateService {
    boolean insert(WfTemplate entity);

    void deleteTemplateByIds(String templateIds);

    PageHelper.Page getAllTemplateValid(Integer pageNo, Integer pageSize);

    Integer getTemplateCompleteSizeByid(Integer templateid);
}
