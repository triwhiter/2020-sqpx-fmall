package com.ctgu.fmall.common;

import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: yanghao
 * @Date: 2020/8/1 19:00
 * @PackageName:com.ctgu.fmall.common
 * @Description: 未登录时返回json而不是跳转到登录页
 * @Version:V1.0
 */
@Component
public class MyAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(401);
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Result error = ResultUtil.error(ResultEnum.PERMISSION_DENIED.UNAUTHORIZED);
        out.write(new ObjectMapper().writeValueAsString(error));
        out.flush();
        out.close();
    }
}