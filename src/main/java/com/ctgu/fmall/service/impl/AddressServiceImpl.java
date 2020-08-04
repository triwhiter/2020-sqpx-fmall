package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.common.ResultEnum;
import com.ctgu.fmall.entity.Address;
import com.ctgu.fmall.mapper.AddressMapper;
import com.ctgu.fmall.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ctgu.fmall.utils.ResultUtil;
import com.ctgu.fmall.vo.Result;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zhen
 * @since 2020-08-02
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    @Override
    public Result addAddressInfo(Address address) {
        boolean IsSave = this.save(address);
        if (IsSave != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result editAddress(Address address) {
        boolean IsUpdate = this.updateById(address);
        if(IsUpdate != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result delAddress(int id) {
        boolean IsRemove = this.removeById(id);
        if (IsRemove != true){
            return ResultUtil.error(ResultEnum.FAIL);
        }else{
            return ResultUtil.success(ResultEnum.SUCCESS);
        }
    }

    @Override
    public Result getAllAddress(int uid) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.like("uid",uid);
        List<Address> addressList = this.list(wrapper);
        if (addressList != null){
            return  ResultUtil.success(addressList);
        }else{
            return ResultUtil.error(ResultEnum.FAIL);
        }
    }

}
