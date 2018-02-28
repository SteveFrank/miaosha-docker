package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.vo.LoginVo;

import javax.servlet.http.HttpServletResponse;

/**
 * @author yangqian
 * @date 2018/1/14
 */
public interface MiaoShaUserService {

    /**
     *
     * @param id 主键ID 用户号码
     * @return
     */
    MiaoshaUser getById(Long id);

    /**
     * 更新密码
     * @param id
     * @param passwordNew
     * @return
     */
    boolean updatePassword(String token, Long id, String passwordNew);

    /**
     * 根据Token获取对应的user
     * @param token
     * @return
     */
    MiaoshaUser getByToken(HttpServletResponse response, String token);

    /**
     *
     * @param response
     * @param loginVo
     * @return
     */
    CodeMsg login(HttpServletResponse response, LoginVo loginVo);
}
