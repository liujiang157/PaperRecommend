package com.paper.demo.mapper;

import com.paper.demo.entity.bo.BrowerRecord;
import org.apache.ibatis.annotations.Insert;
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
public interface BrowseRecordMapper {


    @Insert("insert into browse (paperId,userId) values (#{browerRecord.paperId},#{browerRecord.userId})")
    void insert(@Param("browerRecord") BrowerRecord browerRecord);

    @Select("select * from browse")
    List<BrowerRecord> getAllRecords();
}
