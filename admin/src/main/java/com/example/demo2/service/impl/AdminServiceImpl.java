package com.example.demo2.service.impl;

import com.example.demo2.mapper.AdminMapper;
import com.example.demo2.model.Admin;
import com.example.demo2.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public boolean isHasPrivilege(HttpServletRequest request) {
        boolean isHasPrivilege=false;
        if(request.getSession().getAttribute("admin")!=null)
            isHasPrivilege = true;
        return isHasPrivilege;
    }

    @Override
    public Admin selectAdmin(Admin admin) {
        return adminMapper.selectAdmin(admin);
    }
}
