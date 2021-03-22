package com.paper.demo.service;

import com.paper.demo.entity.bo.BrowerRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
public interface RecordBrowerService {
    void recordBrower(HttpServletRequest request, Integer paperId);

    List<BrowerRecord> getAllRecords();
}
