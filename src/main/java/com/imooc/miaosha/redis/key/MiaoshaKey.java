package com.imooc.miaosha.redis.key;

import com.imooc.miaosha.redis.prefix.BasePrefix;

/**
 * @author yangqian
 * @date 2018/2/18
 */
public class MiaoshaKey extends BasePrefix {

    private MiaoshaKey( int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static MiaoshaKey isGoodsOver = new MiaoshaKey(0, "go");
    public static MiaoshaKey getMiaoshaPath = new MiaoshaKey(60, "mp");
    public static MiaoshaKey getMiaoshaVerifyCode = new MiaoshaKey(300, "vc");
}