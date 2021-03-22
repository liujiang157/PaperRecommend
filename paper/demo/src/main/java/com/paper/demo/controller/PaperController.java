package com.paper.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liujiang
 * @descrpition  搜索结果
 * @date 2021-03-19
 */
@Controller
public class PaperController {


    @RequestMapping("/paper")
    public String index() {
        return "paper";
    }
}
