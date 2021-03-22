package com.paper.demo.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
public interface TokenService {
    boolean checkToken(HttpServletRequest request);
}
