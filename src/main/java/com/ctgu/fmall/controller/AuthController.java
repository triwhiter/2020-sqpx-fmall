package com.ctgu.fmall.controller;

import com.ctgu.fmall.utils.ResultFactory;
import com.ctgu.fmall.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: yanghao
 * @Date: 2020/8/1 10:51
 * @PackageName:com.ctgu.fmall.controller
 * @Description: 登录注册
 * @Version:V1.0
 */
@RestController
@Slf4j
public class AuthController {
    @PostMapping("/login")
    public Result login(){
        log.info("请求登录");
        return ResultFactory.buildSuccessResult("登录成功");
    }

    @PostMapping("/register")
    public Result register(){
        return ResultFactory.buildSuccessResult("注册成功");
    