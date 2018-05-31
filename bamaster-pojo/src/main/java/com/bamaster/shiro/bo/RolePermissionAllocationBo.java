package com.bamaster.shiro.bo;

import java.io.Serializable;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-27
 *
 * @create 2017-11-27 14:38
 *
 * @desc 角色对应的权限 方便查询语句的接收
 */
public class RolePermissionAllocationBo implements Serializable {
	private static final long serialVersionUID = 1L;
	//角色ID
	private Long id;
	//角色type
	private String type;
	//角色Name
	private String name;
	//权限Name列转行，以,分割
	private String permissionNames;
	//权限Id列转行，以‘,’分割
	private String permissionIds;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPermissionNames() {
		return permissionNames;
	}

	public void setPermissionNames(String permissionNames) {
		this.permissionNames = permissionNames;
	}

	public String getPermissionIds() {
		return permissionIds;
	}

	public void setPermissionIds(String permissionIds) {
		this.permissionIds = permissionIds;
	}
	
	
}
