package com.paper.demo.mapper;

import com.paper.demo.entity.bo.DownloadRecord;
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
public interface RecordDownloadMapper {

    @Insert("insert into download (userId,paperId) values(#{downloadRecord.userId},#{downloadRecord.paperId})")
    Integer insert( @Param("downloadRecord")DownloadRecord downloadRecord);

    @Select("select * from download")
    List<DownloadRecord> getAllRecords();
}
