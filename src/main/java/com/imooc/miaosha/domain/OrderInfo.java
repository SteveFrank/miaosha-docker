package com.imooc.miaosha.domain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * order_info数据库映射实体
 */
public class OrderInfo {
    private Long id;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 商品ID
     */
    private Long goodsId;

    /**
     * 收获地址ID
     */
    private Long deliveryAddrId;

    /**
     * 冗余过来的商品名称
     */
    private String goodsName;

    /**
     * 商品数量
     */
    private Integer goodsCount;

    /**
     * 商品单价
     */
    private BigDecimal goodsPrice;

    /**
     * 1pc，2android，3ios
     */
    private Byte orderChannel;

    /**
     * 订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    private Byte status;

    /**
     * 订单的创建时间
     */
    private Date createDate;

    /**
     * 支付时间
     */
    private Date payDate;

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

    /**
     * 获取收获地址ID
     */
    public Long getDeliveryAddrId() {
        return deliveryAddrId;
    }

    /**
     * 设置收获地址ID
     */
    public void setDeliveryAddrId(Long deliveryAddrId) {
        this.deliveryAddrId = deliveryAddrId;
    }

    /**
     * 获取冗余过来的商品名称
     */
    public String getGoodsName() {
        return goodsName;
    }

    /**
     * 设置冗余过来的商品名称
     */
    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    /**
     * 获取商品数量
     */
    public Integer getGoodsCount() {
        return goodsCount;
    }

    /**
     * 设置商品数量
     */
    public void setGoodsCount(Integer goodsCount) {
        this.goodsCount = goodsCount;
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
     * 获取1pc，2android，3ios
     */
    public Byte getOrderChannel() {
        return orderChannel;
    }

    /**
     * 设置1pc，2android，3ios
     * @param orderChannel
     */
    public void setOrderChannel(Byte orderChannel) {
        this.orderChannel = orderChannel;
    }

    /**
     * 获取订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态，0新建未支付，1已支付，2已发货，3已收货，4已退款，5已完成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取订单的创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置订单的创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取支付时间
     */
    public Date getPayDate() {
        return payDate;
    }

    /**
     * 设置支付时间
     */
    public void setPayDate(Date payDate) {
        this.payDate = payDate;
    }
}