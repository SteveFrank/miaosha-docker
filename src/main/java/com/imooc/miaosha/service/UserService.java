package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.User;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public interface UserService {

    User findById(Integer id);

    boolean tx();
}
