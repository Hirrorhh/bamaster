package com.bamaster.shiro.realm;


import com.bamaster.core.shiro.ShiroToken;
import com.bamaster.core.utils.Constant;
import com.bamaster.shiro.pojo.UUser;
import com.bamaster.shiro.service.PermissionService;
import com.bamaster.shiro.service.RoleService;
import com.bamaster.user.service.UUserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 15:50
 *
 * @desc shiro核心自定义文件,实现自定义的认证和授权
 */

public class SampleRealm extends AuthorizingRealm {

	@Autowired
	UUserService userService;
	@Autowired
	PermissionService permissionService;
	@Autowired
	RoleService roleService;
	
	public SampleRealm() {
		super();
	}
	/**
	 *  认证信息，主要针对用户登录， 
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		
		ShiroToken token = (ShiroToken) authcToken;
		UUser user = userService.login(token.getUsername(),token.getPswd());
		if(null == user){
			throw new AccountException("帐号或密码不正确！");
		/**
		 * 如果用户的status为禁用。那么就抛出<code>DisabledAccountException</code>
		 */
		}else if(Constant.DISABLE.equals(user.getStatus())){
			throw new DisabledAccountException("帐号已经禁止登录！");
		}
		return new SimpleAuthenticationInfo(user,user.getPswd(), getName());
    }

	 /** 
     * 授权 
     */  
    @Override  
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	

		SimpleAuthorizationInfo info =  new SimpleAuthorizationInfo();
		//根据用户ID查询角色（role），放入到Authorization里
		Subject subject = SecurityUtils.getSubject();
		UUser user = (UUser) subject.getPrincipal();
		Set<String> roles = roleService.findRoleByUserId(user.getId());
		info.setRoles(roles);
		//根据用户ID查询权限（permission），放入到Authorization里。
		Set<String> permissions = permissionService.findPermissionByUserId(user.getId());
		info.setStringPermissions(permissions);
        return info;  
    }  
    /**
     * 清空当前用户权限信息
     */
	public  void clearCachedAuthorizationInfo() {
		PrincipalCollection principalCollection = SecurityUtils.getSubject().getPrincipals();
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
	/**
	 * 指定principalCollection 清除
	 */
	public void clearCachedAuthorizationInfo(PrincipalCollection principalCollection) {
		SimplePrincipalCollection principals = new SimplePrincipalCollection(
				principalCollection, getName());
		super.clearCachedAuthorizationInfo(principals);
	}
}
