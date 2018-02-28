package com.imooc.miaosha.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * MD5工具
 * @author yangqian
 * @date 2018/1/14
 */
public class MD5Util {

    public static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    /**
     * 加盐，更加安全
     */
    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + inputPass +salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = ""+salt.charAt(0)+salt.charAt(2) + formPass +salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        /*
         * d3b1294a61a07da9b49b6e22b2cbd7f9
         */
        System.out.println(inputPassToFormPass("123456"));
		System.out.println(formPassToDBPass(inputPassToFormPass("123456"), salt));
//		System.out.println(inputPassToDbPass("123456", "1a2b3c4d"));//b7797cce01b4b131b433b6acf4add449
    }
}
