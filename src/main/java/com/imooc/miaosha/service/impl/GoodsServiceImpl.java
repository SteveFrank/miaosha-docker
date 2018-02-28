package com.imooc.miaosha.service.impl;

import com.imooc.miaosha.dao.GoodsMapper;
import com.imooc.miaosha.dao.MiaoshaGoodsMapper;
import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.service.GoodsService;
import com.imooc.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yangqian
 * @date 2018/2/11
 */
@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;
    @Autowired
    private MiaoshaGoodsMapper miaoshaGoodsMapper;

    @Override
    public List<GoodsVo> listGoodsVo() {
        return goodsMapper.listGoodsVo();
    }

    @Override
    public GoodsVo getGoodsVoByGoodsId(Long goodsId) {
        return goodsMapper.getGoodsVoByGoodsId(goodsId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean reduceStock(GoodsVo goodsVo) {
        Goods goods = new Goods();
        goods.setId(goodsVo.getId());
        goodsMapper.reduceStock(goods);
        MiaoshaGoods miaoshaGoods = new MiaoshaGoods();
        miaoshaGoods.setGoodsId(goodsVo.getId());
        int result = miaoshaGoodsMapper.reduceStock(miaoshaGoods);
        return result > 0;
    }

    @Override
    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setGoodsId(goods.getId());
            g.setStockCount(goods.getStockCount());
            goodsMapper.resetStock(g);
        }
    }
}
