package com.ctgu.fmall.controller;


import com.ctgu.fmall.dto.OrderDTO;
import com.ctgu.fmall.entity.OrderDetail;
import com.ctgu.fmall.entity.OrderList;
import com.ctgu.fmall.service.OrderDetailService;
import com.ctgu.fmall.service.OrderListService;
import com.ctgu.fmall.service.ShopCartService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    ShopCartService shopCartService;

    @GetMapping("/getOrderInfo/{uid}")
    @ApiOperation("获取用户订单")
    public Result getOrderListInfoByUid(@PathVariable("uid") int uid){
        return orderListService.getOrderListInfoByUid(uid);
    }

    @PostMapping("/")
    @ApiOperation("用户下单")
    @Transactional
    public Result add(@RequestBody @Valid OrderDTO orderDTO){
        OrderList orderList=new OrderList(orderDTO);
        try{
        orderListService.save(orderList);
        for(int i=0;i <orderDTO.getPids().size();i++){
            OrderDetail detail = new OrderDetail();
            detail.setNumber(orderDTO.getNums().get(i));
            detail.setPid(orderDTO.getPids().get(i));
            detail.setOid(orderList.getId());
            orderDetailService.save(detail);
            shopCartService.removeById(orderDTO.getPids().get(i));
        }
        return ResultUtil.success();
        }catch (Exception e){
            return ResultUtil.error(e.getMessage());
        }
    }

}

