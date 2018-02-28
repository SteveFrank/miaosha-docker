package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.MiaoshaOrderMapper;
import com.imooc.miaosha.dao.OrderInfoMapper;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.key.OrderKey;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author yangqian
 * @date 2018/2/11
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoMapper orderInfoMapper;
    @Autowired
    private MiaoshaOrderMapper miaoshaOrderMapper;
    @Autowired
    private RedisService redisService;


    @Override
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(Long userId, long goodsId) {
        return redisService.get(OrderKey.getMiaoshaOrderByUidGid, ""+userId+"_"+goodsId, MiaoshaOrder.class);
    }

    @Transactional
    @Override
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo) {

        OrderInfo orderInfo = new OrderInfo();
        Date date = new Date();
        orderInfo.setCreateDate(date);
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goodsVo.getId());
        orderInfo.setGoodsName(goodsVo.getGoodsName());
        orderInfo.setGoodsPrice(goodsVo.getGoodsPrice());
        orderInfo.setOrderChannel(Byte.parseByte("1"));
        orderInfo.setStatus(Byte.parseByte("0"));
        orderInfo.setUserId(user.getId());

        orderInfoMapper.insert(orderInfo);

        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goodsVo.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());

        miaoshaOrderMapper.insertMiaoshaOrder(miaoshaOrder);

        redisService.set(OrderKey.getMiaoshaOrderByUidGid,
                ""+user.getId()+"_"+goodsVo.getId(), miaoshaOrder);

        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {
        return orderInfoMapper.getOrderById(orderId);
    }

    @Override
    public void deleteOrders() {
        orderInfoMapper.deleteOrders();
        miaoshaOrderMapper.deleteMiaoshaOrders();
    }
}
