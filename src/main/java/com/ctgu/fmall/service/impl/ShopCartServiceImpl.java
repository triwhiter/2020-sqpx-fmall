package com.ctgu.fmall.service.impl;

import com.ctgu.fmall.common.ResultEnum;
import com.ctgu.fmall.entity.ShopCart;
import com.ctgu.fmall.mapper.ShopCartMapper;
import com.ctgu.fmall.service.ShopCartService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
 * @since 2020-07-31
 */
@Service
public class ShopCartServiceImpl extends ServiceImpl<ShopCartMapper, ShopCart> implements ShopCartService {

    @Autowired
    private ShopCartMapper shopCartMapper;

    @Override
    public Result getShopCartInfo(int uid) {
        List<Map> shopCartInfoList =  shopCartMapper.getShopCartInfo(uid);
        if (shopCartInfoList != null){
            return ResultUtil.success(shopCartInfoList);
        }else{
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

    @Override
    public Result delShopCartById(int uid, int pid) {
        boolean IsDel = shopCartMapper.delShopCartById(uid, pid);
        if (IsDel != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result delAllShopCart(int uid) {
        boolean IsDel = shopCartMapper.delAllShopCart(uid);
        if (IsDel != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }


}
