package com.bamaster.core.utils;



import java.util.HashMap;
import java.util.Map;


/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-28
 *
 * @create 2017-11-28 13:50
 *
 * @desc 返回数据实体类工具类(统一封装)
 */
public class Result extends HashMap<String, Object> {

	private static final long serialVersionUID = 1L;
	
	public Result() {
		put("status", "200");
	}

	public Result(String code, String msg) {
		put("status", code);
		put("message", msg);
	}
	
	public static Result error() {
		return new Result(Constant.RESULT.CODE_FAIL.getValue(),Constant.RESULT.MSG_FAIL.getValue());
	}
	
	public static Result error(String msg) {
		return error(Constant.RESULT.CODE_FAIL.getValue(), msg);
	}
	
	public static Result error(String code, String msg) {
		Result r = new Result();
		r.put("status", code);
		r.put("message", msg);
		return r;
	}

	public static Result ok(String msg) {
		Result r = new Result();
		r.put("message", msg);
		return r;
	}
	
	public static Result ok(Map<String, Object> map) {
		Result r = new Result();
		r.putAll(map);
		return r;
	}
	
	public static Result ok() {
		return new Result(Constant.RESULT.CODE_SUCCESS.getValue(),Constant.RESULT.MSG_SUCCESS.getValue());
	}

	public Result put(String key, Object value) {
		super.put(key, value);
		return this;
	}
}
