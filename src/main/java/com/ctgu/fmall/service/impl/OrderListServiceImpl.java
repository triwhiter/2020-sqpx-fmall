package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.ctgu.fmall.common.ResultEnum;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.entity.OrderList;
import com.ctgu.fmall.mapper.OrderListMapper;
import com.ctgu.fmall.mapper.ProductMapper;
import com.ctgu.fmall.service.OrderListService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.service.ProductService;
import com.ctgu.fmall.utils.ResultUtil;
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
 * @since 2020-08-02
 */
@Service
public class OrderListServiceImpl extends ServiceImpl<OrderListMapper, OrderList> implements OrderListService {

    @Autowired
    private OrderListMapper orderListMapper;

    @Override
    public Result getOrderListInfoByUid(int uid) {
        List<Map> orderListInfo = orderListMapper.getOrderListInfoByUid(uid);
        if(orderListInfo != null){
            return ResultUtil.success(orderListInfo);
            //return new Result(200,"获取数据成功",orderListInfo);
        }else{
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

    @Override
    public Result getOrderInfoPage(int uid, int page, int size) {
        List<Map> orderListInfo = orderListMapper.getOrderListInfoByUid(uid);
        List<Map> res = new ArrayList<>();
        for (int i = (page - 1 ) * size; i < page * size ; i++) {
            Map temp = orderListInfo.get(i);
            temp.put("page",page);
            temp.put("size",size);
            temp.put("total",orderListInfo.size());
            res.add(temp);
        }
        if (res.size() != 0){
            return ResultUtil.success(res);
        }else{
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

    @Override
    public Result getAllOrderInfo(int page, int size) {
        List<Map> allOrderInfo = orderListMapper.getAllOrderInfo();
        List<Map> res = new ArrayList<>();
        for (int i = (page - 1 ) * size; i < page * size && i < allOrderInfo.size() ; i++) {
            Map temp = allOrderInfo.get(i);
            temp.put("page",page);
            temp.put("size",size);
            temp.put("total",allOrderInfo.size());
            res.add(temp);
        }
        if (res.size() != 0){
            return ResultUtil.success(res);
        }else{
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

    @Override
    public Result delOrder(int id) {
        boolean IsRemove = this.removeById(id);
        if (IsRemove != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else {
            return ResultUtil.success(ResultEnum.SUCCESS);
        }

    }

    @Override
    public Result addOrder(OrderList orderList) {
        boolean IsSave = this.save(orderList);
        if (IsSave != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else {
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result editOrder(OrderList orderList) {
        boolean IsUpdate = this.updateById(orderList);
        if (IsUpdate != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else {
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result getProductInfoById(int id) {
        List<Map> productInfo = orderListMapper.getProductInfoById(id);
        if (productInfo != null){
            return ResultUtil.success(productInfo);
        }else {
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

    @Override
    public Result updateStatusById(int id) {
        OrderList order = this.getById(id);
        order.setStatus("已审核");
        boolean IsUpdate = this.updateById(order);
        if(IsUpdate != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else {
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

}
