package com.ctgu.fmall.controller;


import com.ctgu.fmall.entity.OrderList;
import com.ctgu.fmall.service.OrderListService;
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
@RequestMapping("/orderList")
public class OrderListController {

    @Autowired
    private OrderListService orderListService;

    @GetMapping("/getOrderInfo/{uid}")
    @ApiOperation("获取用户订单")
    public Result getOrderListInfoByUid(@PathVariable("uid") int uid){
        return orderListService.getOrderListInfoByUid(uid);
    }
}

