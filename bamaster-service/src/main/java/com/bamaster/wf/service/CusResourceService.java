package com.bamaster.wf.service;/**
 * Created by Hirror on 2017/12/19.
 */

import com.bamaster.wf.pojo.CusResource;

import java.util.List;

/**
 * @author jf_hirror
 * @version basic 1.0 2017-12-19
 * @create 2017-12-19 17:17
 * @desc
 **/
public interface CusResourceService {
    void saveResourceDetail(CusResource cusResource);

    void updateResourceDetail(CusResource cusResource);

    List<CusResource> getAllFileListByNodeid(Integer nodeid);
}
