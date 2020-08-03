package com.ctgu.fmall.service;

import com.ctgu.fmall.entity.Address;
import com.baomidou.mybatisplus.extension.service.IService;
import com.ctgu.fmall.vo.Result;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zhen
 * @since 2020-08-03
 */
public interface AddressService extends IService<Address> {

    Result addAddressInfo(Address address);

    Result editAddress(Address address);

    Result delAddress(int id);

    Result getAllAddress(int uid);
}
