package com.bamaster.wf.service.impl;

import com.bamaster.core.utils.Constant;
import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.wf.dao.WfTemplateMapper;
import com.bamaster.wf.pojo.WfTemplate;
import com.bamaster.wf.service.WfTemplateService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jf_hirror
 * @version basic 1.0 2017-12-15
 * @create 2017-12-15 14:31
 * @desc
 **/
@Service
public class WfTemplateServiceImpl implements WfTemplateService {

    @Autowired
    private WfTemplateMapper templateMapper;

    @Override
    public boolean insert(WfTemplate entity) {
        int i = templateMapper.insert(entity);
        return i > 0 ? true : false;
    }

    @Override
    public void deleteTemplateByIds(String templateIds) {
        if (StringUtils.isNotBlank(templateIds)) {
            String[] ids = templateIds.split(",");
            if(ids !=null)
            for (String id : ids) {
                WfTemplate template = new WfTemplate();
                template.setTemplateid(Integer.parseInt(id));
                template.setDeleted(Constant.DISABLE);
                templateMapper.updateByPrimaryKeySelective(template);
            }

        }
    }

    @Override
    public Page getAllTemplateValid(Integer pageNo, Integer pageSize) {
        WfTemplate wfTemplate = new WfTemplate();
        wfTemplate.setDeleted(Constant.ENABLE);
        PageHelper.startPage(pageNo, pageSize);
        List<WfTemplate> wfTemplateList = templateMapper.select(wfTemplate);
        Page wfTemplates = PageHelper.endPage();
        return wfTemplates;
    }

    @Override
    public Integer getTemplateCompleteSizeByid(Integer templateid) {
        WfTemplate wfTemplate = new WfTemplate();
        wfTemplate.setTemplateid(templateid);
        WfTemplate result = templateMapper.selectByPrimaryKey(wfTemplate);
        return result == null ? null : result.getCompleteSize();
    }
}
