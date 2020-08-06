package com.ctgu.fmall.mapper;

import com.ctgu.fmall.entity.OrderList;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

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

    @Select("SELECT ANY_VALUE(order_list.id) as id,ANY_VALUE(product.store) as store," +
            "ANY_VALUE(order_list.amount) as amount,ANY_VALUE(product.`name`) as name," +
            "ANY_VALUE(order_list.create_time) as create_time, ANY_VALUE(product_image.img_url) as img_url, " +
            "ANY_VALUE(order_detail.number) as number " +
            "from order_list, order_detail, user, product, product_image where order_list.uid = #{uid} and order_list.id = order_detail.oid and order_list.uid = user.id and order_detail.pid = product.id and product.id = product_image.pid group by product_image.pid;")
    List<Map> getOrderListInfoByUid(int uid);

    @Select("SELECT ANY_VALUE(order_list.id) as id,ANY_VALUE(order_list.amount) as amount, " +
            "ANY_VALUE(order_list.`status`) as status,ANY_VALUE(order_list.create_time) as create_time, " +
            "ANY_VALUE(order_list.user_message) as user_message, ANY_VALUE(order_list.update_time) as update_time, " +
            "ANY_VALUE(order_list.uid) as uid,  ANY_VALUE(user.user_name) as user_name,  " +
            "ANY_VALUE(user.phone_number) as phone_number, ANY_VALUE(address.area) as area, " +
            "ANY_VALUE(address.receiver) as receiver,ANY_VALUE(address.street) as street " +
            "from order_list, user, address where order_list.uid = user.id and order_list.aid = address.id;")
    List<Map> getAllOrderInfo();

    @Select("select DISTINCT ANY_VALUE(product.`name`) as name,ANY_VALUE(FORMAT(product.original_price,2)) as original_price,ANY_VALUE(FORMAT(product.promote_price,2)) as promote_price, ANY_VALUE(product.store) as store,  ANY_VAlue(product_image.img_url) as img_url,ANY_VAlUE(order_detail.number) as number from product, order_list, order_detail, product_image where order_list.id = #{id} and order_list.id = order_detail.oid and order_detail.pid = product.id and product.id = product_image.pid group by order_detail.id;")
    List<Map> getProductInfoById(int id);
}
