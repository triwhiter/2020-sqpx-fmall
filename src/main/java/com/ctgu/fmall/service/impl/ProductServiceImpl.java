package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.eums.ResultEnum;
import com.ctgu.fmall.entity.*;
import com.ctgu.fmall.mapper.*;
import com.ctgu.fmall.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhen
 * @since 2020-08-05
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductImageMapper productImageMapper;

    @Autowired
    private OrderDetailMapper orderDetailMapper;

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public Result delProduct(int id) {
        QueryWrapper<ProductImage> productImageQueryWrapper = new QueryWrapper<>();
        productImageQueryWrapper.eq("pid",id);
        int deleteProductImage = productImageMapper.delete(productImageQueryWrapper);

        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("pid",id);
        int deleteComment = commentMapper.delete(commentQueryWrapper);

        QueryWrapper<ShopCart> shopCartQueryWrapper = new QueryWrapper<>();
        shopCartQueryWrapper.eq("pid",id);
        int deleteShopCart = shopCartMapper.delete(shopCartQueryWrapper);

        QueryWrapper<OrderDetail> orderDetailQueryWrapper = new QueryWrapper<>();
        orderDetailQueryWrapper.eq("pid",id);
        int deleteOrderDetailMapper = orderDetailMapper.delete(orderDetailQueryWrapper);

        int delProduct = productMapper.delProduct(id);

        if (delProduct != 0){
            return ResultUtil.success(ResultEnum.SUCCESS);
        }else {
            return ResultUtil.error(ResultEnum.FAIL);
        }

    }
}
