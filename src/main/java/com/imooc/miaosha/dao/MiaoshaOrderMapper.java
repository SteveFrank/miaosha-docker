package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MiaoshaOrderMapper {

    /**
     * @param userId 用户ID
     * @param goodsId 商品ID
     * @return 返回对应的秒杀订单对象
     */
    @Select(" select * from miaosha_order mo " +
            " where " +
            " user_id = #{userId} " +
            " and goods_id = #{goodsId} ")
    MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId") long userId,
                                                @Param("goodsId") long goodsId);

    @Insert("insert into miaosha_order (user_id, goods_id, order_id)values(#{userId}, #{goodsId}, #{orderId})")
    int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);

    /**
     * 保存数据
     */
    int insert(MiaoshaOrder record);

    /**
     * 更新主键ID对应的非NULL字段数据
     */
    int update(MiaoshaOrder record);

    /**
     * 通过条件查询数据信息
     */
    List<MiaoshaOrder> select(MiaoshaOrder record);

    /**
     * 通过条件查询唯一数据信息
     */
    MiaoshaOrder selectOne(MiaoshaOrder record);

    @Delete("delete from miaosha_order")
    void deleteMiaoshaOrders();
}