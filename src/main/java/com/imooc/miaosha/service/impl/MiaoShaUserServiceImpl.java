package com.imooc.miaosha.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.imooc.miaosha.dao.MiaoshaUserMapper;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.exception.GlobalException;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.key.MiaoshaUserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.service.MiaoShaUserService;
import com.imooc.miaosha.utils.MD5Util;
import com.imooc.miaosha.utils.UUIDUtil;
import com.imooc.miaosha.utils.ValidatorUtil;
import com.imooc.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Service("miaoShaUserService")
public class MiaoShaUserServiceImpl implements MiaoShaUserService {

    public static final String COOKI_NAME_TOKEN = "token";

    @Autowired
    private MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    private RedisService redisService;

    @Override
    public MiaoshaUser getById(Long id) {

        MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);

        if (!Objects.isNull(user)) {
            return user;
        } else {
            user = new MiaoshaUser();
            user.setId(id);
            user = miaoshaUserMapper.selectOne(user);
            redisService.set(MiaoshaUserKey.getById, ""+id, user);
        }
        return user;
    }

    /**
     * 修改用户密码
     * @param id
     * @param passwordNew
     * @return
     */
    @Override
    public boolean updatePassword(String token, Long id, String passwordNew) {
        // 取user
        MiaoshaUser user = getById(id);
        if (Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        MiaoshaUser toBeUpdate = new MiaoshaUser();
        toBeUpdate.setPassword(MD5Util.formPassToDBPass(passwordNew, user.getSalt()));
        miaoshaUserMapper.update(toBeUpdate);
        // 删除缓存
        redisService.delete(MiaoshaUserKey.getById, ""+id);
        user.setPassword(toBeUpdate.getPassword());
        redisService.set(MiaoshaUserKey.token, token, user);
        return true;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if (StringUtils.isEmpty(token)) {
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if (!Objects.isNull(user)) {
            // 延长有效期
            addCookie(response, token, user);
        }
        return user;
    }

    @Override
    public CodeMsg login(HttpServletResponse response, LoginVo loginVo) {
        if (loginVo == null) {
            throw new GlobalException(CodeMsg.SERVER_ERROR);
        }

        // 参数校验
        String passInput = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(passInput)) {
            throw new GlobalException(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)) {
            throw new GlobalException(CodeMsg.MOBILE_EMPTY);
        }
        if (!ValidatorUtil.isMobile(mobile)) {
            throw new GlobalException(CodeMsg.MOBILE_ERROR);
        }

        MiaoshaUser user = getById(Long.parseLong(mobile));

        // 验证手机号是否存在
        if (Objects.isNull(user)) {
            throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }

        // 验证密码
        String dbPass = user.getPassword();
        String saltDb = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(passInput, saltDb);
        if (!calcPass.equals(dbPass)) {
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }

        //生成cookie
        String token = UUIDUtil.uuid();
        addCookie(response, token, user);
        return CodeMsg.SUCCESS;
    }

    /**
     * 添加cookie
     * @param response
     * @param token 不需要每次都进行生成一个新的token
     * @param user
     */
    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        // 验证密码成功后，为了保证分布式Session，生成cookie
        redisService.set(MiaoshaUserKey.token, token, user);

        Cookie cookie = new Cookie(COOKI_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");

        response.addCookie(cookie);
    }
}
