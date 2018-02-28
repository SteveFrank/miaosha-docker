package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.MiaoshaUser;

/**
 * @author yangqian
 * @date 2018/2/13
 */
public class GoodsDetailVo {

    private int miaoshaStatus;
    private int remainSeconds;
    private GoodsVo goodsVo;
    private MiaoshaUser user;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoodsVo() {
        return goodsVo;
    }

    public void setGoodsVo(GoodsVo goodsVo) {
        this.goodsVo = goodsVo;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
