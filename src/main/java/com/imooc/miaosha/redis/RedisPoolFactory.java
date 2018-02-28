package com.imooc.miaosha.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Component
public class RedisPoolFactory {

    @Autowired
    private RedisConfig redisConfig;

    @Bean
    public JedisPool JedisPoolFactory() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisConfig.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisConfig.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisConfig.getPoolMaxWait() * 1000);

        JedisPool pool =
                new JedisPool(poolConfig, redisConfig.getHost(),
                        redisConfig.getPort(), redisConfig.getTimeout() * 1000, redisConfig.getPassword(), 0);
        return pool;
    }
}
