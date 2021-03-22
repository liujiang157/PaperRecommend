package com.paper.demo.config;

/**
 * @author liujiang
 * @descrpition
 * @date 2021-03-22
 */

import com.paper.demo.entity.bo.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * 权限拦截AOP
 *
 * @author Administrator
 */
@Component
@Aspect
public class BeanHelper {

    private final Logger log = LoggerFactory.getLogger(BeanHelper.class);

    @Pointcut("within(com.paper.demo.controller..*) && !within(com.paper.demo.controller.LoginController)&&!within(com.paper.demo.controller.RegisterController)")
    public void pointCut() {
    }

    @Around("pointCut()")
    public Object trackInfo(ProceedingJoinPoint pjp) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String user = (String) request.getSession().getAttribute("user");
        if (user == null) {
            log.info("-------------没有登录-------------");
            return "redirect:/login";
        }
        return pjp.proceed();
    }


}
