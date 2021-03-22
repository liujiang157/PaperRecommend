package com.paper.demo.mapper;

import com.paper.demo.entity.bo.Category;
import com.paper.demo.entity.bo.Paper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Mapper
public interface PaperMapper {

    @Select("SELECT\n" +
            "\t\tt1.*\t\n" +
            "\tFROM\n" +
            "\tpaper\tt1\n" +
            "\twhere exists (SELECT count(*) + 1 FROM paper t2 WHERE t2.catId = t1.catId AND t2.paperId > t1.paperId having (count(*) + 1) <= 5) \n" +
            "\tORDER BY t1.createTime\n")
    List<Paper> getAllTop5();

    @Select("select * from category")
    List<Category> getAllCategory();


    @Select("select * from paper where paperId = #{paperId}")
    Paper getPaperByPK(@Param("paperId") Integer paperId);

    @Select("select * from paper where order by createTime desc limit 12")
    List<Paper> get12NewPaper();

    @Select("select paperId from paper")
    List<Integer> getAllPaperIdRecords();

    @Select("select * from paper")
    List<Paper> getAllPapers();
}
