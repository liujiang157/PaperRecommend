package com.paper.demo.service;

import com.paper.demo.entity.bo.Category;
import com.paper.demo.entity.bo.Paper;

import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
public interface PaperService {
    List<Paper> getAllTop5();

    List<Category> getAllCategory();

    Paper getPaperByPK(Integer paperId);

    List<Integer> getAllPaperIdRecords();
}
