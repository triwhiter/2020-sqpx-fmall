package com.ctgu.fmall.controller;


import com.ctgu.fmall.entity.Category;
import com.ctgu.fmall.service.CategoryService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/all")
    private Result getAllCategory(){
        List<Category> list= categoryService.list(null);
        return ResultUtil.success(list);
    }

}

