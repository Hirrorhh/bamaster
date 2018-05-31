package com.bamaster.shiro.bo;



import com.bamaster.shiro.pojo.UUser;

import java.io.Serializable;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:38
 *
 * @desc 用户角色分配 方便查询语句的接收
 */
public class UserRoleAllocationBo extends UUser implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//Role Name列转行，以,分割
	private String roleNames;
	//Role Id列转行，以‘,’分割
	private String roleIds;
	public String getRoleNames() {
		return roleNames;
	}
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}
	public String getRoleIds() {
		return roleIds;
	}
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}
	
}
