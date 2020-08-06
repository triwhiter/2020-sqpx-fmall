package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.dto.OrderDTO;
import com.ctgu.fmall.entity.*;
import com.ctgu.fmall.service.OrderDetailService;
import com.ctgu.fmall.service.OrderListService;
import com.ctgu.fmall.service.ProductService;
import com.ctgu.fmall.service.ShopCartService;
import com.ctgu.fmall.utils.CommonUtil;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@Slf4j
@RequestMapping("/orderList")
public class OrderListController {

    @Autowired
    private OrderListService orderListService;

    @Autowired
    OrderDetailService orderDetailService;

    @Autowired
    ProductService productService;

    @Autowired
    ShopCartService shopCartService;

    @GetMapping("/getOrderInfo/{uid}")
    @ApiOperation("获取用户订单")
    public Result getOrderListInfoByUid(@PathVariable("uid") int uid){
        return orderListService.getOrderListInfoByUid(uid);
    }

    @GetMapping("getOrderInfoPage/{uid}/{page}/{size}")
    @ApiOperation("获取当前用户订单，实现分页")
    public Result getOrderInfoPage(@PathVariable("uid") int uid,
                                   @PathVariable("page") int page,
                                   @PathVariable("size") int size){
        return orderListService.getOrderInfoPage(uid,page,size);
    }

    @GetMapping("/getAllOrderInfo/{page}/{size}")
    @ApiOperation("获取所有订单，实现分页")
    public Result getAllOrderInfo(@PathVariable("page") int page,
                                  @PathVariable("size") int size){
        return orderListService.getAllOrderInfo(page,size);
    }

    @DeleteMapping("/delOrder/{id}")
    @ApiOperation("根据订单id，删除订单")
    public Result delOrder(@PathVariable("id") int id){
        return orderListService.delOrder(id);
    }

    @PostMapping("/addOrder")
    @ApiOperation("添加订单")
    public Result addOrder(@RequestBody OrderList orderList){
        return orderListService.addOrder(orderList);
    }

    @PutMapping("/editOrder")
    @ApiOperation("修改订单")
    public Result editOrder(@RequestBody OrderList orderList){
        return orderListService.editOrder(orderList);
    }

    @PostMapping("/")
    @ApiOperation("用户下单")
    @Transactional
    public Result add(@RequestBody @Valid OrderDTO orderDTO){
        OrderList orderList=new OrderList(orderDTO);
        User user= CommonUtil.getCurrentUser();
        try{
        orderListService.save(orderList);
        for(int i=0;i <orderDTO.getPids().size();i++){
            OrderDetail detail = new OrderDetail();
            detail.setNumber(orderDTO.getNums().get(i));
            detail.setPid(orderDTO.getPids().get(i));
            detail.setOid(orderList.getId());
            orderDetailService.save(detail);
            QueryWrapper<ShopCart> wrapper = new QueryWrapper<>();
            wrapper.eq("pid",orderDTO.getPids().get(i));
            wrapper.eq("uid",user.getId());
            Product product=productService.getById(orderDTO.getPids().get(i));
            product.setStock(product.getStock()-orderDTO.getNums().get(i));
            productService.updateById(product);
            shopCartService.remove(wrapper);
        }
           return ResultUtil.success("订单已保存，请尽快付款");
        }catch (Exception e){
            log.error(e.getMessage());
            return ResultUtil.error("商品库存不足");
        }
    }

    @GetMapping("/getProductInfoById/{id}")
    @ApiOperation("根据订单id，返回改订单的所有购买的所有商品")
    public Result getProductInfoById(@PathVariable("id") int id){
        return orderListService.getProductInfoById(id);
    }

    @PutMapping("/updateStatusById/{id}")
    @ApiOperation("根据id，更新状态")
    public Result updateStatusById(@PathVariable("id") int id){
        return orderListService.updateStatusById(id);
    }

    @GetMapping("/total")
    @ApiOperation("获取订单总数量")
    public Result getTotalOrderList(){
        return ResultUtil.success(orderListService.list(null).size());
    }
}

