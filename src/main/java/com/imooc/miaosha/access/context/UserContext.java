package com.imooc.miaosha.access.context;

import com.imooc.miaosha.domain.MiaoshaUser;

/**
 * 多线程时候，安全保存方式
 * 与当前线程绑定
 * 放入的是当前线程
 * @author yangqian
 * @date 2018/2/19
 */
public class UserContext {

    private static ThreadLocal<MiaoshaUser> userHolder = new ThreadLocal<MiaoshaUser>();

    public static void setUser(MiaoshaUser user) {
        userHolder.set(user);
    }

    public static MiaoshaUser getUser() {
        return userHolder.get();
    }

}
