package com.ctgu.fmall.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ctgu.fmall.entity.Address;
import com.ctgu.fmall.mapper.AddressMapper;
import com.ctgu.fmall.service.AddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

    @Autowired
    private AddressMapper addressMapper;

    @Override
    public Result addAddressInfo(Address address) {
        boolean IsSave = this.save(address);
        if (IsSave != true){
            return new Result(400,"添加失败",false);
        }else{
            return new Result(200,"添加成功", true);
        }
    }

    @Override
    public Result editAddress(Address address) {
        boolean IsUpdate = this.updateById(address);
        if(IsUpdate != true){
            return new Result(400,"更新失败",false);
        }else{
            return new Result(200,"更新成功", true);
        }
    }

    @Override
    public Result delAddress(int id) {
        boolean IsRemove = this.removeById(id);
        if (IsRemove != true){
            return new Result(400,"删除失败",false);
        }else{
            return new Result(200,"删除成功",true);
        }
    }

    @Override
    public Result getAllAddress(int uid) {
        QueryWrapper<Address> wrapper = new QueryWrapper<>();
        wrapper.like("uid",uid);
        List<Address> addressList = this.list(wrapper);
        if (addressList != null){
            return new Result(200,"查询成功",addressList);
        }else{
            return new Result(400,"查询失败",null);
        }
    }

}
