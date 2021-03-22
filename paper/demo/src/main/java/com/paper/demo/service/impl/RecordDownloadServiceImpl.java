package com.paper.demo.service.impl;

import com.paper.demo.entity.bo.DownloadRecord;
import com.paper.demo.mapper.RecordDownloadMapper;
import com.paper.demo.mapper.UserMapper;
import com.paper.demo.service.RecordDownloadService;
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
public class RecordDownloadServiceImpl implements RecordDownloadService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RecordDownloadMapper recordDownloadMapper;

    @Override
    public boolean recordDownload(HttpServletRequest request, Integer paperId) {
        if(request.getSession().getAttribute("user")==null) {
            return true;
        }
        int affectrow = 0;
        int uerId = userMapper.selectByEmail((String) request.getSession().getAttribute("user")).getUserId();
        DownloadRecord downloadRecord= new DownloadRecord(uerId,paperId);
        affectrow = recordDownloadMapper.insert(downloadRecord);
        if(affectrow>0){
            return true;
        }
        return false;
    }

    @Override
    public List<DownloadRecord> getAllRecords() {
        return recordDownloadMapper.getAllRecords();
    }
}
