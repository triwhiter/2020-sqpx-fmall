package com.ctgu.fmall.controller;


import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.utils.CommonUtil;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public Result test(){
        return ResultUtil.success(CommonUtil.getCurrentUser());
    }

    @GetMapping("findInfoById/{id}")
    @ApiOperation("通过用户id查找用户信息")
    public Result getUserInfoById(@PathVariable("id") int id){
        Result userInfoById = userService.getUserInfoById(id);
        return userInfoById;
    }

}

