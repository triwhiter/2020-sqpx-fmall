package com.ctgu.fmall.controller;


import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.utils.CommonUtil;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getUserInfo")
    @ApiOperation("获取当前用户")
    public Result getUserInfo(){
        CommonUtil.getCurrentUser();
        return ResultUtil.success();
    }

    @PutMapping("/editUser")
    @ApiOperation("更新用户信息")
    public Result editUser(@RequestBody User user){
        return userService.editUser(user);
    }

}
