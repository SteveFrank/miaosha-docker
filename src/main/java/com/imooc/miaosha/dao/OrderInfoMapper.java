package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaOrder;
import com.imooc.miaosha.domain.OrderInfo;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface OrderInfoMapper {

    @Insert(" insert into order_info " +
            " (user_id, goods_id, goods_name, goods_count, " +
            " goods_price, order_channel, status, create_date) " +
            " values(" +
            " #{userId}, #{goodsId}, #{goodsName}, " +
            " #{goodsCount}, #{goodsPrice}, " +
            " #{orderChannel}, #{status}, #{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id",
            resultType=Long.class, before=false, statement="select @@IDENTITY")
    Long insert(OrderInfo orderInfo);

    @Select("select * from order_info where id = #{orderId}")
    OrderInfo getOrderById(@Param("orderId")long orderId);

    @Delete("delete from order_info")
    void deleteOrders();

}