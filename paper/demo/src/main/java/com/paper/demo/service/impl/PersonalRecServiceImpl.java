package com.paper.demo.service.impl;

import com.paper.demo.entity.bo.Paper;
import com.paper.demo.entity.bo.User;
import com.paper.demo.mapper.PaperMapper;
import com.paper.demo.mapper.PersonalRecMapper;
import com.paper.demo.mapper.UserMapper;
import com.paper.demo.service.PersonalRecService;
import com.paper.demo.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiConsumer;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Service
public class PersonalRecServiceImpl implements PersonalRecService {

    @Autowired
    private PersonalRecMapper personalRecMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PaperMapper paperMapper;

    @Override
    public void updatePersonalRecIntoB(Map<Integer, Integer[]> user2paperRecMatrix) {
        // TODO Auto-generated method stub
        user2paperRecMatrix.forEach(new BiConsumer<Integer, Integer[]>() {

            public void accept(Integer userId, Integer[] recSongIds) {
                // TODO Auto-generated method stub
                personalRecMapper.deleteAByUserId(userId);
                //批量插入
                personalRecMapper.insertArrayIntoRecB(recSongIds, userId);

            }

        });
    }

    @Override
    public void updatePersonalRecIntoA(Map<Integer, Integer[]> user2paperRecMatrix) {
        // TODO Auto-generated method stub
        user2paperRecMatrix.forEach(new BiConsumer<Integer, Integer[]>() {

            public void accept(Integer userId, Integer[] recSongIds) {
                // TODO Auto-generated method stub
                personalRecMapper.deleteAByUserId(userId);
                //批量插入
                personalRecMapper.insertArrayIntoRecA(recSongIds, userId);

            }

        });
    }

    @Override
    public void initializePersonalRecList(HttpServletRequest request) {
        final User user = userMapper.selectByEmail((String) request.getSession().getAttribute("user"));
        List<Paper> initialRecListA;
        List<Paper> initialRecListB = new ArrayList<Paper>();
        //从最新论文中随机获取12，作为初始化列表
        initialRecListA = paperMapper.get12NewPaper();
        for (int i = 0; i < 48; i++) {
            int len = initialRecListA.size();
            Random random = new Random();
            int index = random.nextInt(len);
            if (i < 12) {
                initialRecListB.add(initialRecListA.get((index + 1) % len));
            }
            initialRecListA.remove(index);
        }
        if (Const.isFromA) {
            personalRecMapper.insertListIntoRecA(initialRecListA, user.getUserId());
        } else {
            personalRecMapper.insertListIntoRecB(initialRecListB, user.getUserId());
        }
    }

    @Override
    public List<Paper> getPersonalDailyRecWithCollectionFlag(HttpServletRequest request) {
        List<Paper> personalRecList = new ArrayList<Paper>();
        List<Paper> resultList = new ArrayList<>();
        if (StringUtils.isEmpty((String) request.getSession().getAttribute("user"))) {
            return resultList;
        }
        User user = userMapper.selectByEmail((String) request.getSession().getAttribute("user"));
        /* =============================================================== */
        personalRecList = selectPersonalRec(user);
        if (null == personalRecList) {
            return resultList;
        }
        for (int i = 0; i < personalRecList.size(); i++) {
            resultList.add(paperMapper.getPaperByPK(personalRecList.get(i).getPaperId()));
        }
        return resultList;

    }

    /**
     * 每天早上6点更新个性化推荐列表，从更新后的表中读取记录
     * 这里采用两张表交替的方式来实现：
     * （1）	6点之后就从另外一张表中读取记录
     * （2）	重新开始计算新的个性化推荐列表存放于原来的表中的
     *
     * @param user
     * @return
     */
    private List<Paper> selectPersonalRec(User user) {
        if (user == null) return null;
        List<Paper> personalRecList = new ArrayList<Paper>();
        if (Const.isFromA) {
            personalRecList = personalRecMapper.selectPersonalRecFromA(user);
        } else {
            personalRecList = personalRecMapper.selectPersonalRecFromB(user);
        }
        return personalRecList;
    }
}
