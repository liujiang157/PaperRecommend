package com.paper.demo.service.impl;

import com.paper.demo.entity.bo.Category;
import com.paper.demo.entity.bo.Paper;
import com.paper.demo.mapper.PaperMapper;
import com.paper.demo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Service
public class PaperServiceImpl implements PaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public List<Paper> getAllTop5() {
        return paperMapper.getAllTop5();
    }

    @Override
    public List<Category> getAllCategory() {
        return paperMapper.getAllCategory();
    }

    @Override
    public Paper getPaperByPK(Integer paperId) {
        return paperMapper.getPaperByPK(paperId);
    }

    @Override
    public List<Integer> getAllPaperIdRecords() {
        return paperMapper.getAllPaperIdRecords();
    }
}
