package com.ctgu.fmall.controller;


import com.ctgu.fmall.service.ShopCartService;
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
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/shopCart")
public class ShopCartController {

    @Autowired
    private ShopCartService shopCartService;

    @GetMapping("findShopCart/{uid}")
    @ApiOperation("通过用户id，查找购物车信息")
    public Result getShopCartInfo(@PathVariable("uid") int uid){
        return shopCartService.getShopCartInfo(uid);
    }

    @DeleteMapping("/del/{uid}/{pid}")
    @ApiOperation("根据uid,pid删除购物车")
    public Result delShopCartById(@PathVariable("uid") int uid,
                                  @PathVariable("pid") int pid){
        return shopCartService.delShopCartById(uid,pid);
    }

    @DeleteMapping("/del/{uid}")
    @ApiOperation("清空该用户所有购物车")
    public Result delAllShopCart(@PathVariable("uid") int uid){
        return shopCartService.delAllShopCart(uid);
    }
}

