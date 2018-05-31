package com.bamaster.core.utils;



import com.bamaster.shiro.pojo.UPermission;
import com.bamaster.shiro.pojo.URole;
import com.bamaster.shiro.pojo.UUser;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-29
 *
 * @create 2017-11-29 15:50
 *
 * @desc 用户相关的处理操作工具类
 */
public class UserManagerUtils {
	
	/**
	 * 加工密码，和登录一致。
	 * @param user
	 * @return
	 */
	public static UUser md5Pswd(UUser user){
		//密码为   username + '#' + pswd，然后MD5
		user.setPswd(md5Pswd(user.getUsername(),user.getPswd()));
		return user;
	}
	/**
	 * 字符串返回值
	 * @param userName
	 * @param pswd
	 * @return
	 */
	public static String md5Pswd(String userName ,String pswd){
		pswd = String.format("%s#%s", userName,pswd);
		pswd = MathUtil.getMD5(pswd);
		return pswd;
	}
	/**
	 * 把查询出来的roles 转换成bootstarp 的 tree数据
	 * @param roles
	 * @return
	 */
	public static List<Map<String,Object>> toTreeData(List<URole> roles){
		List<Map<String,Object>> resultData = new LinkedList<Map<String,Object>>();
		for (URole u : roles) {
			//角色列表
			Map<String,Object> map = new LinkedHashMap<String, Object>();
			map.put("text", u.getName());//名称
			map.put("href", "javascript:void(0)");//链接
			List<UPermission> ps = u.getPermissions();
			map.put("tags",  new Integer[]{ps.size()});//显示子数据条数
			if(null != ps && ps.size() > 0){
				List<Map<String,Object>> list = new LinkedList<Map<String,Object>>();
				//权限列表
				for (UPermission up : ps) {
					Map<String,Object> mapx = new LinkedHashMap<String, Object>();
					mapx.put("text", up.getName());//权限名称
					mapx.put("href", up.getUrl());//权限url
					//mapx.put("tags", "0");//没有下一级
					list.add(mapx);
				}
				map.put("nodes", list);
			}
			resultData.add(map);
		}
		return resultData;
		
	}
	
	
}
