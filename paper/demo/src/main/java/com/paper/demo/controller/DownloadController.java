package com.paper.demo.controller;

import com.paper.demo.annotation.AutoIdempotent;
import com.paper.demo.service.RecordDownloadService;
import com.paper.demo.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Controller
public class DownloadController {

    @Autowired
    private RecordDownloadService recordDownloadService;


    @AutoIdempotent
    @RequestMapping(value = "download.do", method = {RequestMethod.POST})
    @ResponseBody
    public String download(HttpServletRequest request, Integer paperId) throws IOException {
        //对于登录用户，记录其下载记录
        if(recordDownloadService.recordDownload(request, paperId)){
            return AjaxResult.msg(HttpServletResponse.SC_OK, "记录下载成功");
        }
        return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "记录下载失败");
    }

}

