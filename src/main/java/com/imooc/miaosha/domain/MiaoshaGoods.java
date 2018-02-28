package com.imooc.miaosha.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * miaosha_goods数据库映射实体
 */
public class MiaoshaGoods {
    /**
     * 秒杀的商品表
     */
    private Long id;

    /**
     * 商品Id
     */
    private Long goodsId;

    /**
     * 秒杀价
     */
    private BigDecimal miaoshaPrice;

    /**
     * 库存数量
     */
    private Integer stockCount;

    /**
     * 秒杀开始时间
     */
    private Date startDate;

    /**
     * 秒杀结束时间
     */
    private Date endDate;

    /**
     * 获取秒杀的商品表
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置秒杀的商品表
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品Id
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品Id
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }

    /**
     * 获取秒杀价
     */
    public BigDecimal getMiaoshaPrice() {
        return miaoshaPrice;
    }

    /**
     * 设置秒杀价
     */
    public void setMiaoshaPrice(BigDecimal miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    /**
     * 获取库存数量
     */
    public Integer getStockCount() {
        return stockCount;
    }

    /**
     * 设置库存数量
     */
    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    /**
     * 获取秒杀开始时间
     */
    public Date getStartDate() {
        return startDate;
    }

    /**
     * 设置秒杀开始时间
     */
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    /**
     * 获取秒杀结束时间
     */
    public Date getEndDate() {
        return endDate;
    }

    /**
     * 设置秒杀结束时间
     */
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}