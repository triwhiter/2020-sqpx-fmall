package com.ctgu.fmall.common.security;

import com.ctgu.fmall.common.eums.ResultEnum;
import com.ctgu.fmall.utils.ResultUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Auther: yanghao
 * @Date: 2020/8/5 16:19
 * @PackageName:com.ctgu.fmall.common
 * @Description: TODO
 * @Version:V1.0
 */
@Component
public class MyAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest req, HttpServletResponse resp, AccessDeniedException e) throws IOException, ServletException {
        resp.setStatus(403);
        resp.setContentType("application/json;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.write(new ObjectMapper().writeValueAsString(ResultUtil.error(ResultEnum.PERMISSION_DENIED)));
        out.flush();
        out.close();
    }
}
