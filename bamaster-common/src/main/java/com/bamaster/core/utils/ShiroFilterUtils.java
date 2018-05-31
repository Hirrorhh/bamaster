package com.bamaster.core.utils;


import net.sf.json.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 15:50
 *
 * @desc Shiro Filter 工具类
 */
public class ShiroFilterUtils {
	final static Class<? extends ShiroFilterUtils> CLAZZ = ShiroFilterUtils.class;
	//登录页面
	public static final String LOGIN_URL = "/u/login";
	//没有权限提醒
	public final static String UNAUTHORIZED = "/open/unauthorized";
	/**
	 * 是否是Ajax请求
	 * @param request
	 * @return
	 */
	public static boolean isAjax(ServletRequest request){
		return "XMLHttpRequest".equalsIgnoreCase(((HttpServletRequest) request).getHeader("X-Requested-With"));
	}
	
	/**
	 * response 输出JSON
	 * @param response
	 * @param resultMap
	 * @throws IOException
	 */
	public static void out(ServletResponse response, Map<String, String> resultMap){
		
		PrintWriter out = null;
		try {
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			out = response.getWriter();
			out.println(JSONObject.fromObject(resultMap).toString());
		} catch (Exception e) {
			LoggerUtils.fmtError(CLAZZ, e, "输出JSON报错。");
		}finally{
			if(null != out){
				out.flush();
				out.close();
			}
		}
	}
}
