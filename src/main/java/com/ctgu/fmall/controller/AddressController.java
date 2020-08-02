package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.entity.Address;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.AddressService;
import com.ctgu.fmall.utils.CommonUtil;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@RestController
@RequestMapping("/address")
public class AddressController {
    @Autowired
    AddressService addressService;

    /**
     * 获取当前用户的收货地址
     * @return
     */
    @GetMapping("/")
    public Result getAddresses(){
        User user= CommonUtil.getCurrentUser();
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.eq("uid",user.getId());
        List<Address> addresses=addressService.list(wrapper);
        return ResultUtil.success(addresses);
    }
}

