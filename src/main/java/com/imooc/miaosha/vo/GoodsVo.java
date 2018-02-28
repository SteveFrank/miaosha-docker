package com.imooc.miaosha.vo;

import com.imooc.miaosha.domain.Goods;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 秒杀商品数据拼接
 * @author yangqian
 * @date 2018/2/11
 */
public class GoodsVo extends Goods implements Serializable {

    private static final long serialVersionUID = 8620828434481325896L;
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

    public BigDecimal getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(BigDecimal miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
