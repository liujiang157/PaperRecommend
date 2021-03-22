package com.paper.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.paper.demo.entity.bo.User;
import com.paper.demo.service.UserService;
import com.paper.demo.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-19
 */
@Controller
public class LoginController {
    @Autowired
    private UserService userService;



    @RequestMapping("/login")
    public String index() {
        return "login";
    }



    @PostMapping(value = "login.do")
    @ResponseBody
    public String login(HttpServletRequest request, User u) {
        boolean isUserExisted = userService.findLogin(u);
        boolean isActived = userService.findActivedState(u.getEmail());
        if (!isActived){
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "请去邮箱激活账号");
        }
        if (!isUserExisted) {
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "邮箱或密码错误");
        } else {
            request.getSession().setAttribute("user", u.getEmail());
            return AjaxResult.msg(HttpServletResponse.SC_OK, JSONObject.toJSON(u).toString());
        }
    }

    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/login";
    }
}
