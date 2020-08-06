package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Override
    public Result delProductById(int id) {
        boolean IsRemove = this.removeById(id);
        if (IsRemove != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result getRemoveProduct(int page, int size) {
        List<Map> productList = productMapper.getRemoveProduct();
        int totalPages = productList.size() / size;
        if (productList.size() % size != 0){
            totalPages = totalPages + 1;
        }
        List<Map> res = new ArrayList<>();
        for (int i = (page - 1 ) * size; i < page * size && i < productList.size(); i++) {
            Map temp = productList.get(i);
            temp.put("page",page);
            temp.put("size",size);
            temp.put("total",productList.size());
            temp.put("totalPages", totalPages );
            res.add(temp);
        }
        if (res.size() != 0){
            return ResultUtil.success(res);
        }else{
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }
}
