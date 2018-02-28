package com.imooc.miaosha.controller;

import com.imooc.miaosha.access.AccessLimit;
import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.rabbitmq.MqSender;
import com.imooc.miaosha.rabbitmq.vo.MiaoshaMessage;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.key.AccessKey;
import com.imooc.miaosha.redis.key.GoodsKey;
import com.imooc.miaosha.redis.key.MiaoshaKey;
import com.imooc.miaosha.redis.key.OrderKey;
import com.imooc.miaosha.result.CodeMsg;
import com.imooc.miaosha.result.Result;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author yangqian
 * @date 2018/2/11
 */
@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private MiaoshaService miaoshaService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private MqSender mqSender;

    private Map<Long, Boolean> localOverMap = new HashMap<Long, Boolean>();

    @AccessLimit(maxCount = 5, seconds = 5, needLogin = true)
    @RequestMapping(value="/reset", method=RequestMethod.GET)
    @ResponseBody
    public Result<Boolean> reset() {
        List<GoodsVo> goodsList = goodsService.listGoodsVo();
        for(GoodsVo goods : goodsList) {
            goods.setStockCount(10);
            redisService.set(GoodsKey.getMiaoshaGoodsStock, ""+goods.getId(), 10);
            localOverMap.put(goods.getId(), false);
        }

        redisService.delete(OrderKey.getMiaoshaOrderByUidGid,"*");
        redisService.delete(MiaoshaKey.isGoodsOver,"*");
        miaoshaService.reset(goodsList);

        return Result.success(true);
    }

    @AccessLimit(maxCount = 5, seconds = 5, needLogin = true)
    @RequestMapping(value = "/verifyCode", method = RequestMethod.GET)
    @ResponseBody
    public Result getVerifyCode(HttpServletResponse response,
                                MiaoshaUser user,
                                @RequestParam("goodsId") long goodsId) throws IOException {
        // 如果用户没有登录，则需要进行登录后再进行秒杀
        if (Objects.isNull(user)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        BufferedImage image = miaoshaService.createMiaoshaVeriyCodeImage(user, goodsId);
        OutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "JPEG", outputStream);
            outputStream.flush();
        } catch (Exception e) {
            Result.error(CodeMsg.SERVER_ERROR);
        } finally {
            outputStream.close();
        }
        return Result.success("success");
    }

    @AccessLimit(maxCount = 5, seconds = 5, needLogin = true)
    @RequestMapping(value = "/path", method = RequestMethod.GET)
    @ResponseBody
    public Result getMiaoshaPath(
            HttpServletRequest request, MiaoshaUser user,
            @RequestParam("goodsId") long goodsId, @RequestParam("verifyCode") Integer verifyCode) {
        // 如果用户没有登录，则需要进行登录后再进行秒杀
        if (Objects.isNull(user)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        if (Objects.isNull(verifyCode)) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        boolean result = miaoshaService.checkVerifyCode(user, goodsId, verifyCode);
        if (result) {
            // 秒杀PATH获取与设置
            String path = miaoshaService.createMiaoshaPath(user, goodsId);
            return Result.success(path);
        }

        return Result.error(CodeMsg.REQUEST_ILLEGAL);
    }

    @AccessLimit(maxCount = 5, seconds = 5, needLogin = true)
    @RequestMapping(value = "/{path}/do_miaosha", method = RequestMethod.POST)
    @ResponseBody
    public Result doMiaoSha(Model model,
                            MiaoshaUser user,
                            @RequestParam("goodsId") long goodsId,
                            @PathVariable("path") String path) {
        model.addAttribute("user", user);
        // 如果用户没有登录，则需要进行登录后再进行秒杀
        if (Objects.isNull(user)) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }

        // 验证path是否正确
        boolean checkResult = miaoshaService.checkPath(user, goodsId, path);
        if (!checkResult) {
            return Result.error(CodeMsg.REQUEST_ILLEGAL);
        }

        // 内存标记,减少redis访问
        boolean over = localOverMap.get(goodsId);
        if (over) {
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }

        // 首先从redis中减少库存数量 （预减库存）
        long stock = redisService.decr(GoodsKey.getMiaoshaGoodsStock,"" + goodsId);
        // 判断秒杀失败否
        if (stock < 0) {
            localOverMap.put(goodsId, true);
            return Result.error(CodeMsg.MIAO_SHA_OVER);
        }
        // 判断是否已经秒杀到了商品
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (!Objects.isNull(order)) {
            return Result.error(CodeMsg.REPEATE_MIAOSHA);
        }

        // 入队列
        MiaoshaMessage miaoshaMessage = new MiaoshaMessage();
        miaoshaMessage.setUser(user);
        miaoshaMessage.setGoodsId(goodsId);
        mqSender.send(miaoshaMessage);

        // 排队中
        return Result.success(0);
    }

    /**
     * @param model Spring模型
     * @param user 用户
     * @return 返回对应的页面
     */
    @RequestMapping("/v1/do_miaosha")
    public String doMiaoShaV1(Model model, MiaoshaUser user,
                            @RequestParam("goodsId") long goodsId) {
        model.addAttribute("user", user);
        // 如果用户没有登录，则需要进行登录后再进行秒杀
        if (Objects.isNull(user)) {
            return "login";
        }
        // 判断库存
        GoodsVo goodsVo = goodsService.getGoodsVoByGoodsId(goodsId);
        int stock = goodsVo.getGoodsStock();
        if (stock <= 0) {
            model.addAttribute("errmsg", CodeMsg.MIAO_SHA_OVER);
            return "miaosha_fail";
        }
        // 判断是否已经秒杀到了商品
        MiaoshaOrder order
                = orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(), goodsId);
        if (!Objects.isNull(order)) {
            model.addAttribute("errmsg", CodeMsg.REPEATE_MIAOSHA);
            return "miaosha_fail";
        }

        // 秒杀服务
        OrderInfo orderInfo = miaoshaService.miaosha(user, goodsVo);
        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);

        return "order_detail";
    }

    /**
     * orderId：成功
     * -1：秒杀失败
     * 0： 排队中
     * */
    @AccessLimit(maxCount = 5, seconds = 5, needLogin = true)
    @RequestMapping(value="/result", method=RequestMethod.GET)
    @ResponseBody
    public Result<Long> miaoshaResult(Model model, MiaoshaUser user,
                                      @RequestParam("goodsId")long goodsId) {
        model.addAttribute("user", user);
        if(user == null) {
            return Result.error(CodeMsg.SESSION_ERROR);
        }
        long result = miaoshaService.getMiaoshaResult(user.getId(), goodsId);
        return Result.success(result);
    }



    /**
     * 容器启动，初始化的时候做的事儿
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() {
        List<GoodsVo> goodsVoList = goodsService.listGoodsVo();
        if (Objects.isNull(goodsVoList)) {
            return;
        }

        for (GoodsVo goodsVo : goodsVoList) {
            redisService.set(GoodsKey.getMiaoshaGoodsStock, "" + goodsVo.getId(), goodsVo.getStockCount());
            localOverMap.put(goodsVo.getId(), false);
        }
    }
}
