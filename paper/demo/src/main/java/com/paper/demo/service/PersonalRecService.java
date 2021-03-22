package com.paper.demo.service;

import com.paper.demo.entity.bo.Paper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */

public interface PersonalRecService {
    void updatePersonalRecIntoB(Map<Integer, Integer[]> user2paperRecMatrix);

    void updatePersonalRecIntoA(Map<Integer, Integer[]> user2paperRecMatrix);

    void initializePersonalRecList(HttpServletRequest request);

    List<Paper> getPersonalDailyRecWithCollectionFlag(HttpServletRequest request);
}
