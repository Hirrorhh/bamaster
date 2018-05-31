package com.bamaster.core.utils;/**
 * Created by Hirror on 2017/12/19.
 */

import java.util.UUID;

/**
 * @author jf_hirror
 *
 * @version basic 1.0 2017-12-19
 *
 * @create 2017-12-19 16:10
 *
 * @desc 文件上传工具类
 *
 **/
public class UploadUtil {

    public static String generateRandomDir(String uuidName) {
            int code = uuidName.hashCode();
            int firstDir = code & 0xf;
            int secondDir = (code >>> 4) & 0xf;
            return "/" + firstDir + "/" + secondDir;
        }

    public static String generateUUIDFileName(String fileName) {
            String uuid = UUID.randomUUID().toString();
            String ext = fileName.substring(fileName.lastIndexOf("."));
            return uuid + ext;
        }


}
