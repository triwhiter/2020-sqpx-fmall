package com.ctgu.fmall.config;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.MyAuthenticationEntryPoint;
import com.ctgu.fmall.common.MyUsernamePasswordAuthenticationFilter;
import com.ctgu.fmall.common.ResultEnum;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.service.impl.UserDetailsServiceImpl;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;

@EnableWebSecurity
@Slf4j
public class MySecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    UserService userService;


    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Autowired
    MyAuthenticationEntryPoint myAuthenticationEntryPoint;


/*    @Autowired
    MyAuthenticationProvider myAuthenticationProvider;*/

    //    认证策略
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
//                .mvcMatchers("/api/*").hasRole("ADMIN")
//                .mvcMatchers("/api/user/*").access("hasRole('ADMIN')")
                .mvcMatchers("/user/**").hasRole("USER")
                .anyRequest().permitAll();
//                .authenticated();

        // 开启自动配置的登录功能，如果没有权限，就会跳到登录页面！
        http.formLogin()
                .loginProcessingUrl("/api/login")
                .loginPage("/login");

        //退出时返回Json数据
        http.logout()
                .logoutUrl("/logout").deleteCookies()
                .logoutSuccessHandler((req, resp, authentication) -> {
                    Result ok = ResultUtil.success("注销成功");
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(ok));
                    out.flush();
                    out.close();
                });

        //使用Json返回异常数据
        http.exceptionHandling()
                .accessDeniedHandler((req, resp, e) -> {
                    Result error = ResultUtil.error(ResultEnum.PERMISSION_DENIED);
                    resp.setStatus(403);
                    resp.setContentType("application/json;charset=utf-8");
                    PrintWriter out = resp.getWriter();
                    out.write(new ObjectMapper().writeValueAsString(error));
                    out.flush();
                    out.close();
                })//未登录的返回Json数据
                .authenticationEntryPoint(myAuthenticationEntryPoint);

/*        http.authorizeRequests().antMatchers("/api/user/").denyAll();
        http.antMatcher("/api/swagger-ui.html/").anonymous();*/
       /*
        //拦截器
        log.warn("是否有权限：" + http.authorizeRequests().anyRequest().hasAuthority("ADMIN"));*/
        // 防止跨站脚本攻击
        http.csrf().disable();
        // 开启记住我功能
        http.rememberMe();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    MyUsernamePasswordAuthenticationFilter myAuthenticationFilter() throws Exception {
        MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
        filter.setAuthenticationManager(authenticationManagerBean());

        /**
         * 认证成功的处理逻辑
         */
        filter.setAuthenticationSuccessHandler((req, resp, authentication) -> {
            try {
                log.info(authentication.getName());
                QueryWrapper<User> wrapper = new QueryWrapper<>();
                wrapper.eq("email", authentication.getName());
                User user = userService.getOne(wrapper);
                log.warn(String.valueOf((authentication.getPrincipal().getClass().getDeclaredField("username"))));
                Result ok = ResultUtil.success("登录成功", user);
                resp.setContentType("application/json;charset=utf-8");
                PrintWriter out = resp.getWriter();
                out.write(new ObjectMapper().writeValueAsString(ok));
                out.flush();
                out.close();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        });

        /**
         * 认证失败的处理逻辑
         */
        filter.setAuthenticationFailureHandler((req, resp, e) -> {
            Result error = ResultUtil.error(ResultEnum.LOGIN_FAILED);
            resp.setContentType("application/json;charset=utf-8");
            PrintWriter out = resp.getWriter();
            out.write(new ObjectMapper().writeValueAsString(error));
            out.flush();
            out.close();
        });

        return filter;
    }

    /**
     * 自定义授权
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*使用自定义的UserDetailesService*/
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());

        /*使用自定义的authenticationProvider认证逻辑*/
//        auth.authenticationProvider(myAuthenticationProvider);;
    }

    /**
     * 忽略拦截url或静态资源文件夹 - web.ignoring(): 会直接过滤该url - 将不会经过Spring Security过滤器链
     * http.permitAll(): 不会绕开springsecurity验证，相当于是允许该路径通过
     *
     * @param web
     */
    @Override
    public void configure(WebSecurity web) {
        //放行swagger
        web.ignoring().antMatchers(HttpMethod.GET,
                "/v2/api-docs",
                "/swagger-resources",
                "/swagger-resources/**",
                "/configuration/ui",
                "/configuration/security",
                "/swagger-ui.html/**",
                "/webjars/**");
    }


}



