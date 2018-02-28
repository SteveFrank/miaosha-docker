package com.imooc.miaosha.rabbitmq;

import com.imooc.miaosha.rabbitmq.vo.MiaoshaMessage;
import com.imooc.miaosha.redis.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author yangqian
 * @date 2018/2/13
 */
@Service
public class MqSender {

    private static final Logger logger = LoggerFactory.getLogger(MqSender.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(MiaoshaMessage message) {
        String msg = RedisService.beanToString(message);
        logger.info("MQSender --->>> " + msg);
        amqpTemplate.convertAndSend(MqConfig.MIAOSHA_QUEUE, msg);
    }

//    /**
//     * 交换机模式
//     * @param meesage
//     */
//    public void sendTopic(Object meesage) {
//        String msg = RedisService.beanToString(meesage);
//        logger.info("send topic message : " + msg);
//        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,
//                MqConfig.ROUTING_KEY1, msg.concat(" -> " +MqConfig.ROUTING_KEY1));
//        amqpTemplate.convertAndSend(MqConfig.TOPIC_EXCHANGE,
//                MqConfig.ROUTING_KEY2, msg.concat(" -> " +MqConfig.ROUTING_KEY2));
//    }
//
//    /**
//     * 广播模式
//     * @param meesage
//     */
//    public void sendFanout(Object meesage) {
//        String msg = RedisService.beanToString(meesage);
//        logger.info("send Fanout message : " + msg);
//        amqpTemplate.convertAndSend(MqConfig.FANOUT_EXCHANGE,
//                "", msg.concat(" -> " +MqConfig.ROUTING_KEY1));
//    }
//
//    /**
//     * Header 模式
//     * @param message
//     */
//    public void sendHeader(Object message) {
//        String msg = RedisService.beanToString(message);
//        logger.info("send header message : " + msg);
//
//        MessageProperties properties = new MessageProperties();
//        properties.setHeader("header1", "value1");
//        properties.setHeader("header2", "value2");
//        Message objMessage = new Message(msg.getBytes(), properties);
//
//        amqpTemplate.convertAndSend(MqConfig.HEADERS_EXCHANGE,
//                "", objMessage);
//    }

}
