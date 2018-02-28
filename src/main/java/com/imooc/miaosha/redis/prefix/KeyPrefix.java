package com.imooc.miaosha.redis.prefix;

/**
 * Redis Key前缀
 * @author yangqian
 * @date 2018/1/14
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();
}
