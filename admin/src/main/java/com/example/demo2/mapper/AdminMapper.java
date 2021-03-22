package com.example.demo2.mapper;

import com.example.demo2.model.Admin;
import com.example.demo2.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface AdminMapper {
    @Select("select * from admin where admin = #{admin.name} and password = #{admin.password}")
    Admin selectAdmin(@Param("admin") Admin admin);
}
