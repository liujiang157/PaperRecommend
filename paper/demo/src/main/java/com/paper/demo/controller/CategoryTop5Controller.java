package com.paper.demo.controller;

import com.paper.demo.entity.bo.Category;
import com.paper.demo.entity.bo.Paper;
import com.paper.demo.entity.vo.Top5PaperVo;
import com.paper.demo.service.PaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author liujiang
 * @descrpition 展示所有种类Top5
 * @date 2021-03-19
 */
@Controller
public class CategoryTop5Controller {

    @Autowired
    private PaperService paperService;

    @RequestMapping(value = "CategoryTop.do", method = {RequestMethod.GET})
    public String categoryTop5FrameLoad(Model model) {
        List<Paper> papeTop5ListrList = paperService.getAllTop5();
        List<Category> categoryList = paperService.getAllCategory();
        List<Top5PaperVo> top5PaperVoList = new ArrayList<>();
        categoryList.forEach(t -> {
            Top5PaperVo item = new Top5PaperVo();
            item.setCatId(item.getCatId());
            item.setCategory(t.getCategory());
            item.setPaperList(papeTop5ListrList.stream().filter(m -> m.getCatId() == t.getCatId()).collect(Collectors.toList()));
            top5PaperVoList.add(item);
        });
        model.addAttribute("toplist", top5PaperVoList);
        return "index::NewAndTopPaperFrame";
    }

}
