package com.example.demo2.mapper;


import com.example.demo2.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {

    @Insert("insert into user (firstName,lastName,password,email,isActived) values (#{firstName},#{lastName},#{password},#{email},0)")
    Integer insert(User user);

    @Select("select * from user where email=#{user.email} and password=#{user.password}")
    User selectByUser(@Param("user") User user);

    @Select("select * from user where email=#{email}")
    User selectByEmail(@Param("email") String email);

    @Select("select * from user where firstName = #{firstName}")
    User selectByName(@Param("firstName") String username);

    @Select("select * from user")
    List<User> selectAll();

    @Select("select userId from user")
    List<Integer> selectAllUserId();

    @Select("select * from user limit #{offset},#{size}")
    List<User> listByPage(Integer offset, Integer size);

    @Select("select count(1) from user")
    Integer selectCount();

    @Delete("delete from user where email = #{email}")
    Integer deleteUserByEmail(String email);

    @Select("select count(1) from user where email = #{email}")
    int selectCountByName(String email);

    @Select("select * from user where email like CONCAT('%',#{search},'%') limit #{offset},#{size}")
    List<User> SearchByPage(@Param("offset") int offset,@Param("size") Integer size,@Param("search") String search);

    @Select("select * from user where userId=#{id}")
    User selecUsertById(Integer id);

    @Update("update user set firstName = #{user.firstName},lastName = #{user.lastName},email = #{user.email},password = #{user.password} where userId =#{user.userId}")
    Integer update(@Param("user")User user);
}
