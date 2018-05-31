package com.bamaster.common.controller;

import com.bamaster.core.shiro.ShiroToken;
import com.bamaster.shiro.pojo.UUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;

import java.util.List;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 15:50
 *
 * @desc Shiro管理下的Token工具类
 */
public class TokenManagerUtils {
	/**
	 * 获取当前登录的用户User对象
	 * @return
	 */
	public static UUser getToken(){
		UUser token = (UUser)SecurityUtils.getSubject().getPrincipal();
		return token ;
	}
	
	
	
	/**
	 * 获取当前用户的Session
	 * @return
	 */
	public static Session getSession(){
		return SecurityUtils.getSubject().getSession();
	}
	/**
	 * 获取当前用户NAME
	 * @return
	 */
	public static String getNickname(){
		return getToken().getNickname();
	}
	/**
	 * 获取当前用户ID
	 * @return
	 */
	public static Integer getUserId(){
		return getToken()==null?null:getToken().getId();
	}
	/**
	 * 把值放入到当前登录用户的Session里
	 * @param key
	 * @param value
	 */
	public static void setVal2Session(Object key ,Object value){
		getSession().setAttribute(key, value);
	}
	/**
	 * 从当前登录用户的Session里取值
	 * @param key
	 * @return
	 */
	public static Object getVal2Session(Object key){
		return getSession().getAttribute(key);
	}
	/**
	 * 获取验证码，获取一次后删除
	 * @return
	 */
	public static String getYZM(){
		String code = (String) getSession().getAttribute("CODE");
		getSession().removeAttribute("CODE");
		return code ;
	}
	
	
	/**
	 * 登录
	 * @param user
	 * @param rememberMe
	 * @return
	 */
	public static UUser login(UUser user,Boolean rememberMe){
		ShiroToken token = new ShiroToken(user.getUsername(), user.getPswd());
		token.setRememberMe(rememberMe);
		SecurityUtils.getSubject().login(token);
		return getToken();
	}


	/**
	 * 判断是否登录
	 * @return
	 */
	public static boolean isLogin() {
		return null != SecurityUtils.getSubject().getPrincipal();
	}
	/**
	 * 退出登录
	 */
	public static void logout() {
		SecurityUtils.getSubject().logout();
	}
	

}
