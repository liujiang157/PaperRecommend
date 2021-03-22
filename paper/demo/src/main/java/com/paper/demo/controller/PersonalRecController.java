package com.paper.demo.controller;


import com.paper.demo.entity.bo.Paper;
import com.paper.demo.service.PersonalRecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author: Liujiang
 * @Date: 2020/4/7 21:06
 */
@Controller
public class PersonalRecController {
    @Autowired
    private PersonalRecService personalRecService;


    @RequestMapping(value = "personalizedRecFrameLoad.do", method = {RequestMethod.GET})
    public String personalizedRecFrameLoad(HttpServletRequest request, Model model) {
        List<Paper> personalRecSongList = personalRecService.getPersonalDailyRecWithCollectionFlag(request);
        model.addAttribute("personalRecSongList", personalRecSongList);
        return "paper::personalizedRecFrame";
    }
}
