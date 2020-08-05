package com.ctgu.fmall.common.security;

import com.ctgu.fmall.entity.Admin;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.AdminService;
import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 16:11
 * @PackageName:com.ctgu.fmall.common.exception
 * @Description: TODO
 * @Version:V1.0
 */
@Component
@Slf4j
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    UserService userService;
    @Autowired
    AdminService adminService;
    @Override
    public void onAuthenticationSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        log.info("权限信息："+authentication);
        Result ok;
        if(authentication.getAuthorities().equals("ROLE_USER")){
                User user = userService.getById(authentication.getName());
                ok = ResultUtil.success("登录成功", user);
                log.info("当前登录的普通用户："+user);
            }
            else {
                Admin admin=adminService.getById(authentication.getName());
                ok = ResultUtil.success("登录成功", admin);
                log.info("当前登录的管理员："+admin);
            }
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(ok));
            out.flush();
            out.close();
    }
}
