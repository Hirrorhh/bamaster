package com.bamaster.bamaster.node.controller;

import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.Result;
import com.bamaster.wf.controller.BaseNodeController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-18
 *
 * @create 2017-12-18 15:53
 *
 * @desc 备案大师节点2控制器
 **/
@Controller
@Scope(value = "prototype")
@RequestMapping("bamaster_st7")
@Api(value = "备案大师节点控制器ST7", description = "备案大师节点控制器ST7")
public class BaNodeControllerST7 extends BaseNodeController{



}
