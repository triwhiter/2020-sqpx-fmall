package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.service.ProductService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.ProductVO;
import com.ctgu.fmall.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zhen
 * @since 2020-08-03
 */

@RestController
@Slf4j
@RequestMapping("/products")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping("/{pageNo}/{pageSize}")
    public Result getProductsByPage(@PathVariable Integer pageNo, @PathVariable("pageSize") Integer pageSize){
        IPage page = new Page<Product>(pageNo,pageSize);
        QueryWrapper<Product> wrapper= new QueryWrapper<>();
        IPage oldPage=productService.page(page,wrapper);
        List<Product>productList=oldPage.getRecords();
        List<ProductVO>productVOS=new ArrayList<>();
        if(productList.size()==0){
            ResultUtil.success(productVOS);
        }
        for(Product p:productList){
            log.info(p.toString());
        }
        return ResultUtil.success();
    }
}

