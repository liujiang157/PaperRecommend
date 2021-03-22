package com.example.demo2.service.impl;

import com.example.demo2.mapper.UserMapper;
import com.example.demo2.model.User;
import com.example.demo2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean findLogin(User user) {
        boolean isUserExisted = false;
        User result = userMapper.selectByUser(user);
        if (result != null) {
            isUserExisted = true;
            user = result;
        }
        return isUserExisted;
    }

    @Override
    public boolean isEmailExisted(String email) {
        boolean isEmailExisted = false;
        User result = userMapper.selectByEmail(email);
        if (result != null) {
            isEmailExisted = true;
        }
        return isEmailExisted;
    }

    @Override
    public boolean isNameExisted(String name) {
        boolean isNameExisted = false;
        User result = userMapper.selectByName(name);
        if (result != null) {
            isNameExisted = true;
        }
        return isNameExisted;
    }

    @Override
    public boolean insert(User u) {
        boolean isInsertSuccessful=false;
        int affectedRows=userMapper.insert(u);
        if(affectedRows>0) {
            isInsertSuccessful=true;
        }
        return isInsertSuccessful;
    }

    @Override
    public List<User> getAllRecords() {
        return userMapper.selectAll();
    }

    @Override
    public List<Integer> getAllUserIdRecords() {
        return  userMapper.selectAllUserId();
    }

    @Override
    public List<User> getAllUsers() {
        return userMapper.selectAll();
    }


    /**
     * 分页逻辑
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<User> list(Integer page, Integer size,String search) {
        int offset = size*(page-1);
        if(offset<0)
            offset=0;
        List<User> userList = new ArrayList<>();
        if(search==null)
            userList = userMapper.listByPage(offset,size);
        else
            userList = userMapper.SearchByPage(offset,size,search);
        return userList;
    }

    @Override
    public int getTotalCount(String search){
        if(search==null)
            return userMapper.selectCount();
        else
            return userMapper.selectCountByName(search);
    }

    @Override
    public boolean deleteUserByName(String username) {
        boolean isDeleteSuccessful=false;
        int affectedRows=userMapper.deleteUserByEmail(username);
        if(affectedRows>0) {
            isDeleteSuccessful=true;
        }
        return isDeleteSuccessful;
    }

    @Override
    public User selectUserById(Integer id) {
        return userMapper.selecUsertById(id);
    }

    @Override
    public boolean update(User user) {
        boolean isUpdatedSuccessful=false;
        int affectedRows=userMapper.update(user);
        if(affectedRows>0) {
            isUpdatedSuccessful=true;
        }
        return isUpdatedSuccessful;
    }

    @Override
    public boolean tooQuickly(HttpServletRequest request, int minutes) {
        //第一次操作
        if(request.getSession().getAttribute("lastTime")==null) {
            request.getSession().setAttribute("lastTime", new SimpleDateFormat("mm").format(new Date()));
            return false;
        }
        //第二次及其以上的操作
        String lastMinute=(String) request.getSession().getAttribute("lastTime");
        String curMinute=new SimpleDateFormat("mm").format(new Date());

        if( Math.abs(Integer.valueOf(curMinute)- Integer.valueOf(lastMinute)) <=minutes) {
            return true;
        }
        request.getSession().setAttribute("lastTime",curMinute);
        return false;
    }
}
