package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.ResultEnum;
import com.ctgu.fmall.dto.AuthAdminDTO;
import com.ctgu.fmall.entity.Admin;
import com.ctgu.fmall.service.AdminService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.elasticsearch.rest.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/login")
    public Result login(@RequestBody @Valid AuthAdminDTO authAdminDTO) {

        QueryWrapper<Admin> wrapper = new QueryWrapper<>();
        wrapper.eq("phone_number",authAdminDTO.getUsername())
                .eq("password",authAdminDTO.getPassword());

       Admin admin=adminService.getOne(wrapper);
       if(admin==null){
           return ResultUtil.error(ResultEnum.LOGIN_FAILED);
       }
        return ResultUtil.success("登录成功",admin);
    }
}

