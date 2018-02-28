package com.imooc.miaosha.domain;

import java.math.BigDecimal;

/**
 * goods数据库映射实体
 */
public class Goods {
    /**
     * 商品ID
     */
    private Long id;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品标题
     */
    private String goodsTitle;

    /**
     * 商品的图片
     */
    private String goodsImg;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 商品库存，-1表示没有限制
     */
    private Integer goodsStock;

    /**
     * 商品的详情介绍
     */
    private String goodsDetail;

    /**
     * 获取商品ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置商品ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取商品标题
     */
    public String getGoodsTitle() {
        return goodsTitle;
    }

    /**
     * 设置商品标题
     */
    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    /**
     * 获取商品的图片
     */
    public String getGoodsImg() {
        return goodsImg;
    }

    /**
     * 设置商品的图片
     */
    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    /**
     * 获取商品单价
     */
    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    /**
     * 设置商品单价
     */
    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    /**
     * 获取商品库存，-1表示没有限制
     */
    public Integer getGoodsStock() {
        return goodsStock;
    }

    /**
     * 设置商品库存，-1表示没有限制
     */
    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    /**
     * 获取商品的详情介绍
     */
    public String getGoodsDetail() {
        return goodsDetail;
    }

    /**
     * 设置商品的详情介绍
     */
    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }
}