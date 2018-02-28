package com.imooc.miaosha.redis.key;

import com.imooc.miaosha.redis.prefix.BasePrefix;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public class UserKey extends BasePrefix {

    private UserKey(String prefix) {
        super(prefix);
    }
    public static UserKey getById = new UserKey("miaosha_user_id");
    public static UserKey getByName = new UserKey("miaosha_user_name");

}
