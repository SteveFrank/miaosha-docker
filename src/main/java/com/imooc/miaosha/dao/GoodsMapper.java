package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.Goods;
import com.imooc.miaosha.domain.MiaoshaGoods;
import com.imooc.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface GoodsMapper {

    @Select("select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date " +
            " from miaosha_goods mg " +
            " left join goods g on mg.goods_id = g.id ")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*, mg.miaosha_price, mg.stock_count, mg.start_date, mg.end_date " +
            " from miaosha_goods mg " +
            " left join goods g on mg.goods_id = g.id " +
            " and mg.goods_id = #{goodsId} limit 1")
    GoodsVo getGoodsVoByGoodsId(@Param("goodsId") Long goodsId);

    /**
     * 更新商品库存操作
     * @param goods
     */
    @Update("update goods set goods_stock = goods_stock - 1 where id = #{id}")
    void reduceStock(Goods goods);

    @Update("update miaosha_goods set stock_count = #{stockCount} where goods_id = #{goodsId}")
    int resetStock(MiaoshaGoods g);
}