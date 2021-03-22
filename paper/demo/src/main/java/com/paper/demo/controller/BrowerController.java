package com.paper.demo.controller;

import com.paper.demo.annotation.AutoIdempotent;
import com.paper.demo.service.RecordBrowerService;
import com.paper.demo.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Controller
public class BrowerController {

    @Autowired
    private RecordBrowerService recordBrowerService;

    @AutoIdempotent
    @PostMapping(value = "recordBrower.do")
    @ResponseBody
    public String recordPlay(HttpServletRequest request, Integer paperId) {
        recordBrowerService.recordBrower(request, paperId);
        return AjaxResult.msg(HttpServletResponse.SC_OK, "记录用户浏览行为成功");
    }
}
