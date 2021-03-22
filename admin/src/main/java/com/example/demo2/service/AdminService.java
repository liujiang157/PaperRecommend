package com.example.demo2.service;

import com.example.demo2.model.Admin;

import javax.servlet.http.HttpServletRequest;

public interface AdminService {

    public boolean isHasPrivilege(HttpServletRequest request);

    Admin selectAdmin(Admin admin);
}
