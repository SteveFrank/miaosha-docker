package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.MiaoshaUser;
import com.imooc.miaosha.domain.OrderInfo;
import com.imooc.miaosha.redis.RedisService;
import com.imooc.miaosha.redis.key.MiaoshaKey;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.service.MiaoshaService;
import com.imooc.miaosha.service.OrderService;
import com.imooc.miaosha.utils.MD5Util;
import com.imooc.miaosha.utils.UUIDUtil;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;
import java.util.Random;

/**
 * @author yangqian
 * @date 2018/2/11
 */
@Service
public class MiaoshaServiceImpl implements MiaoshaService {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private RedisService redisService;

    @Override
    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) {
        // 减库存，下订单，写入秒杀订单
        boolean success = goodsService.reduceStock(goodsVo);
        if (success) {
            // order_info miaosha_order 处理
            return orderService.createOrder(user, goodsVo);
        } else {
            // 设置商品卖完
            setGoodsOver(goodsVo.getId());
            return null;
        }
    }

    @Override
    public long getMiaoshaResult(Long userId, long goodsId) {
        MiaoshaOrder order = orderService.getMiaoshaOrderByUserIdGoodsId(userId, goodsId);

        if(!Objects.isNull(order)) {
            //秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }

    @Override
    public String createMiaoshaPath(MiaoshaUser user, Long goodsId) {
        String uuid_str = MD5Util.md5(UUIDUtil.uuid() + "-miaosha");
        redisService.set(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, uuid_str);
        return uuid_str;
    }

    @Override
    public boolean checkPath(MiaoshaUser user, long goodsId, String path) {
        if (Objects.isNull(user) || Objects.isNull(path)) {
            return false;
        }
        String redisPath = redisService.get(MiaoshaKey.getMiaoshaPath, ""+user.getId()+"_"+goodsId, String.class);
        if (redisPath.equals(path)) {
            return true;
        }
        return false;
    }

    @Override
    public BufferedImage createMiaoshaVeriyCodeImage(MiaoshaUser user, long goodsId) {
        if (Objects.isNull(user) || goodsId < 0L) {
            return null;
        }
        int width = 80;
        int height = 32;
        //create the image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        // set the background color
        g.setColor(new Color(0xDCDCDC));
        g.fillRect(0, 0, width, height);
        // draw the border
        g.setColor(Color.black);
        g.drawRect(0, 0, width - 1, height - 1);
        // create a random instance to generate the codes
        Random rdm = new Random();
        // make some confusion
        for (int i = 0; i < 50; i++) {
            int x = rdm.nextInt(width);
            int y = rdm.nextInt(height);
            g.drawOval(x, y, 0, 0);
        }
        // generate a random code
        String verifyCode = generateVerifyCode(rdm);
        g.setColor(new Color(0, 100, 0));
        g.setFont(new Font("Candara", Font.BOLD, 18));
        g.drawString(verifyCode, 8, 24);
        g.dispose();
        //把验证码存到redis中
        int rnd = calc(verifyCode);
        redisService.set(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, rnd);
        //输出图片
        return image;
    }

    @Override
    public boolean checkVerifyCode(MiaoshaUser user, long goodsId, int verifyCode) {
        if(user == null || goodsId <=0) {
            return false;
        }
        Integer codeOld = redisService.get(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId, Integer.class);
        if(codeOld == null || codeOld - verifyCode != 0 ) {
            return false;
        }
        redisService.delete(MiaoshaKey.getMiaoshaVerifyCode, user.getId()+","+goodsId);
        return true;
    }

    private static int calc(String exp) {
        try {
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("JavaScript");
            return (Integer)engine.eval(exp);
        }catch(Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    private static char[] ops = new char[] {'+', '-', '*'};
    /**
     * + - *
     * */
    private String generateVerifyCode(Random rdm) {
        int num1 = rdm.nextInt(10);
        int num2 = rdm.nextInt(10);
        int num3 = rdm.nextInt(10);
        char op1 = ops[rdm.nextInt(3)];
        char op2 = ops[rdm.nextInt(3)];
        String exp = ""+ num1 + op1 + num2 + op2 + num3;
        return exp;
    }

    /**
     * 设置数据库中商品卖完的状态
     * @param goodsId
     */
    private void setGoodsOver(Long goodsId) {
        redisService.set(MiaoshaKey.isGoodsOver, ""+goodsId, true);
    }

    /**
     * 商品卖完了
     * @param goodsId
     * @return
     */
    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }
}
