package com.example.demo2.service;

import com.example.demo2.model.Paper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: Liujiang
 * @Date: 2020/4/5 14:30
 */

public interface PersonalRecService {
    /**
     * 获取当前用户每天的个性化推荐论文列表，并带上是否已经收藏的标记.
     * 每天早上6点更新一次
     * @param request
     * HttpServletRequest
     * @return
     * 若没有获取到，返回null
     */
    List<Paper> getPersonalDailyRecWithCollectionFlag(HttpServletRequest request);

    /**
     * 初始化当前用户的个性化论文推荐列表
     * @param request
     * HttpServletRequest
     */
    void initializePersonalRecList(HttpServletRequest request);

    /**
     * 更新个性化推荐列表B
     * @param user2song
     * userId to songId matrix
     */
    void updatePersonalRecIntoB(Map<Integer, Integer[]> user2song);

    /**
     * 更新个性化推荐列表A
     * @param user2song
     * userId to songId matrix
     */
    void updatePersonalRecIntoA(Map<Integer, Integer[]> user2song);

}
