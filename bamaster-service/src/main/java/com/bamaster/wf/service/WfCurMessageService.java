package com.bamaster.wf.service;/**
 * Created by Hirror on 2017/12/18.
 */

import com.bamaster.core.utils.PageHelper;
import com.bamaster.wf.pojo.WfCurMessage;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 15:56
 *
 * @desc
 **/
public interface WfCurMessageService {
    void insertMessage(WfCurMessage notice);

    PageHelper.Page findMessageByUserId(WfCurMessage wfCurMessage, Integer pageNo, Integer pageSize);
}
