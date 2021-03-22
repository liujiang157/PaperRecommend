package com.paper.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.paper.demo.entity.bo.User;
import com.paper.demo.service.PersonalRecService;
import com.paper.demo.service.UserService;
import com.paper.demo.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-19
 */
@Controller
public class RegisterController {


    @RequestMapping("/register")
    public String index() {
        return "register";
    }


    @Autowired
    private UserService userService;

    @Autowired
    private PersonalRecService personalRecService;

    @PostMapping(value = "register.do")
    @ResponseBody
    public String register(HttpServletRequest request
            , @RequestParam(value = "email") String email
            , @RequestParam(value = "password") String password
            , @RequestParam(value = "firstName") String firstName
            , @RequestParam(value = "lastName") String lastName
            , @RequestParam(value = "confrimpwd") String confrimpwd) {

        User u = new User(firstName, lastName, password, email);
        boolean isExist = userService.isEmailExisted(email);
        if (isExist) {
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "邮箱已存在");
        } else {
            if (!password.equals(confrimpwd))
                return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "密码不一致");
        }
        if (!userService.sendActivedEmail(email)) {
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "激活邮件发送失败");
        }
        boolean isInserted = userService.insert(u);
        if (isInserted) {
            personalRecService.initializePersonalRecList(request); //用户冷启动
            return AjaxResult.msg(HttpServletResponse.SC_OK, JSONObject.toJSON(u).toString());
        } else {
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "注册失败");
        }

    }


}
