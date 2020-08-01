package com.ctgu.fmall.service.impl;

import com.ctgu.fmall.entity.Category;
import com.ctgu.fmall.mapper.CategoryMapper;
import com.ctgu.fmall.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

}
