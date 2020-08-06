package com.ctgu.fmall.service;

import com.ctgu.fmall.entity.Product;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ctgu.fmall.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhen
 * @since 2020-08-05
 */
public interface ProductService extends IService<Product> {

    Result delProduct(int id);

    Result delProductById(int id);

    Result getRemoveProduct(int page, int size);
}
