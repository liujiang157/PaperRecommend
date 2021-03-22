package com.example.demo2.service;

import com.example.demo2.model.User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface UserService {

    /**
     * 查询用户名和密码是否匹配
     * @param user
     * @return匹配返回true
     */
    public boolean findLogin(User user);

    /**
     * 检验指定的邮箱帐号是否存在
     * @param email
     * 邮箱帐号
     * @return
     * 若存在返回true
     */
    public boolean isEmailExisted(String email);

    /**
     * 检验用户名重复
     * @param name
     * @return
     */
    public boolean isNameExisted(String name);

    /**
     * 向user用户表中插入新的记录
     * @param u
     * 用户User对象
     * @return
     * 若插入成功返回true
     */
    public boolean insert(User u);

    /**
     * 获取所有的用户记录
     * @return
     * 若没有，则返回null
     */
    public List<User> getAllRecords();

    /**
     * 获取所有的用户Id记录
     * @return
     * 若没有，则返回null
     */
    public List<Integer> getAllUserIdRecords();


    List<User> getAllUsers();

    List<User> list(Integer page, Integer size,String search);

    int getTotalCount(String search);

    boolean deleteUserByName(String username);

    User selectUserById(Integer id);

    boolean update(User u);

    boolean tooQuickly(HttpServletRequest request, int i);
}
