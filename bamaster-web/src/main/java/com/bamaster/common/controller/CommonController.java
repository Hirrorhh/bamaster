package com.bamaster.common.controller;


import com.bamaster.core.utils.LoggerUtils;
import com.bamaster.core.utils.Result;
import com.bamaster.core.utils.VerifyCodeUtils;
import com.bamaster.shiro.service.RoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
@Scope(value = "prototype")
@RequestMapping("open")
@Api(value = "通用控制器", description = "通用控制器")
public class CommonController {

    @Resource
    RoleService roleService;


    /**
     * 404错误
     *
     * @param request
     * @return
     */
    @RequestMapping("404")
    @ApiOperation(value = "状态码404页面跳转", notes = "状态码404页面跳转", httpMethod = "GET")
    public ModelAndView _404(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("common/404");
        return view;
    }

    /**
     * 500错误
     *
     * @param request
     * @return
     */
    @RequestMapping("500")
    @ApiOperation(value = "状态码500页面跳转", notes = "状态码500页面跳转", httpMethod = "GET")
    public ModelAndView _500(HttpServletRequest request) {
        ModelAndView view = new ModelAndView("common/500");

        Throwable t = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String defaultMessage = "未知";
        if (null == t) {
            view.addObject("line", defaultMessage);
            view.addObject("clazz", defaultMessage);
            view.addObject("methodName", defaultMessage);
            return view;
        }
        String message = t.getMessage();//错误信息
        StackTraceElement[] stack = t.getStackTrace();
        view.addObject("message", message);
        if (null != stack && stack.length != 0) {
            StackTraceElement element = stack[0];
            int line = element.getLineNumber();//错误行号
            String clazz = element.getClassName();//错误java类
            String fileName = element.getFileName();

            String methodName = element.getMethodName();//错误方法
            view.addObject("line", line);
            view.addObject("clazz", clazz);
            view.addObject("methodName", methodName);
            LoggerUtils.fmtError(getClass(), "line:%s,clazz:%s,fileName:%s,methodName:%s()",
                    line, clazz, fileName, methodName);
        }
        return view;
    }

    /**
     * 获取验证码
     *
     * @param response
     */
    @RequestMapping(value = "getVCode", method = RequestMethod.POST)
    @ApiOperation(value = "获取验证码", notes = "获取验证码", httpMethod = "POST")
    public void getVCode(HttpServletResponse response, HttpServletRequest request) {
        try {
            response.setHeader("Pragma", "No-cache");
            response.setHeader("Cache-Control", "no-cache");
            response.setDateHeader("Expires", 0);
            response.setContentType("image/jpg");

            //生成随机字串
            String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
            //存入Shiro会话session
            TokenManagerUtils.setVal2Session(VerifyCodeUtils.V_CODE, verifyCode.toLowerCase());
            //生成图片
            int w = 146, h = 33;
            VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "获取验证码异常：%s", e.getMessage());
        }
    }

    /**
     * 校验验证码
     *
     * @param
     */
    @RequestMapping(value = "checkVCode", method = RequestMethod.POST)
    @ApiOperation(value = "校验验证码", notes = "校验验证码", httpMethod = "POST")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "vcode", value = "验证码", required = true, paramType = "query"),
    })
    public Result getVCode(String vcode) {
        try {
            if (StringUtils.isBlank(vcode)) {
                return Result.error("验证码为空!");
            }
            String code = (String) TokenManagerUtils.getVal2Session(VerifyCodeUtils.V_CODE);
            if (!StringUtils.equalsIgnoreCase(vcode, code)) {
                return Result.error("验证码错误!");
            }
            return Result.ok();
        } catch (Exception e) {
            LoggerUtils.fmtError(getClass(), e, "校验验证码异常：%s", e.getMessage());
            return Result.error("校验验证码异常");
        }
    }

    /**
     * `
     * 跳转到其他网站
     *
     * @param url
     * @return
     */
    @RequestMapping(value = "/gotoWeb", method = RequestMethod.GET)
    @ApiOperation(value = "跳转到其他网站", notes = "跳转到其他网站", httpMethod = "GET")
    public ModelAndView _goto(String url) {

        return new ModelAndView("www/go_to", "url", url);
    }

    /**
     * 没有权限提示页面
     *
     * @return
     */
    @ApiOperation(value = "shiro验证没有权限的跳转页面", notes = "shiro验证没有权限的跳转页面", httpMethod = "GET")
    @RequestMapping(value = "unauthorized", method = RequestMethod.GET)
    public ModelAndView unauthorized() {
        return new ModelAndView("common/unauthorized");
    }

    @RequestMapping(value = "shiro", method = RequestMethod.GET)
    public ModelAndView shiro() {
        return new ModelAndView("shiro");
    }
}
