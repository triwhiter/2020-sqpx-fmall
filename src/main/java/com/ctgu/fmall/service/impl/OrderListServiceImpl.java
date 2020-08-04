package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.entity.OrderList;
import com.ctgu.fmall.mapper.OrderListMapper;
import com.ctgu.fmall.service.OrderListService;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        }else{
            return ResultUtil.error("获取失败");
        }
    }

}
