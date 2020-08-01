package com.ctgu.fmall.common;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring security 登录失败后返回一串json
 */
@Component("MyAuthenticationFailureHandler")
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    logger.info("登录失败");
    response.setStatus(403); // 403 普通用户访问管理员页面
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().append("{\"code\":1,\"msg\":\"登录失败!\",\"data\":\"failed\"}");
  }
}