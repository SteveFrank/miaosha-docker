package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.UserMapper;
import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findById(Integer id) {
        return userMapper.getById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean tx() {

        User user1 = new User();
        user1.setId(2);
        user1.setName("John");

        User user2 = new User();
        user2.setId(3);
        user2.setName("Steve");

        userMapper.insert(user1);
        userMapper.insert(user2);

        return true;
    }
}
