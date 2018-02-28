package com.imooc.miaosha.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * @author yangqian
 * @date 2018/2/13
 */
@Configuration
public class MqConfig {

    public static final String MIAOSHA_QUEUE = "miaosha.queue";

    public static final String TOPIC_QUEUE1 = "topic.queue1";
    public static final String TOPIC_QUEUE2 = "topic.queue2";
    public static final String HEADER_QUEUE = "header.queue";

    public static final String TOPIC_EXCHANGE = "topicExchange";

    public static final String ROUTING_KEY1 = "topic.key1";

    public static final String FANOUT_EXCHANGE = "fanoutExchange";

    public static final String HEADERS_EXCHANGE = "headersExchange";

    /**
     * # 0或者多个单词，* 单个单词匹配
     */
    public static final String ROUTING_KEY2 = "topic.#";

    /**
     * direct 模式
     * @return
     */
    @Bean
    public Queue queue() {
        // 需要持久化
        return new Queue(MIAOSHA_QUEUE, true);
    }

    /**
     * Topic模式,交换机Exchange
     * 先将消息放入exchange中,在进行消息的发送
     * @return
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue(TOPIC_QUEUE1, true);
    }

    @Bean
    public Queue topicQueue2() {
        return new Queue(TOPIC_QUEUE2, true);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    @Bean
    public Binding topicBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with(ROUTING_KEY1);
    }

    @Bean
    public Binding topicBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with(ROUTING_KEY2);
    }

    /**
     * Fanout模式 交换机Exchange
     * @return
     */
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }

    /**
     * 广播模式 绑定的队列都可以收到消息
     * @return
     */
    @Bean
    public Binding fanoutBinding1() {
        return BindingBuilder.bind(topicQueue1()).to(fanoutExchange());
    }

    @Bean
    public Binding fanoutBinding2() {
        return BindingBuilder.bind(topicQueue2()).to(fanoutExchange());
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(HEADERS_EXCHANGE);
    }

    @Bean
    public Queue headersQueue1() {
        return new Queue(HEADER_QUEUE, true);
    }

    @Bean
    public Binding headersBinding() {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map.put("header1", "value1");
        map.put("header2", "value2");
        return BindingBuilder.bind(headersQueue1()).to(headersExchange()).whereAll(map).match();
    }

}
