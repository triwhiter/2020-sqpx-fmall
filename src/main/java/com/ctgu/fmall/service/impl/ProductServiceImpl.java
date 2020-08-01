package com.ctgu.fmall.service.impl;

import com.ctgu.fmall.entity.Product;
import com.ctgu.fmall.mapper.ProductMapper;
import com.ctgu.fmall.service.ProductService;
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
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}
