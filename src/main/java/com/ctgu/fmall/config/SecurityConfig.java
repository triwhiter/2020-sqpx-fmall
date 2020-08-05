package com.ctgu.fmall.config;

import com.ctgu.fmall.common.security.*;
import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.service.impl.AdminDetailsServiceImpl;
import com.ctgu.fmall.service.impl.UserDetailsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Configuration
    @Order(1)
    public static class App1ConfigurationAdapter extends WebSecurityConfigurerAdapter {

        public App1ConfigurationAdapter() {
            super();
        }

        @Autowired
        UserService userService;


        @Autowired
        UserDetailsServiceImpl userDetailsService;

        @Autowired
        MyAuthenticationEntryPoint myAuthenticationEntryPoint;


        @Autowired
        MyUserAuthenticationSuccessHandler myUserAuthenticationSuccessHandler;


        @Autowired
        MyAuthenticationFailureHandler myAuthenticationFailureHandler;

        @Autowired
        MyLogoutSuccessHandler myLogoutSuccessHandler;

        @Autowired
        MyAccessDeniedHandler myAccessDeniedHandler;

/*        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }*/


        @Autowired
        @Qualifier("passwordEncoder")
        PasswordEncoder passwordEncoder;


        //实现单点登录
        @Bean
        HttpSessionEventPublisher httpSessionEventPublisher() {
            return new HttpSessionEventPublisher();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowCredentials(true);
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            configuration.setMaxAge((long) 3600);
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }


        @Bean
        MyUsernamePasswordAuthenticationFilter myAuthenticationFilter() throws Exception {
            MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
            filter.setAuthenticationManager(authenticationManagerBean());
            /**
             * 认证成功的处理逻辑
             */
            filter.setAuthenticationSuccessHandler(myUserAuthenticationSuccessHandler);

            /**
             * 认证失败的处理逻辑
             */
            filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);

            return filter;
        }

        //    认证策略
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //authorizeRequests:声明配置是权限配置
            //antMatchers：拦截路径
            //permitAll：任何权限都可以访问
            //anyRequest：任何请求
            //authenticated：认证后才能访问
            //and().csrf().disable()：固定写法，表示csrf拦截失效

            http.authorizeRequests()
//        .mvcMatchers("/user/**","/shopCart/**","/orderList/**","/address/**")
//                .hasAnyRole("USER","ADMIN")
////                .mvcMatchers("/api/user/*").access("hasRole('ADMIN')")
////                .mvcMatchers("/user/**").hasAnyRole("ADMIN","ROOT")
////                .mvcMatchers("/user/**").denyAll()
                    .mvcMatchers("/**").permitAll().anyRequest().authenticated()
                    .anyRequest().permitAll();
//                .authenticated();
            //开启跨域
/*        http.cors();

        // 开启自动配置的登录功能，如果没有权限，就会跳到登录页面！
        http.csrf().disable();*/
            http.authorizeRequests()
                    .anyRequest()
                    .authenticated()//所有请求都需要登录认证才能访问
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/api/login")
                    .loginPage("/login")
                    .permitAll()
                    /*  .and()
                      .httpBasic()//开启httpbasic认证*/
                    .and()
                    .cors()
                    .configurationSource(corsConfigurationSource())
                    .and().csrf().disable()
                    .sessionManagement()//配置单点登录
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true);


            //退出时返回Json数据
            http.logout()
                    .logoutUrl("/logout").deleteCookies()
                    .logoutSuccessHandler(myLogoutSuccessHandler);

            //使用Json返回异常数据
            http.exceptionHandling()
                    .accessDeniedHandler(myAccessDeniedHandler)//未登录的返回Json数据
                    .authenticationEntryPoint(myAuthenticationEntryPoint);

/*        http.authorizeRequests().antMatchers("/api/user/").denyAll();
        http.antMatcher("/api/swagger-ui.html/").anonymous();*/
       /*
        //拦截器
        log.warn("是否有权限：" + http.authorizeRequests().anyRequest().hasAuthority("ADMIN"));*/
            // 开启记住我功能
            http.rememberMe();

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
            auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);

            /*使用自定义的authenticationProvider认证逻辑*/
//        auth.authenticationProvider(myAuthenticationProvider);;
        }

    }

    @Configuration
    @Order(2)
    public static class App2ConfigurationAdapter extends WebSecurityConfigurerAdapter {
        @Autowired
        UserService userService;

        @Autowired
        AdminDetailsServiceImpl adminDetailsService;

        @Autowired
        MyAuthenticationEntryPoint myAuthenticationEntryPoint;

        @Autowired
        MyUserAuthenticationSuccessHandler myUserAuthenticationSuccessHandler;

        @Autowired
        MyAuthenticationFailureHandler myAuthenticationFailureHandler;

        @Autowired
        MyLogoutSuccessHandler myLogoutSuccessHandler;

        @Autowired
        MyAccessDeniedHandler myAccessDeniedHandler;

        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

        @Autowired
        PasswordEncoder passwordEncoder;


        //实现单点登录
        @Bean
        HttpSessionEventPublisher httpSessionEventPublisher() {
            return new HttpSessionEventPublisher();
        }

        @Bean
        CorsConfigurationSource corsConfigurationSource() {
            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowCredentials(true);
            configuration.setAllowedOrigins(Arrays.asList("*"));
            configuration.setAllowedMethods(Arrays.asList("*"));
            configuration.setAllowedHeaders(Arrays.asList("*"));
            configuration.setMaxAge((long) 3600);
            source.registerCorsConfiguration("/**", configuration);
            return source;
        }

        @Bean
        MyUsernamePasswordAuthenticationFilter myAuthenticationFilter() throws Exception {
            MyUsernamePasswordAuthenticationFilter filter = new MyUsernamePasswordAuthenticationFilter();
            filter.setAuthenticationManager(authenticationManagerBean());
            //认证成功的处理逻辑
            filter.setAuthenticationSuccessHandler(myUserAuthenticationSuccessHandler);
             //认证失败的处理逻辑
            filter.setAuthenticationFailureHandler(myAuthenticationFailureHandler);
            return filter;
        }
        //认证策略
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            //authorizeRequests:声明配置是权限配置
            //antMatchers：拦截路径
            //permitAll：任何权限都可以访问
            //anyRequest：任何请求
            //authenticated：认证后才能访问
            //and().csrf().disable()：固定写法，表示csrf拦截失效
            http.authorizeRequests()
        .mvcMatchers("/user/**","/admin/**","/shopCart/**","/orderList/**","/address/**")
                .hasAnyRole("USER","ADMIN","SUPER_ADMIN")
////                .mvcMatchers("/api/user/*").access("hasRole('ADMIN')")
////                .mvcMatchers("/user/**").hasAnyRole("ADMIN","ROOT")
////                .mvcMatchers("/user/**").denyAll()
                    .mvcMatchers("/**").permitAll().anyRequest().authenticated()
                    .anyRequest().permitAll();
//                .authenticated();
            //开启跨域
/*        http.cors();

        // 开启自动配置的登录功能，如果没有权限，就会跳到登录页面！
        http.csrf().disable();*/
            http.authorizeRequests()
                    .anyRequest()
                    .authenticated()//所有请求都需要登录认证才能访问
                    .and()
                    .formLogin()
                    .loginProcessingUrl("/api/admin/login")
                    .loginPage("/login")
                    .permitAll()
                    /*  .and()
                      .httpBasic()//开启httpbasic认证*/
                    .and()
                    .cors()
                    .configurationSource(corsConfigurationSource())
                    .and().csrf().disable()
                    .sessionManagement()//配置单点登录
                    .maximumSessions(1)
                    .maxSessionsPreventsLogin(true);


            //退出时返回Json数据
            http.logout()
                    .logoutUrl("/logout").deleteCookies()
                    .logoutSuccessHandler(myLogoutSuccessHandler);

            //使用Json返回异常数据
            http.exceptionHandling()
                    .accessDeniedHandler(myAccessDeniedHandler)//未登录的返回Json数据
                    .authenticationEntryPoint(myAuthenticationEntryPoint);

/*        http.authorizeRequests().antMatchers("/api/user/").denyAll();
        http.antMatcher("/api/swagger-ui.html/").anonymous();*/
       /*
        //拦截器
        log.warn("是否有权限：" + http.authorizeRequests().anyRequest().hasAuthority("ADMIN"));*/
            // 开启记住我功能
            http.rememberMe();

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
            auth.userDetailsService(adminDetailsService).passwordEncoder(passwordEncoder);
            /*使用自定义的authenticationProvider认证逻辑*/
//        auth.authenticationProvider(myAuthenticationProvider);;
        }
    }

    @Configuration
    @Order(3)
    public static class guestSecurityConfigurationAdapter extends WebSecurityConfigurerAdapter {

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests()
                    .anyRequest().permitAll();
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

            web.ignoring()
                    .antMatchers("/css/**","/js/**","/index.html","/img/**","/fonts/**","/favicon.ico");
        }
    }
}