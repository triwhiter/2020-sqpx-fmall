package com.ctgu.fmall.common.security;

import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 16:17
 * @PackageName:com.ctgu.fmall.common
 * @Description: TODO
 * @Version:V1.0
 */
@Component
public class MyLogoutSuccessHandler  implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest req, HttpServletResponse resp, Authentication authentication) throws IOException, ServletException {
        Result ok = ResultUtil.success("注销成功");
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(ok));
        out.flush();
        out.close();
    }
}
