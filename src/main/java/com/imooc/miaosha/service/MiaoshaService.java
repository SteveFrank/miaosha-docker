package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

import java.awt.image.BufferedImage;
import java.util.List;

/**
 * @author yangqian
 * @date 2018/2/11
 */
public interface MiaoshaService {
    /**
     * 重置所有数据方便测试
     * @param goodsList
     */
    void reset(List<GoodsVo> goodsList);
    /**
     *
     * @param user
     * @param goodsVo
     * @return
     */
    OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo);

    /**
     *
     * @param userId
     * @param goodsId
     * @return
     */
    long getMiaoshaResult(Long userId, long goodsId);

    /**
     * 秒杀Path
     * @return
     */
    String createMiaoshaPath(MiaoshaUser user, Long goodsId);

    /**
     * 验证当前请求路径是否非法
     * @param user
     * @param goodsId
     * @param path
     * @return 非法返回false
     */
    boolean checkPath(MiaoshaUser user, long goodsId, String path);

    /**
     * 生成验证码
     * @param user
     * @param goodsId
     * @return
     */
    BufferedImage createMiaoshaVeriyCodeImage(MiaoshaUser user, long goodsId);

    /**
     * 秒杀验证
     * @param user
     * @param goodsId
     * @param verifyCode
     * @return
     */
    boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode);
}
