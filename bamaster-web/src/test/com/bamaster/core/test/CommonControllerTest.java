package com.bamaster.core.test;



import org.junit.Test;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import junit.framework.Assert;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;

/**
 * Created by Hirror on 2017/12/8.
 */
public class CommonControllerTest {
    @Test
    public void _404() throws Exception {
       /* byte[] asBytes = Base64.getDecoder().decode("3AvVhmFLUs0KTA3Kprsdag==");
        System.out.println(new String(asBytes, "utf-8"));*/
    }
    @Test
    public void _64() throws Exception {
        byte[] encode = Base64.encode("我其实很帅".getBytes());

        System.out.println(new String(encode));
        byte[] decode = Base64.decode("5oiR5YW25a6e5b6I5biF");
        System.out.println(Base64.encodeToString(decode));

        System.out.println(Base64.decode("3AvVhmFLUs0KTA3Kprsdag==").toString());
    }

    @Test
    public void key() throws Exception {
        //生成秘钥
        String password="testkey";
        try {
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            // kg.init(128);//要生成多少位，只需要修改这里即可128, 192或256
            //SecureRandom是生成安全随机数序列，password.getBytes()是种子，只要种子相同，序列就一样，所以生成的秘钥就一样。
            kg.init(256, new SecureRandom(password.getBytes()));
            SecretKey sk = kg.generateKey();
            byte[] b = sk.getEncoded();
            String s = byteToHexString(b);
            System.out.println(s);
            System.out.println("十六进制密钥长度为"+s.length());
            System.out.println("二进制密钥的长度为"+s.length()*4);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            System.out.println("没有此算法。");
        }
    }

    private String byteToHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String strHex=Integer.toHexString(bytes[i]);
            if(strHex.length() > 3){
                sb.append(strHex.substring(6));
            } else {
                if(strHex.length() < 2){
                    sb.append("0" + strHex);
                } else {
                    sb.append(strHex);
                }
            }
        }
        return  sb.toString();
    }

}