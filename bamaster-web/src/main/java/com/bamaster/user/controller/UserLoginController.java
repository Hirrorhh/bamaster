package com.bamaster.user.controller;


import com.bamaster.common.controller.*;
import com.bamaster.common.controller.TokenManagerUtils;
import com.bamaster.core.utils.*;
import com.bamaster.shiro.pojo.UUser;
import com.bamaster.user.service.UUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-28 10:50
 *
 * @desc 用户登录相关的controller
 */

@Controller
@Scope(value="prototype")
@RequestMapping("base")
@Api(value = "用户登录相关控制器", description = "用户登录相关控制器")
public class UserLoginController{

	@Resource
	UUserService userService;
	
	/**
	 * 登录跳转
	 * @return
	 */
	@RequestMapping(value="login",method= RequestMethod.GET)
	@ApiOperation(value = "用户登录页面跳转", notes = "用户登录页面跳转", httpMethod = "GET")
	public ModelAndView login(){
		
		return new ModelAndView("user/login");
	}
	/**
	 * 注册跳转
	 * @return
	 */
	@RequestMapping(value="register",method= RequestMethod.GET)
	@ApiOperation(value = "用户注册页面跳转", notes = "用户注册页面跳转", httpMethod = "GET")
	public ModelAndView register(){
		
		return new ModelAndView("user/register");
	}
	/**
	 * 注册 && 登录
	 * @param vcode		验证码	
	 * @param entity	UUser实体
	 * @return
	 */
	@RequestMapping(value="subRegister",method= RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户注册和登录", notes = "用户注册和登录", httpMethod = "POST")
	@ApiImplicitParams({
			//@ApiImplicitParam(name = "vcode", value = "验证码", required = true),
	})
	public Result subRegister(String vcode, @RequestBody UUser entity){
		LoggerUtils.debug(getClass(),"用户注册和登录"+entity);
		try {
			/*if(!VerifyCodeUtils.verifyCode(vcode)){
                return Result.error("300","验证码不正确!");
            }*/
			String username =  entity.getUsername();

			UUser user = userService.findUserByUserName(username);
			if(null != user){
                return Result.error("300","用户名已经存在!");
            }
			Date date = new Date();
			entity.setCreateTime(date);
			//把密码md5
			entity = UserManagerUtils.md5Pswd(entity);
			//设置有效
			entity.setStatus(Constant.ENABLE);

			entity = userService.insert(entity);
			LoggerUtils.fmtDebug(getClass(), "注册插入完毕！", JSONObject.fromObject(entity).toString());
		//	entity = TokenManagerUtils.login(entity, Boolean.TRUE);
			LoggerUtils.fmtDebug(getClass(), "注册后，登录完毕！", JSONObject.fromObject(entity).toString());
			return Result.ok("注册成功!");
		} catch (Exception e) {
			e.printStackTrace();
			return Result.error();
		}
	}
	/**
	 * 登录提交
	 * @param entity		登录的UUser
	 * @param rememberMe	是否记住
	 * @param request		request，用来取登录之前Url地址，用来登录后跳转到没有登录之前的页面。
	 * @return
	 */

	@RequestMapping(value="submitLogin",method= RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST")
	@ApiImplicitParams({
			/*@ApiImplicitParam(name = "vcode", value = "验证码", required = true),*/
			@ApiImplicitParam(name = "rememberMe", value = "是否记住我", required = true,dataType = "Boolean")
	})
	public Result submitLogin(@RequestBody UUser entity, Boolean rememberMe, HttpServletRequest request){
		LoggerUtils.debug(getClass(),"用户登录"+entity+"记住状态" + rememberMe);
		try {

			entity = UserManagerUtils.md5Pswd(entity);
			//使用shiro的登录
			entity = TokenManagerUtils.login(entity,rememberMe);
			

			 //shiro 获取登录之前的地址

			SavedRequest savedRequest = WebUtils.getSavedRequest(request);
			String url = null ;
			if(null != savedRequest){
				url = savedRequest.getRequestUrl();
			}
			/**
			 * 我们平常用的获取上一个请求的方式，在Session不一致的情况下是获取不到的
			 * String url = (String) request.getAttribute(WebUtils.FORWARD_REQUEST_URI_ATTRIBUTE);
			 */
			LoggerUtils.fmtDebug(getClass(), "获取登录之前的URL:[%s]",url);
			//如果登录之前没有地址，那么就跳转到首页。
			if(StringUtils.isBlank(url)){
				url = request.getContextPath() + "/user/index.html";
			}
			//跳转地址
			return Result.ok().put("back_url", url);
		/**
		 * 这里其实可以直接catch Exception，然后抛出 message即可，但是最好还是各种明细catch 好点。。
		 */
		} catch (DisabledAccountException e) {
			return Result.error("帐号已经禁用。");
		} catch (Exception e) {
			return Result.error("帐号或密码错误");
		}
			

	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping(value="logout",method = RequestMethod.POST)
	@ResponseBody
	@ApiOperation(value = "用户退出", notes = "用户退出", httpMethod = "POST")
	public Result logout(){
		try {
			TokenManagerUtils.logout();
			return Result.ok();
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "退出出现错误，%s。", e.getMessage());
			return Result.error("用户退出时出现异常");
		}
	}
}
