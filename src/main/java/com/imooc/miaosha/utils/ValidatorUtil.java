package com.imooc.miaosha.utils;

import com.alibaba.druid.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 手机号基础校验
 * @author yangqian
 * @date 2018/1/14
 */
public class ValidatorUtil {

    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String src) {
        if(StringUtils.isEmpty(src)) {
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }

//	public static void main(String[] args) {
//			System.out.println(isMobile("18912341234"));
//			System.out.println(isMobile("1891234123"));
//	}
}
