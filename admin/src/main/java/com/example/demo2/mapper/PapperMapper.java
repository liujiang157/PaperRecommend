package com.example.demo2.mapper;

import com.example.demo2.model.Category;
import com.example.demo2.model.Paper;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PapperMapper {

    @Insert("insert into paper(title,url,catId) values( #{paper.title},#{paper.url},#{paper.catId} )")
    @Options(useGeneratedKeys=true, keyProperty="paperId", keyColumn="paperId")
    Integer insertOnlypaper(@Param("paper") Paper paper);

    @Insert("insert into paper(title,url,catId) values( #{paper.title},#{paper.url},#{paper.catId}, )")
    Integer insertpaperWithCover(@Param("paper") Paper paper);

    @Select("select count(1) from paper")
    Integer selectCount();

    @Select("select count(1) from paper where title = #{search}")
    Integer selectCountByName(String search);

    @Select("select * from paper limit #{offset},#{size}")
    List<Paper> listByPage(int offset, Integer size);

    @Select("select * from paper where title like CONCAT('%',#{search},'%') limit #{offset},#{size}")
    List<Paper> SearchByPage(int offset, Integer size, String search);

    @Delete("delete from paper where paperId = #{paperId}")
    Integer deleteUserByName(String paperId);



    @Select("select * from category")
    List<Category> selectAllCat();
}
