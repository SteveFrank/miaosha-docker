package com.imooc.miaosha.redis;

import com.alibaba.fastjson.JSON;
import com.imooc.miaosha.redis.prefix.KeyPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Service("redisService")
public class RedisService {

    @Autowired
    private JedisPool jedisPool;

    public <T> T get(KeyPrefix prefix, String key, Class<T> clazz) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = jedis.get(realKey);
            T result = stringToBean(str, clazz);
            return result;
        } catch (Exception e) {
            returnToPool(jedis);
            return null;
        }

    }

    public <T> boolean set(KeyPrefix prefix, String key, T value) {
        Jedis jedis = null;

        try {
            jedis = jedisPool.getResource();
            // 生成真正的key
            String realKey = prefix.getPrefix() + key;
            String str = beanToString(value);
            int seconds = prefix.expireSeconds();
            if (seconds <= 0) {
                jedis.set(realKey, str);
            } else {
                // 设置过期时间
                jedis.setex(realKey, seconds, str);
            }
            if (!Objects.isNull(str)) {
                jedis.set(realKey, str);
                return true;
            }
        } catch (Exception e) {
            returnToPool(jedis);
            return false;
        }

        return false;
    }

    /**
     * 判断key是否存在
     * */
    public <T> boolean exists(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.exists(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 删除
     * */
    public boolean delete(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            long ret =  jedis.del(realKey);
            return ret > 0;
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 增加值
     */
    public <T> Long incr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.incr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     * 减少值
     */
    public <T> Long decr(KeyPrefix prefix, String key) {
        Jedis jedis = null;
        try {
            jedis =  jedisPool.getResource();
            //生成真正的key
            String realKey  = prefix.getPrefix() + key;
            return  jedis.decr(realKey);
        }finally {
            returnToPool(jedis);
        }
    }

    /**
     *
     * @param str JSON字符串
     * @param clazz 对象
     * @param <T> 对象通用模型
     * @return 返回对应的转化对象
     */
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if (str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if (clazz == int.class || clazz == Integer.class) {
            return (T) Integer.valueOf(str);
        } else if (clazz == long.class || clazz == Long.class) {
            return (T) Long.valueOf(str);
        } else if (clazz == String.class) {
            return (T) str;
        } else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }

    /**
     * 将对象转化为字符串
     * @param value 对象
     * @param <T> 对象通用类型
     * @return 返回转化的字符串
     */
    public static <T> String beanToString(T value) {
        if (value == null) {
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class) {
            return "" + value;
        } else if (clazz == long.class || clazz == Long.class) {
            return "" + value;
        } else if (clazz == String.class) {
            return (String) value;
        } else {
            return JSON.toJSONString(value);
        }
    }

    /**
     * 关闭redis连接
     * @param jedis client对象
     */
    private void returnToPool(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }
    }



}
