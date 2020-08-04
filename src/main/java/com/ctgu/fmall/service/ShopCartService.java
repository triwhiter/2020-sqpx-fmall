package com.ctgu.fmall.service;

import com.ctgu.fmall.entity.ShopCart;
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
public interface ShopCartService extends IService<ShopCart> {

    Result getShopCartInfo(int uid);


    Result delShopCartById(int uid, int pid);

    Result delAllShopCart(int uid);
}
