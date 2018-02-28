package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.rabbitmq.vo.MiaoshaMessage;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/2/13
 */
@Service
public class MqReceiver {

    private static Logger logger = LoggerFactory.getLogger(MqReceiver.class);

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;

    @RabbitListener(queues = MqConfig.MIAOSHA_QUEUE)
    public void receive(String message) {
        logger.info("MqReceiver --->>> " + message);

        // 获取队列中的秒杀信息
        MiaoshaMessage miaoshaMessage = RedisService.stringToBean(message, MiaoshaMessage.class);
        MiaoshaUser user = miaoshaMessage.getUser();
        Long goodsId = miaoshaMessage.getGoodsId();

        // 判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();
        if (stock <= 0) {
            return ;
        }
        // 判断是否秒杀到该物品
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (!Objects.isNull(order)) {
            return ;
        }
        // 减库存 下订单 写入数据库的秒杀订单
        // 秒杀服务
        miaoshaService.miaosha(user, goodsVo);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE1)
    public void receiveTopic1(String message) {
        logger.info("topic Queue1 message --->>> " + message);
    }

    @RabbitListener(queues = MqConfig.TOPIC_QUEUE2)
    public void receiveTopic2(String message) {
        logger.info("topic Queue2 message --->>> " + message);
    }

    @RabbitListener(queues = MqConfig.HEADER_QUEUE)
    public void receiveHeaderQueue(byte[] message) {
        logger.info("header queue message --->>> " + new String(message));
    }


}
