package com.ctgu.fmall.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.dto.CProductDTO;
import com.ctgu.fmall.entity.Category;
import com.ctgu.fmall.entity.Comment;
import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.service.CategoryService;
import com.ctgu.fmall.service.ProductService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/allinCate")
    @ApiOperation("获取所有目录以及目录下面的所有商品")
    public Result getAllPInC(){
        List<Category> clist = categoryService.list(null);
        List<CProductDTO> list= new ArrayList<>();
        for (Category c: clist) {
            QueryWrapper<Product> wrapper = new QueryWrapper<>();
            wrapper.eq("cid",c.getId());
            List<Product> plist= productService.list(wrapper);
            CProductDTO cProductDTO = new CProductDTO(c.getName(),plist);

            list.add(cProductDTO);
        }
        return ResultUtil.success(list);
    }

    @GetMapping("/top5")
    @ApiOperation("月销排名前5的商品")
    public Result listTop5(){
        QueryWrapper<Product> wrapper =new QueryWrapper<Product>();
        wrapper.orderByDesc("sale_num")
                .last("limit 5");
        List<Product> list=  productService.list(wrapper);
        return ResultUtil.success(list);
    }

    @GetMapping("/{pid]")
    @ApiOperation("通过商品编号获得商品详情")
    public Result getProductByid(@PathVariable int pid){
        QueryWrapper<Product> wrapper=new QueryWrapper<Product>();
        wrapper.eq("pid",pid);
        Product product = productService.getOne(wrapper);
        return ResultUtil.success(product);
    }

}

