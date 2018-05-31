package com.bamaster.core.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-06
 *
 * @create 2017-12-06 10:50
 *
 * @desc Shiro token
 */
public class ShiroToken extends UsernamePasswordToken  implements java.io.Serializable{
	
	private static final long serialVersionUID = -6451794657814516274L;

	public ShiroToken(String username, String pswd) {
		super(username,pswd);
		this.pswd = pswd ;
	}
	
	
	/** 登录密码[字符串类型] 因为父类是char[] ] **/
	private String pswd ;

	public String getPswd() {
		return pswd;
	}


	public void setPswd(String pswd) {
		this.pswd = pswd;
	}
	
	
	
	
	
}
