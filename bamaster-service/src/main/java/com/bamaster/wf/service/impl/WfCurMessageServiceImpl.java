package com.bamaster.wf.service.impl;/**
 * Created by Hirror on 2017/12/18.
 */

import com.bamaster.core.utils.PageHelper;
import com.bamaster.core.utils.PageHelper.Page;
import com.bamaster.wf.dao.WfCurMessageMapper;
import com.bamaster.wf.pojo.WfCurMessage;
import com.bamaster.wf.service.WfCurMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 15:57
 *
 * @desc
 **/
@Service
public class WfCurMessageServiceImpl implements WfCurMessageService {

    @Autowired
    private WfCurMessageMapper messageMapper;

    @Override
    public void insertMessage(WfCurMessage notice) {
        messageMapper.insertSelective(notice);
    }

    @Override
    public Page findMessageByUserId(WfCurMessage wfCurMessage, Integer pageNo, Integer pageSize) {
        PageHelper.startPage(pageNo,pageSize);
        List<WfCurMessage> messageList = messageMapper.select(wfCurMessage);
        Page messages = PageHelper.endPage();
        return messages;
    }
}
