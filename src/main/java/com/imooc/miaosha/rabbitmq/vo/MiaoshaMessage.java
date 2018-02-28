package com.imooc.miaosha.rabbitmq.vo;

import com.imooc.miaosha.domain.MiaoshaUser;

import java.io.Serializable;

/**
 * @author yangqian
 * @date 2018/2/18
 */
public class MiaoshaMessage {

    private MiaoshaUser user;
    private Long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
