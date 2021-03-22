package com.paper.demo.controller;

import com.paper.demo.entity.bo.Paper;
import com.paper.demo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Controller
public class SingleController {

    @Autowired
    private PaperService paperService;

    @RequestMapping("/single")
    public String single(HttpServletRequest request, Model model,
                         @RequestParam(value = "paperId") Integer paperId) {
        Paper paper = paperService.getPaperByPK(paperId);
        model.addAttribute("paper", paper);
        return "single";
    }


}
