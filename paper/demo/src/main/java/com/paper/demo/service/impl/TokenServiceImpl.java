package com.paper.demo.service.impl;

import com.paper.demo.service.TokenService;
import com.paper.demo.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-20
 */
@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    RedisUtil redisUtil;

    @Override
    public boolean checkToken(HttpServletRequest request) {
        String token = request.getHeader("referer");
        String userEmail = (String) request.getSession().getAttribute("user");
        if (StringUtils.isEmpty(userEmail)) {
            return true;
        }
        if (!redisUtil.exists(userEmail + token)) {
            redisUtil.set(userEmail + token, 1);
            return true;
        }
        return false;
    }
}
