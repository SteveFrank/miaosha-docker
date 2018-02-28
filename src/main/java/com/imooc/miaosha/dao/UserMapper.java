package com.imooc.miaosha.dao;

import com.imooc.miaosha.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    /**
     * 查询对应用户
     * @param id 主键ID
     * @return
     */
    @Select("select * from user where id = #{id}")
    User getById(@Param("id")int id	);

    /**
     * 插入用户数据
     * @param user
     * @return
     */
    @Insert("insert into user(id, name)values(#{id}, #{name})")
    int insert(User user);
}