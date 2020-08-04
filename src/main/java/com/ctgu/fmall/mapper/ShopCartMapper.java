package com.ctgu.fmall.mapper;

import com.ctgu.fmall.entity.ShopCart;
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
 * @since 2020-07-31
 */
@Repository
public interface ShopCartMapper extends BaseMapper<ShopCart> {

    @Select("select product.*, product_image.*, shop_cart.* from product, product_image, shop_cart where" +
            " shop_cart.uid = #{uid} and product.id = shop_cart.pid and product.id = product_image.pid group by product_image.pid;")
    List<Map> getShopCartInfo(int uid);
}
