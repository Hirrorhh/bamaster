package com.bamaster.shiro.bo;



import com.bamaster.shiro.pojo.URole;
import org.apache.commons.lang3.StringUtils;

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
public class URoleBo extends URole implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID (用String， 考虑多个ID，现在只有一个ID)
	 */
	private String userId;
	/**
	 * 是否勾选
	 */
	private String marker;

	public boolean isCheck(){
		return StringUtils.equals(userId,marker);
	}
	public String getMarker() {
		return marker;
	}

	public void setMarker(String marker) {
		this.marker = marker;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
