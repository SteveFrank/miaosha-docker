package com.imooc.miaosha.redis.key;

import com.imooc.miaosha.redis.prefix.BasePrefix;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public class OrderKey extends BasePrefix {

    public OrderKey(String prefix) {
        super(prefix);
    }

	public static OrderKey getMiaoshaOrderByUidGid = new OrderKey("moug");

}
