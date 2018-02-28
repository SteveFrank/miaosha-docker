package com.imooc.miaosha.redis.key;

import com.imooc.miaosha.redis.prefix.BasePrefix;

/**
 * @author yangqian
 * @date 2018/2/13
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    /**
     * 缓存时长设置不可过长
     */
    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getMiaoshaGoodsStock= new GoodsKey(0, "gs");
    public static GoodsKey getMiaoshaGoodsDeatil = new GoodsKey(60, "gd");
}
