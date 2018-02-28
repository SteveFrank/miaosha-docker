package com.imooc.miaosha.service;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.vo.GoodsVo;

/**
 * @author yangqian
 * @date 2018/2/11
 */
public interface OrderService {

    /**
     * 查看秒杀订单内容
     * 是否用户重复秒杀
     * @param userId
     * @param goodsId
     * @return
     */
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId);

    OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo);

    OrderInfo getOrderById(long orderId);

    void deleteOrders();
}
