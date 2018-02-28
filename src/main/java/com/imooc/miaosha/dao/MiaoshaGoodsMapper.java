package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaGoods;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface MiaoshaGoodsMapper {

    @Update("update miaosha_goods set stock_count = stock_count - 1 where goods_id = #{goodsId} and stock_count > 0")
    int reduceStock(MiaoshaGoods g);
}