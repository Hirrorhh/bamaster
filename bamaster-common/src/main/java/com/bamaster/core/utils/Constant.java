package com.bamaster.core.utils;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-11-28
 *
 * @create 2017-11-28 13:50
 *
 * @desc 常量的枚举类
 */
public class Constant {

    public static String URL404 =  "/404.html";
    //0:失效
    public static final Integer DISABLE = new Integer(0);
    //1:有效
    public static final Integer ENABLE = new Integer(1);
    //1:有效
    public static final Integer ING = new Integer(2);
    //1:有效
    public static final Integer COMPLETED = new Integer(3);
    public static final Integer NODE_END_ST = new Integer(99999);

    /**
     * 返回状态值
     */
    public enum RESULT{
        /**
         * 成功
         */
        CODE_SUCCESS("200"),
        /**
         * 失败
         */
        CODE_FAIL("500"),
        /**
         * 失败msg
         */
        MSG_SUCCESS("操作成功"),
        /**
         * 失败msg
         */
        MSG_FAIL("操作失败");
        private String value;

        private RESULT(String value){
            this.value=value;
        }
        public String getValue(){
            return value;
        }
    }


}
