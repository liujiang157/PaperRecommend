package com.paper.demo.service.impl;

import com.paper.demo.entity.bo.BrowerRecord;
import com.paper.demo.mapper.BrowseRecordMapper;
import com.paper.demo.mapper.UserMapper;
import com.paper.demo.service.RecordBrowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Service
public class RecordBrowerServiceImpl implements RecordBrowerService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private BrowseRecordMapper browseRecordMapper;


    @Override
    public void recordBrower(HttpServletRequest request, Integer paperId) {
        if(request.getSession().getAttribute("user")==null)
            return;

        int uerId = userMapper.selectByEmail((String) request.getSession().getAttribute("user")).getUserId();
        BrowerRecord browerRecord = new BrowerRecord(uerId,paperId);
        browseRecordMapper.insert(browerRecord);
    }

    @Override
    public List<BrowerRecord> getAllRecords() {
        return browseRecordMapper.getAllRecords();
    }
}
