package com.paper.demo.controller;

import com.paper.demo.service.UserService;
import com.paper.demo.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-19
 */
@RestController
public class ActiveController {
    @Autowired
    private UserService userService;

    @RequestMapping("/active")
    public String active(String email){
        if(userService.findActivedState(email)){
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "邮箱已激活");
        }
        if(userService.ActivedUserState(email)){
            return AjaxResult.msg(HttpServletResponse.SC_OK, "激活成功");
        }
        return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "激活失败");
    }
}
