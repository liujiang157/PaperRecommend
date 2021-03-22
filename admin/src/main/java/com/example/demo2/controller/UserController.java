package com.example.demo2.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo2.model.User;
import com.example.demo2.service.AdminService;
import com.example.demo2.service.UserService;
import com.example.demo2.utils.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AdminService adminService;

    /**
     * 用户列表
     *
     * @param request
     * @param model
     * @param page
     * @param size
     * @return
     */
    @RequestMapping("/userlist")
    public String userlist(HttpServletRequest request, Model model,
                           @RequestParam(value = "page", defaultValue = "1") Integer page,
                           @RequestParam(value = "size", defaultValue = "5") Integer size,
                           @RequestParam(value = "search", required = false) String search) {
        if(!adminService.isHasPrivilege(request)) {
            return "admin/adminlogin";
        }
        int totalpage = userService.getTotalCount(search) % size == 0 ? userService.getTotalCount(search) / size : userService.getTotalCount(search) / size + 1;
        if (page < 1) {
            page = 1;
        }
        if (page > totalpage) {
            page = totalpage;
        }
        List<Integer> pagelist = new ArrayList<>();
        for (int i = 1; i <= totalpage; i++) {
            pagelist.add(i);
        }
        List<User> userlist = userService.list(page, size, search);
        model.addAttribute("userlist", userlist);
        model.addAttribute("pagelist", pagelist);
        model.addAttribute("currentpage", page);
        return "admin/userlist";
    }

    @RequestMapping("/deleteuser")
    public String deleteuser(HttpServletRequest request,@RequestParam(value = "username") String username) {
        boolean IsDeleted = userService.deleteUserByName(username);
        if (IsDeleted)
            return "redirect:/userlist";
        return "/fail";
    }


    @RequestMapping("/updateuser")
    public String updateuser(HttpServletRequest request,@RequestParam("id") Integer id,Model model){
        User user = userService.selectUserById(id);
        model.addAttribute("user",user);
        return "admin/updateuser";

    }





    @PostMapping(value = "adduser.do", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String insert(HttpServletRequest request, User u) {
        boolean isEmailExisted = userService.isEmailExisted(u.getEmail());
        if (isEmailExisted)
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "添加失败");
        else {
            boolean isInserted = userService.insert(u);
            if (isInserted) {
                return AjaxResult.msg(HttpServletResponse.SC_OK, JSONObject.toJSON(u).toString());
            } else {
                return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "添加失败");
            }
        }
    }

    @PostMapping(value = "updateuser.do", produces = "text/html;charset=UTF-8")
    @ResponseBody
    public String update(HttpServletRequest request, User user) {
        boolean isUpdated = userService.update(user);
        if (isUpdated) {
            return AjaxResult.msg(HttpServletResponse.SC_OK, JSONObject.toJSON(user).toString());
        } else {
            return AjaxResult.msg(HttpServletResponse.SC_BAD_REQUEST, "更新失败");
        }
    }
}




