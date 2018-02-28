package com.imooc.miaosha.utils;

import java.util.UUID;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public class UUIDUtil {
    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
