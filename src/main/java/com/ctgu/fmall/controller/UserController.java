package com.ctgu.fmall.controller;


import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import sun.rmi.runtime.Log;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("")
    public Result test(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("获取当前认证用户："+authentication);
        return ResultUtil.success(authentication);
    }

    @GetMapping("findInfo/{id}")
    @ApiOperation("获取个人信息")
    public Result getUserInfoById(@PathVariable("id") int id){
        return userService.getUserInfoById(id);
    }
}

