package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.entity.Address;
import com.ctgu.fmall.entity.User;
import com.ctgu.fmall.service.AddressService;
import com.ctgu.fmall.utils.CommonUtil;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getAllAddress/{uid}")
    @ApiOperation("通过用户id，查找该用户的所有收货地址")
    public Result getAllAddress(@PathVariable("uid") int uid){
        return addressService.getAllAddress(uid);
    }

    @PostMapping("/addAddress")
    @ApiOperation("添加收货地址")
    public Result addAddressInfo(@RequestBody Address address){
        return addressService.addAddressInfo(address);
    }

    @PutMapping("/editAddress")
    @ApiOperation("修改收货地址")
    public Result editAddress(@RequestBody Address address){
        return addressService.editAddress(address);
    }

    @DeleteMapping("/del/{id}")
    @ApiOperation("更加地址Id删除地址")
    public Result delAddress(@PathVariable("id") int id){
        return addressService.delAddress(id);
    }

}
