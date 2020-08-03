package com.ctgu.fmall.controller;


import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-08-03
 */
@RestController
@RequestMapping("/products")
public class ProductController {
    @GetMapping("/")
    public Result getProductsByPage(){
     return ResultUtil.success();
    }
}

