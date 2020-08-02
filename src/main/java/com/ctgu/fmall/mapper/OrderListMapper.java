package com.ctgu.fmall.mapper;

import com.ctgu.fmall.entity.OrderList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@Repository
public interface OrderListMapper extends BaseMapper<OrderList> {

    @Select("SELECT order_list.*,order_detail.*,product.*,user.* from order_list, order_detail, user, product where order_list.uid = #{uid} and order_list.order_code = order_detail.oid and order_list.uid = user.id and order_detail.pid = product.id;")
    List<Object> getOrderListInfoByUid(int uid);
}
