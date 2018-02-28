package com.imooc.miaosha.domain;

/**
 * miaosha_order数据库映射实体
 */
public class MiaoshaOrder {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 订单ID
     */
    private Long orderId;

    /**
     * 商品ID
     */
    private Long goodsId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取用户ID
     */
    public Long getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     */
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    /**
     * 获取订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取商品ID
     */
    public Long getGoodsId() {
        return goodsId;
    }

    /**
     * 设置商品ID
     */
    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}