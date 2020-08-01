package com.ctgu.fmall.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.ResultEnum;
import com.ctgu.fmall.dto.AuthDTO;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.mapper.UserMapper;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.sql.Wrapper;

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
    @Autowired
    UserMapper userMapper;
    @PostMapping("/login")
    public Result login(@RequestBody @Valid AuthDTO authDTO){
        QueryWrapper<User> wrapper = new QueryWrapper();
        wrapper.eq("nick_name",authDTO.getUsername())
        .eq("password",authDTO.getPassword());
        User user=userMapper.selectOne(wrapper);
        if(user==null){
            return ResultUtil.success(ResultEnum.LOGIN_FAILED);
        }
        log.info("请求登录");
        return ResultUtil.success("登录成功",user);
    }

    @PostMapping("/register")
    public Result register(){
        return ResultUtil.success("注册成功");
    }

}
