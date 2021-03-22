package com.paper.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-19
 */
@Controller
public class IndexController {


    @RequestMapping("/index")
    public String index() {
        return "index";
    }


}
