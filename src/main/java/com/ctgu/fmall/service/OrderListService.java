package com.ctgu.fmall.service;

import com.ctgu.fmall.entity.OrderList;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ctgu.fmall.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhen
 * @since 2020-07-31
 */
public interface OrderListService extends IService<OrderList> {

    Result getOrderListInfoByUid(int uid);
}
