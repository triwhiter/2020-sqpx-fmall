package com.ctgu.fmall.controller;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.eums.ResultEnum;
import com.ctgu.fmall.dto.AuthDTO;
import com.ctgu.fmall.entity.Admin;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.AdminService;
import com.ctgu.fmall.service.SmsService;
import com.ctgu.fmall.service.UserService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

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
    UserService userService;

    @Autowired
    SmsService smsService;

    @Autowired
    AdminService adminService;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${user.default.avatar}")
    private String avatar;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid AuthDTO authDTO){
        log.info("登录信息："+authDTO);
        QueryWrapper<User> userQueryWrapper = new QueryWrapper();
        userQueryWrapper.eq("phone_number",authDTO.getUsername());

        User user=userService.getOne(userQueryWrapper);

        QueryWrapper<Admin> adminQueryWrapper = new QueryWrapper<>();
        adminQueryWrapper.eq("phone_number",authDTO.getUsername());

        Admin admin=adminService.getOne(adminQueryWrapper);

        if(user==null ){
            if(admin==null){
                return ResultUtil.error(ResultEnum.LOGIN_FAILED);
            }
            if(!passwordEncoder.matches(authDTO.getPassword(),admin.getPassword())){
                return ResultUtil.error(ResultEnum.LOGIN_FAILED);
            }
            return ResultUtil.success("登录成功",admin);
        }
        if(!passwordEncoder.matches(authDTO.getPassword(),user.getPassword())){
            return ResultUtil.error(ResultEnum.LOGIN_FAILED);
        }
        return ResultUtil.success("登录成功",user);
    }

    @PostMapping("/register")
    public Result register(@RequestBody AuthDTO authDTO){
        log.info("注册信息："+authDTO);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("nick_name",authDTO.getUsername());
        if(userService.getOne(wrapper)!=null){
            return ResultUtil.error("昵称已被占用");
        }
        User user=new User();
        user.setAvatar(avatar);
        user.setNickName(authDTO.getUsername());
        user.setEmail(authDTO.getMail());
        user.setPhoneNumber(authDTO.getPhone());
        user.setSex(authDTO.getSex());
        user.setUserName(authDTO.getName());
        user.setPassword(passwordEncoder.encode(authDTO.getPassword()));
        try {
            userService.save(user);
        }catch (Exception e){
            log.error(e.getMessage());
            return  ResultUtil.error("注册信息有误");
        }
        return ResultUtil.success("注册成功");
    }


    @PostMapping("/verifyCode")
    public Result verifyCode(@RequestBody String phone){
//        测试用
        smsService.send(phone);
        return ResultUtil.success("验证码发送成功");

//       if(smsService.send(phone)){
//            return ResultUtil.success("验证码发送成功");
//        }
//        return ResultUtil.error("验证码发送太频繁，请稍后再试");
    }

    @PostMapping("/confirmCode")
    public Result confirmCode(@RequestBody AuthDTO authDTO){
        log.info("手机号：{}，验证码：{}",authDTO.getPhone(),authDTO.getCheckNum());
        String dbCode=stringRedisTemplate.opsForValue().get(authDTO.getPhone());
        log.info("Redis验证码{}",dbCode);
        if(dbCode==null || !dbCode.equals(authDTO.getCheckNum())){
           return ResultUtil.error("验证码有误");
        }
        return ResultUtil.success("验证成功");
    }

}
