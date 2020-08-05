package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.eums.ResultEnum;
import com.ctgu.fmall.dto.AuthAdminDTO;
import com.ctgu.fmall.entity.Admin;
import com.ctgu.fmall.service.AdminService;
import com.ctgu.fmall.utils.CommonUtil;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public Result login(@RequestBody @Valid AuthAdminDTO authAdminDTO) {

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number",authAdminDTO.getUsername());
       Admin admin=adminService.getOne(wrapper);
        if(admin==null || passwordEncoder.matches(authAdminDTO.getPassword(),admin.getPassword())){
            return ResultUtil.error(ResultEnum.LOGIN_FAILED);
        }
        return ResultUtil.success("登录成功",admin);
    }

    @GetMapping("/AdminInfo")
    public Result getCurrentAdmin(){
        return ResultUtil.success(CommonUtil.getCurrentAdmin());
    }

    @GetMapping("")
    public String test(){
        return "你好管理员";
    }
}

