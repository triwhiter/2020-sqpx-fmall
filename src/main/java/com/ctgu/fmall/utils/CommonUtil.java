package com.ctgu.fmall.utils;

import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.mapper.UserMapper;
import com.ctgu.fmall.service.UserService;
import com.sun.media.jfxmedia.logging.Logger;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @Auther: yanghao
 * @Date: 2020/8/2 20:02
 * @PackageName:com.ctgu.fmall.utils
 * @Description: 这是全局公共的工具类
 * @Version:V1.0
 */
@Slf4j
@Component
public class CommonUtil {

   private static UserService userService;

    @Autowired
    public  void setUserService( UserService userService){
        CommonUtil.userService=userService;
    }

    /**
     * 获取当前登录的用户信息
     * @return
     */
    public  static User  getCurrentUser(){
        /**
         SecurityContextHolder.getContext()获取安全上下文对象，就是那个保存在 ThreadLocal 里面的安全上下文对象
         总是不为null(如果不存在，则创建一个authentication属性为null的empty安全上下文对象)
         获取当前认证了的 principal(当事人),或者 request token (令牌)
         如果没有认证，会是 null,该例子是认证之后的情况
         */
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //有登陆用户就返回登录用户，没有就返回null
        if (authentication != null) {
            if (authentication instanceof AnonymousAuthenticationToken) {
                return null;
            }

            if (authentication instanceof Authentication) {

                Integer uid=Integer.valueOf(authentication.getName());

                log.info("当前用户的ID：{}"+uid);

                User user=userService.getById(uid);
                log.info("当前认证用户："+user);
                return  user;
            }
        }
        log.info("当前用户未登录");
        return  null;
    }
}
