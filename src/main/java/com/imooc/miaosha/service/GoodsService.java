package com.imooc.miaosha.service;

import com.imooc.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * @author yangqian
 * @date 2018/2/11
 */
public interface GoodsService {
    /**
     * 查询出所有的秒杀商品
     * @return 返回所有的秒杀商品
     */
    List<GoodsVo> listGoodsVo();

    GoodsVo getGoodsVoByGoodsId(Long goodsId);

    boolean reduceStock(GoodsVo goods);

    void resetStock(List<GoodsVo> goodsList);
}
