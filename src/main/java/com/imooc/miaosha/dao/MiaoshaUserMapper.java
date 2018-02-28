package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.MiaoshaUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface MiaoshaUserMapper {
    /**
     * 保存数据
     */
    int insert(MiaoshaUser record);

    /**
     * 更新主键ID对应的非NULL字段数据
     */
    int update(MiaoshaUser record);

    /**
     * 通过条件查询数据信息
     */
    List<MiaoshaUser> select(MiaoshaUser record);

    /**
     * 通过条件查询唯一数据信息
     */
    MiaoshaUser selectOne(MiaoshaUser record);
}