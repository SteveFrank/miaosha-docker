package com.imooc.miaosha.controller.demo;

import com.imooc.miaosha.domain.User;
import com.imooc.miaosha.rabbitmq.MqSender;
import com.imooc.miaosha.redis.prefix.KeyPrefix;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.key.UserKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/1/14
 */
@Controller
@RequestMapping("/sample")
public class SampleController {

    @Autowired
    private RedisService redisService;
    @Autowired
    private UserService userService;
    @Autowired
    private MqSender mqSender;

//    @RequestMapping("/mq")
//    @ResponseBody
//    public Result mq() {
//        mqSender.send("hello, imooc");
//        return Result.success(CodeMsg.SUCCESS);
//    }

//    @RequestMapping("/mqTopic")
//    @ResponseBody
//    public Result mqTopic() {
//        mqSender.sendTopic("hello, imooc MQ Topic");
//        return Result.success(CodeMsg.SUCCESS);
//    }
//
//    @RequestMapping("/mqFanout")
//    @ResponseBody
//    public Result mqFanout() {
//        mqSender.sendFanout("hello, imooc MQ Fanout");
//        return Result.success(CodeMsg.SUCCESS);
//    }
//
//    @RequestMapping("/mqHeader")
//    @ResponseBody
//    public Result mqHeader() {
//        mqSender.sendHeader("hello, imooc MQ Header");
//        return Result.success(CodeMsg.SUCCESS);
//    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<String> redisGet(@RequestParam("key") String key) {
        KeyPrefix prefix = new KeyPrefix() {
            @Override
            public int expireSeconds() {
                return 0;
            }

            @Override
            public String getPrefix() {
                return "";
            }
        };
        String result = redisService.get(prefix, key, String.class);
        return Result.success(result);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<String> redisSet(@RequestParam("key") String key,
                                   @RequestParam("value") String value) {
        KeyPrefix prefix = new KeyPrefix() {
            @Override
            public int expireSeconds() {
                return 0;
            }

            @Override
            public String getPrefix() {
                return "";
            }
        };
        Boolean result = redisService.set(prefix, key, value);
        return Result.success(result.toString());
    }

    @RequestMapping("/redis/setUser")
    @ResponseBody
    public Result<String> redisUserSet(@RequestParam("id") Integer id) {
        User user = userService.findById(id);
        if (!Objects.isNull(user)) {
            Boolean result = redisService.set(UserKey.getById, "" + 1, user);
            return Result.success(result.toString());
        }
        return Result.error(CodeMsg.SERVER_ERROR);
    }

}
