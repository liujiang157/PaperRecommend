package com.paper.demo.mapper;

import com.paper.demo.entity.bo.Paper;
import com.paper.demo.entity.bo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Mapper
public interface PersonalRecMapper {

    @Select("select * from reca,paper where reca.userId=#{user.userId} and reca.paperId=paper.paperId")
    List<Paper> selectPersonalRecFromA(@Param("user") User user);

    @Select("select * from recb,paper where recb.userId=#{user.userId} and recb.paperId=paper.paperId")
    List<Paper> selectPersonalRecFromB(@Param("user")User user);

    @Delete("delete from recb where userId=#{userId}")
    void deleteBByUserId(Integer userId);

    @Delete("delete from reca where userId=#{userId}")
    void deleteAByUserId(Integer userId);

    //批量插入
    @Insert({
            "<script>",
            "insert into reca(userId,paperId) values ",
            "<foreach collection='initialRecListA' item='item' index='index' separator=','>",
            "(#{id}, #{item.paperId})",
            "</foreach>",
            "</script>"
    })
    void insertListIntoRecA(List<Paper> initialRecListA, Integer id);


    @Insert({
            "<script>",
            "insert into recb(userId,paperId) values ",
            "<foreach collection='initialRecListB' item='item' index='index' separator=','>",
            "(#{id}, #{item.paperId})",
            "</foreach>",
            "</script>"
    })
    void insertListIntoRecB(List<Paper> initialRecListB, Integer id);


    @Insert({
            "<script>",
            "insert into recb(userId,paperId) values ",
            "<foreach collection='recSongIds' item='paperId' index='index' separator=','>",
            "(#{userId}, #{paperId})",
            "</foreach>",
            "</script>"
    })
    void insertArrayIntoRecB(Integer[] recSongIds, Integer userId);

    @Insert({
            "<script>",
            "insert into reca(userId,paperId) values ",
            "<foreach collection='recSongIds' item='paperId' index='index' separator=','>",
            "(#{userId}, #{paperId})",
            "</foreach>",
            "</script>"
    })
    void insertArrayIntoRecA(Integer[] recSongIds, Integer userId);
}

