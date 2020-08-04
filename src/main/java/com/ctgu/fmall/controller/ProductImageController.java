package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.entity.ProductImage;
import com.ctgu.fmall.service.ProductImageService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@RestController
@RequestMapping("/productImage")
public class ProductImageController {

    @Autowired
    private ProductImageService productImageService;

    @GetMapping("/{pid}")
    public Result getImgByPid(@PathVariable int pid){
        QueryWrapper<ProductImage> wrapper = new QueryWrapper<>();
        wrapper.eq("pid",pid);
        List<ProductImage> productImages =productImageService.list(wrapper);
        return ResultUtil.success(productImages);
    }

}

