package com.imooc.miaosha.redis.prefix;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public class BasePrefix implements KeyPrefix {

    /**
     * 过期时间
     */
    private int expireSeconds;

    /**
     * Key前缀
     */
    private String prefix;

    /**
     * 0代表永不过期
     * @param prefix 构造函数
     */
    public BasePrefix(String prefix) {
        this(0, prefix);
    }

    public BasePrefix( int expireSeconds, String prefix) {
        this.expireSeconds = expireSeconds;
        this.prefix = prefix;
    }

    /**
     * 默认0代表永不过期
     * @return
     */
    @Override
    public int expireSeconds() {
        return expireSeconds;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":" + prefix;
    }

}
