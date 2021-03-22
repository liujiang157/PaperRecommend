package com.paper.demo.service;

import com.paper.demo.entity.bo.DownloadRecord;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
public interface RecordDownloadService {
    boolean recordDownload(HttpServletRequest request, Integer paperId);

    List<DownloadRecord> getAllRecords();
}
